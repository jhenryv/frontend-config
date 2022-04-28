package vuce2.mfeconfig.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.models.Resource;
import vuce2.mfeconfig.repositories.BaseConfigService;
import vuce2.mfeconfig.repositories.ResourceRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ResourceService extends BaseConfigService {

    @javax.annotation.Resource
    private MongoDatabase mongoDatabase;

    @javax.annotation.Resource
    private ResourceRepository configRepository;

    @SneakyThrows
    public String ResourcePath() {
        Path _folder = Paths.get("config");

        return _folder.toString();
    }

    @SneakyThrows
    public String MicrofrontendPath() {
        Path _folder = Paths.get("config", "microfrontend");
        return _folder.toString();
    }

    @SneakyThrows
    public JSONObject getResource(String name) {

        Path path = Paths.get(this.ResourcePath(), name);
        String fileName = path.toString() + ".json";
        return GetData(fileName, name);
    }

    @SneakyThrows
    public JSONObject getResourceDb(String code) {
        //var query = configRepository.findByCodigo(name);
        JSONObject jo = new JSONObject();
        MongoCollection<Document> cresource = mongoDatabase.getCollection("resource", Document.class);

        Bson where = Filters.eq("codigo", code);
        FindIterable<Document> items = cresource.find(where);

        for (Document document : items) {
            jo.put(code, document.get("datos"));
        }
        return jo;
    }

    public ArrayList<String> getKeys() {
        var resourceKeys = new ArrayList<String>();
        MongoCollection<Document> cresource = mongoDatabase.getCollection("resource", Document.class);

        Bson where = Filters.ne("codigo", "");//codigo !=""
        FindIterable<Document> results = cresource.find(where);
        Gson gson = new Gson();
        for (Document result : results) {
            resourceKeys.add(result.getString("codigo"));
        }
        return resourceKeys;
    }

    @SneakyThrows
    public JSONObject getMicrofrontend() {

        var jo = new JSONObject();

        File f = new File(this.MicrofrontendPath());

        File[] files = f.listFiles();

        for (File file : files) {
            String onlyName = file.getName().replace(".json", "");
            JSONArray ja = GetData(file, onlyName);
            jo.put(onlyName, ja);
        }

        return jo;
    }

    public ArrayList<StatusResponse> SeedConfig() {
        var results = new ArrayList<StatusResponse>();
        String[] configFileNames = GetAvailableConfigFileNames();
        if (configFileNames == null || configFileNames.length == 0) {
            var status = new StatusResponse();
            status.AddMessage("No se encontró archivos de configuración en el directorio especificado.");
            results.add(status);
            return results;
        }

        configRepository.deleteAll(); //temporal

        for (var configFileName : configFileNames) {
            var configKey = Files.getNameWithoutExtension(configFileName);

            var currentOpResult = new StatusResponse();
            currentOpResult.setData(configKey);

            var jsonConfigBody = getResource(configKey);
            var jsonEntriesArray = jsonConfigBody.entrySet().toArray();
            if (jsonEntriesArray.length == 0) {
                currentOpResult.AddMessage("No se pudo obtener elementos a registrar.");
                results.add(currentOpResult);
                continue;
            }
            boolean lecturaDeArchivoDeConfiguracionExitosa = false;
            ArrayList<HashMap<String, Object>> currentResourceHashMapList = null;
            try {
                var configEntry = (Map.Entry<String, Object>) jsonEntriesArray[0];
                var configValue = configEntry.getValue();
                if (configValue.getClass() != JSONArray.class) {
                    currentOpResult.AddMessage("No se pudo obtener un arreglo de elementos para la clave '" + configKey + "'.");
                    results.add(currentOpResult);
                    continue;
                }
                JSONArray configValueArray = (JSONArray) configEntry.getValue();
                ObjectMapper oMapper = new ObjectMapper();
                currentResourceHashMapList = new ArrayList<HashMap<String, Object>>();
                for (Object configItem : configValueArray) {
                    HashMap<String, Object> map = oMapper.convertValue(configItem, HashMap.class);
                    currentResourceHashMapList.add(map);
                }

                lecturaDeArchivoDeConfiguracionExitosa = true;
            } catch (Exception ex) {
                currentOpResult.AddMessage("Ocurrió un error al leer los datos del archivo de configuración. " + ex.getMessage());
            }
            if (lecturaDeArchivoDeConfiguracionExitosa == false) {
                results.add(currentOpResult);
                continue;
            }
            var currentResource = new Resource();
            currentResource.setCodigo(configKey);
            currentResource.setTipo(configKey);
            currentResource.setDatos(currentResourceHashMapList);
            try {
                configRepository.save(currentResource);
                currentOpResult.setSuccess(true);
            } catch (Exception ex) {
                currentOpResult.AddMessage("Ocurrió un error al registrar los datos del archivo de configuración. " + ex.getMessage());
            }
            results.add(currentOpResult);
        }

        return results;
    }

    private JSONObject GetData(String fileName, String key) throws InterruptedException {
        JSONObject jo = new JSONObject();

        try {
            File file = new File(fileName);
            Object obj = new JSONParser().parse(new FileReader(file));

            jo = (JSONObject) obj;
            //items = (JSONArray) jsonObject.get(key);

        } catch (RuntimeException | IOException | ParseException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return jo;
    }

    private JSONArray GetData(File file, String key) throws InterruptedException {
        JSONArray items = new JSONArray();

        try {
            Object obj = new JSONParser().parse(new FileReader(file));

            JSONObject jsonObject = (JSONObject) obj;
            items = (JSONArray) jsonObject.get(key);

        } catch (RuntimeException | IOException | ParseException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return items;
    }

    public String[] GetAvailableConfigFileNames() {
        ArrayList<String> fileNames = new ArrayList<String>();
        File configFolder = new File(this.ResourcePath());
        File[] configFiles = configFolder.listFiles();
        for (int i = 0; i < configFiles.length; i++) {
            if (configFiles[i].isDirectory()) continue;
            fileNames.add(configFiles[i].getName());
        }
        return fileNames.toArray(new String[fileNames.size()]);
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
        ArrayList<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();

        Gson gson = new Gson();
        for (Object configItem : configValueArray) {
            HashMap<String, Object> map = oMapper.convertValue(configItem, HashMap.class);
            mapList.add(map);
        }
        MongoCollection<Document> cresource = mongoDatabase.getCollection("resource", Document.class);

        //eliminando registro preexistente con codigo = configKey
        var currentResource = FindResourceByCodigo(configKey);
        if (currentResource != null) {

            Bson where = Filters.eq("codigo", configKey);
            cresource.findOneAndDelete(where);
        }

        var newResource = new Resource();
        newResource.setCodigo(configKey);
        newResource.setTipo(configKey);
        newResource.setDatos(mapList);

        String jsonString = gson.toJson(newResource);

        var newdoc = Document.parse(jsonString);
        try {
            cresource.insertOne(newdoc);
            response.setSuccess(true);
        } catch (Exception ex) {
            response.AddMessage("Ocurrió un error al registrar los datos del archivo de configuración. " + ex.getMessage());
        }
        return response;
    }

    Resource FindResourceByCodigo(String codigo) throws JsonProcessingException {
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
}
