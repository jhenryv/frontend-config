package vuce2.mfeconfig.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import vuce2.mfeconfig.models.GenericJsonConfigData;
import vuce2.library.common.model.StatusResponseT;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseConfigService {
    protected final String ConfigBaseDirectory = "config";

    protected String GetConfigPath() {
        return GetConfigPath(new String[]{});
    }

    protected String GetConfigPath(String... subdirectorios) {
        Path _folder = null;
        if (subdirectorios.length == 0) {
            _folder = Paths.get(ConfigBaseDirectory);
        } else {
            _folder = Paths.get(ConfigBaseDirectory, subdirectorios);
        }
        return _folder.toString();
    }

    public String[] GetAvailableConfigFiles() {
        return GetAvailableConfigFiles(new String[]{});
    }

    public String[] GetAvailableConfigFiles(String... subdirectorios) {
        ArrayList<String> configFileRelativePaths = new ArrayList<String>();
        File configFolder = new File(this.GetConfigPath(subdirectorios));
        File[] configFiles = configFolder.listFiles();
        for (int i = 0; i < configFiles.length; i++) {
            if (configFiles[i].isDirectory()) continue;
            configFileRelativePaths.add(configFiles[i].toString());
        }
        return configFileRelativePaths.toArray(new String[configFileRelativePaths.size()]);
    }

    protected JSONObject ParseJsonFromString(String jsonString) throws InterruptedException {
        JSONObject jo = new JSONObject();
        try {
            Object obj = new JSONParser().parse(jsonString);
            jo = (JSONObject) obj;
        } catch (RuntimeException | ParseException e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
        return jo;
    }

    protected JSONObject ParseJsonFromConfigFile(String configFileRelativePath) {
        JSONObject result = new JSONObject();
        try {
            File configFile = new File(configFileRelativePath);
            Object obj = new JSONParser().parse(new FileReader(configFile));
            result = (JSONObject) obj;

        } catch (RuntimeException | IOException | ParseException e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @SneakyThrows
    protected StatusResponseT<GenericJsonConfigData> ConstructGenericJsonConfig(JSONObject baseJsonObject) {
        var result = new StatusResponseT<GenericJsonConfigData>();
        //region Lectura/validación de objeto de entrada
        if (baseJsonObject == null) {
            result.AddMessage("Objeto base no válido");
            return result;
        }

        var jsonEntriesArray = baseJsonObject.entrySet().toArray();
        if (jsonEntriesArray.length == 0) {
            result.AddMessage("No se encontró datos de configuración del objeto base especificado.");
            return result;
        }

        Map.Entry<String, Object> baseConfigEntry = null;
        boolean lecturaDeConfigEntryExitosa = false;
        try {
            baseConfigEntry = (Map.Entry<String, Object>) jsonEntriesArray[0];
            lecturaDeConfigEntryExitosa = true;
        } catch (Exception ex) {
            result.AddMessage("No sepudo leer datos de configuración del objeto base especificado." + ex.getMessage());
        }
        if (lecturaDeConfigEntryExitosa == false) {
            return result;
        }
        //endregion

        var configKey = baseConfigEntry.getKey();
        var configValue = baseConfigEntry.getValue();
        if (configValue.getClass() != JSONArray.class) {
            result.AddMessage("No se pudo obtener un arreglo de elementos para la clave '" + configKey + "' en el objeto base especificado.");
            return result;
        }
        JSONArray configValueArray = (JSONArray) baseConfigEntry.getValue();
        ObjectMapper oMapper = new ObjectMapper();
        ArrayList<HashMap<String, String>> hashSet = new ArrayList<HashMap<String, String>>();
        boolean lecturaDeClavesValorExitosa = false;
        try {
            for (Object configItem : configValueArray) {
                HashMap<String, String> map = oMapper.convertValue(configItem, HashMap.class);
                hashSet.add(map);
            }
            lecturaDeClavesValorExitosa = true;
        } catch (Exception ex) {
            result.AddMessage("Ocurrió un error en la lectura del arreglo de elementos para la clave '" + configKey + "' en el objeto base especificado.");
        }
        if (lecturaDeClavesValorExitosa == false) {
            return result;
        }

        GenericJsonConfigData objData = new GenericJsonConfigData();
        objData.setCodigo(configKey);
        objData.setDatos(hashSet);

        result.setData(objData);
        result.setSuccess(true);
        return result;
    }

}
