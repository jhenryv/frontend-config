package vuce2.mfeconfig.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.ModuleConfigDto;
import vuce2.mfeconfig.models.ModuleConfig;
import vuce2.mfeconfig.models.Resource;
import vuce2.mfeconfig.repositories.BaseConfigService;
import vuce2.mfeconfig.repositories.ModuleConfigRepository;

import java.util.*;

@Service
@Slf4j
public class ModuleConfigService extends BaseConfigService {
    @javax.annotation.Resource
    private MongoDatabase mongoDatabase;

    @javax.annotation.Resource
    private ModuleConfigRepository moduleConfigRepository;

    public ArrayList<StatusResponse> Seed() {
        var results = new ArrayList<StatusResponse>();
        String[] configFiles = GetAvailableConfigFiles("microfrontend");
        if (configFiles == null || configFiles.length == 0) {
            results.add(new StatusResponse(false, "No se encontró archivos de configuración en el directorio especificado."));
            return results;
        }
        moduleConfigRepository.deleteAll(); //temporal

        for (var configFile : configFiles) {
            var configFileJsonData = ParseJsonFromConfigFile(configFile);
            var currentConfigFileParseResult = ConstructGenericJsonConfig(configFileJsonData);
            if (currentConfigFileParseResult.getSuccess() == false) {
                results.add(currentConfigFileParseResult);
                continue;
            }
            var configKey = currentConfigFileParseResult.getData().getCodigo();
            var curentGenericConfigData = currentConfigFileParseResult.getData().getDatos();

            var currentOpResult = new StatusResponse();
            currentOpResult.setData(configKey);
            try {
                for (var currentHashMap : curentGenericConfigData) {
                    var currentModuleConfigItem = constructModuleConfig(currentHashMap);
                    moduleConfigRepository.save(currentModuleConfigItem);
                }
                currentOpResult.setSuccess(true);
            } catch (Exception ex) {
                currentOpResult.AddMessage("Ocurrió un error al registrar los datos del archivo de configuración. " + ex.getMessage());
            }
            results.add(currentOpResult);
        }
        return results;
    }

    protected ModuleConfig constructModuleConfig(HashMap<String, String> hashMap) {
        if (hashMap == null) return null;
        var result = new ModuleConfig();
        result.setRemoteEntry(hashMap.get("remoteEntry"));
        result.setRemoteName(hashMap.get("remoteName"));
        result.setExposedModule(hashMap.get("exposedModule"));
        result.setDisplayName(hashMap.get("displayName"));
        result.setRoutePath(hashMap.get("routePath"));
        result.setNgName(hashMap.get("ngName"));
        return result;
    }

    public Hashtable<String, ArrayList<ModuleConfigDto>> getModuleConfigsByMicrofrontend(String[] microfrontendNames) {
        var result = new Hashtable<String, ArrayList<ModuleConfigDto>>();
        //region 1) Validacion de datos de entrada
        if (microfrontendNames == null || microfrontendNames.length == 0) {
            return result;
        }
        //endregion
        //region 2) Inicializacion de estructura de resultados
        for (String mfName : microfrontendNames) {
            if (result.containsKey(mfName) == false) {
                result.put(mfName, new ArrayList<ModuleConfigDto>());
            }
        }
        //endregion
        //region 3) Obtencion de datos en origen
        var moduleConfigCollection = mongoDatabase.getCollection("moduleConfig", Document.class);
        Bson where = Filters.in("remoteName", microfrontendNames);
        FindIterable<Document> dbResults = moduleConfigCollection.find(where);
        //endregion
        //region 4) Poblado de resultados
        for (Document dbResult : dbResults) {
            var convertedResult = ObjectMapperUtils.map(dbResult, ModuleConfigDto.class);
            convertedResult.setId(dbResult.get("_id").toString());
            result.get(convertedResult.getRemoteName()).add(convertedResult);
        }
        //endregion
        return result;
    }

    public String[] getRemoteNames() {
        var resourceKeys = new ArrayList<String>();
        MongoCollection<Document> cresource = mongoDatabase.getCollection("moduleConfig", Document.class);

        Bson where = Filters.ne("remoteName", "");//remoteName !=""
        FindIterable<Document> results = cresource.find(where);
        Gson gson = new Gson();
        var hashRemoteNames = new HashSet<String>();
        for (Document result : results) {
            var currentRemoteName = result.getString("remoteName");
            if (hashRemoteNames.contains(currentRemoteName)) continue;
            hashRemoteNames.add(currentRemoteName);
        }
        return hashRemoteNames.toArray(new String[hashRemoteNames.size()]);
    }

    public StatusResponse LoadData(String configBody) throws InterruptedException, JsonProcessingException {
        var response = new StatusResponse();
        var jsonConfigBody = ParseJsonFromString(configBody);
        var jsonEntriesArray = jsonConfigBody.entrySet().toArray();
        if (jsonEntriesArray.length == 0) {
            response.AddMessage("No se pudo leer dato de configuración del texto especificado.");
            return response;
        }
        var configEntry = (Map.Entry<String, Object>) jsonEntriesArray[0];
        var configKey = configEntry.getKey();
        var configValue = configEntry.getValue();
        if (configValue.getClass() != JSONArray.class) {
            response.AddMessage("No se pudo obtener un arreglo de elementos para la clave '" + configKey + "'.");
            return response;
        }
        JSONArray configValueArray = (JSONArray) configEntry.getValue();
        ObjectMapper oMapper = new ObjectMapper();
        ArrayList<HashMap<String, String>> hashMapList = new ArrayList<HashMap<String, String>>();

        Gson gson = new Gson();
        for (Object configItem : configValueArray) {
            HashMap<String, String> map = oMapper.convertValue(configItem, HashMap.class);
            hashMapList.add(map);
        }
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleConfig", Document.class);

        //eliminando registros preexistentes con remoteName = configKey
        var preExistingConfigItems = getModuleConfigsByMicrofrontend(new String[]{configKey});
        if (preExistingConfigItems.size() > 0) {

            Bson where = Filters.eq("remoteName", configKey);
            collection.deleteMany(where);
        }

        for (var hashMap : hashMapList) {
            var currentModuleConfigItem = constructModuleConfig(hashMap);
            moduleConfigRepository.save(currentModuleConfigItem);
        }
        response.setSuccess(true);
        return response;
    }

