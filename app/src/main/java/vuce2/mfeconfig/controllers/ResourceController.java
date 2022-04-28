package vuce2.mfeconfig.controllers;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vuce2.mfeconfig.services.ResourceService;
import vuce2.library.common.model.StatusResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/resource")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ResourceController extends BaseController {

    @javax.annotation.Resource
    private ResourceService _resourceService;

    //    @GetMapping(value = "/resource/{name}")
//    public JSONObject GetResource(@PathVariable("name") String name) {
//        return configService.getResource(name);
//    }
    @PostMapping(value = "/get-by-code")
    public JSONObject getResourceDb(@RequestParam("code") String code) {
        return _resourceService.getResourceDb(code);
    }

    @PostMapping(value = "/get-codes")
    public ResponseEntity<?> getKeys() {
        return new ResponseEntity<>(_resourceService.getKeys(), HttpStatus.OK);
    }

//    @GetMapping(value = "/microfrontend")
//    public JSONObject Microfrontend() {
//        return configService.getMicrofrontend();
//    }

    //@PostMapping("/seed")
    public ResponseEntity<?> SeedConfig() {
        return new ResponseEntity<>(_resourceService.SeedConfig(), HttpStatus.OK);
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
        result = _resourceService.LoadData(fileContents);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
