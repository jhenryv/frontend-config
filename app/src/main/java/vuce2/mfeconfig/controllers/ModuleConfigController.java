package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vuce2.mfeconfig.dtos.ModuleConfigDto;
import vuce2.mfeconfig.services.ModuleConfigService;
import vuce2.library.common.model.StatusResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/module-config")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ModuleConfigController extends BaseController {
    @javax.annotation.Resource
    private ModuleConfigService _moduleConfigService;

    //@PostMapping("/seed")
    public ResponseEntity<?> Seed() {
        return new ResponseEntity<>(_moduleConfigService.Seed(), HttpStatus.OK);
    }

    @PostMapping(value = "/get-by-rnames/")
    public ResponseEntity<?> getResourceDb(@RequestBody String[] microfrontendNames) {
        return new ResponseEntity<>(_moduleConfigService.getModuleConfigsByMicrofrontend(microfrontendNames), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<StatusResponse> UploadConfig(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        var result = new StatusResponse();
        //region Validaciones del archivo json
        var fileValidationResult = VerifyUploadedFile(file);
        if (fileValidationResult.getSuccess() == false) {
            result.setMessages(fileValidationResult.getMessages());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        //endregion
        String fileContents = new String(file.getBytes(), StandardCharsets.UTF_8);
        result = _moduleConfigService.LoadData(fileContents);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/upsert")
    public ResponseEntity<StatusResponse> Upsert(@RequestBody ModuleConfigDto request) throws IOException, InterruptedException {

        StatusResponse result = _moduleConfigService.Upsert(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/by-id")
    public ResponseEntity<StatusResponse> Delete(@RequestBody String id) {
        StatusResponse result = _moduleConfigService.Delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/by-rname")
    public ResponseEntity<StatusResponse> DeleteMf(@RequestBody String remoteName) {
        StatusResponse result = _moduleConfigService.DeleteMf(remoteName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/get-rnames")
    public ResponseEntity<?> getRemoteNames() {
        return new ResponseEntity<>(_moduleConfigService.getRemoteNames(), HttpStatus.OK);
    }

    @PostMapping("/get-by-rname-expmod")
    public ResponseEntity<ModuleConfigDto> Read(@RequestBody RemoteNameAndExposedModule request) throws IOException, InterruptedException {

        ModuleConfigDto result = _moduleConfigService.getByRemoteNameAndExposedModule(request.remoteName, request.exposedModule);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(_moduleConfigService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<ModuleConfigDto> getById(@RequestBody String id) {
        ModuleConfigDto result = _moduleConfigService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //region Clases auxiliares
    static class RemoteNameAndExposedModule {
        public String remoteName;
        public String exposedModule;
    }
//endregion
}