    Resource FindNgModulesByResourceName(String codigo) throws JsonProcessingException {
        if (codigo == null || codigo == "") return null;
        MongoCollection<Document> cresource = mongoDatabase.getCollection("resource", Document.class);
        Bson where = Filters.eq("codigo", codigo);
        FindIterable<Document> items = cresource.find(where);
        if (items == null) return null;
        Gson gson = new Gson();
        for (var item : items) {
            var json = item.toJson();
            Resource mappedItem = gson.fromJson(json, Resource.class);
            return mappedItem;
        }
        return null;
    }

    //region Upsert

    public StatusResponse Upsert(ModuleConfigDto request) {
        StatusResponse sr = new StatusResponse();
        //Valid
        Optional<ModuleConfig> e = moduleConfigRepository.findById(request.getId());
        if (e.isPresent()) {
            //Update
            ModuleConfig ue = ObjectMapperUtils.map(e.get(), ModuleConfig.class);
            Optional<ModuleConfig> r = moduleConfigRepository.findByRemoteNameAndExposedModule(request.getRemoteName(), request.getExposedModule());
            //TODO:
            if (r.isPresent()) {
                ue.setRemoteName(request.getRemoteName());
                ue.setExposedModule(request.getExposedModule());
                ue.setDisplayName(request.getDisplayName());
                ue.setNgName(request.getNgName());
                ue.setRoutePath(request.getRoutePath());
                ue.setComponents(request.getComponents());
                ue.setRemoteEntry("");
                //update
                moduleConfigRepository.save(ue);
                sr.AddMessage("Registro actualizado");
            } else {
                sr.AddMessage("Remote name / Exposed module no coinciden con id");
            }
            return sr;
        } else {
            //New
            Optional<ModuleConfig> r = moduleConfigRepository.findByRemoteNameAndExposedModule(request.getRemoteName(), request.getExposedModule());

            if (r.isPresent()) {
                sr.AddMessage("Ya se encuentra registrado");
                return sr;
            }

            ModuleConfig entity = ObjectMapperUtils.map(request, ModuleConfig.class);
            entity.setId(new ObjectId());
            moduleConfigRepository.save(entity);
            sr.AddMessage("Registro guardado correctamente");
        }
        sr.setSuccess(true);
        return sr;
    }

    //endregion

    //region Delete
    public StatusResponse Delete(String id) {
        StatusResponse sr = new StatusResponse();
        Optional<ModuleConfig> e = moduleConfigRepository.findById(id);
        if (!e.isPresent()) {
            sr.AddMessage("No se encuentra el Id");
            return sr;
        }
        ModuleConfig ue = ObjectMapperUtils.map(e.get(), ModuleConfig.class);
        moduleConfigRepository.delete(ue);
        sr.setSuccess(true);
        sr.AddMessage("Eliminado correctamente");
        return sr;
    }
    //endregion

    //region DeleteMf
    public StatusResponse DeleteMf(String remoteName) {
        StatusResponse sr = new StatusResponse();
        List<ModuleConfig> e = moduleConfigRepository.findByRemoteName(remoteName);
        for (ModuleConfig item : e) {
            ModuleConfig ue = ObjectMapperUtils.map(item, ModuleConfig.class);
            moduleConfigRepository.delete(ue);
        }
        sr.setSuccess(true);
        sr.AddMessage("Eliminado correctamente el microfronted");
        return sr;
    }
    //endregion

    //region ObtnerUno
    public ModuleConfigDto getById(String id) {
        Optional<ModuleConfig> e = moduleConfigRepository.findById(id);
        if (e.isPresent()) {
            return ObjectMapperUtils.map(e.get(), ModuleConfigDto.class);
        }
        return null;
    }
    //endregion

    //region ObtenerTodos
    public List<ModuleConfigDto> getAll() {
        List<ModuleConfig> e = moduleConfigRepository.findAll();
        List<ModuleConfigDto> res = new ArrayList<ModuleConfigDto>();
        for (ModuleConfig item : e) {
            ModuleConfigDto obj = ObjectMapperUtils.map(item, ModuleConfigDto.class);
            res.add(obj);
        }
        return res;
    }
    //endregion

    //region Read
    public ModuleConfigDto getByRemoteNameAndExposedModule(String remoteName, String exposedModule) {
        Optional<ModuleConfig> e = moduleConfigRepository.findByRemoteNameAndExposedModule(remoteName, exposedModule);
        if (e.isPresent()) {

            ModuleConfigDto obj = ObjectMapperUtils.map(e.get(), ModuleConfigDto.class);
            return obj;
        }
        return null;
    }
    //endregion
}
