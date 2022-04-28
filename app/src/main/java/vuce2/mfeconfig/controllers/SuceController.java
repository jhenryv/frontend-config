package vuce2.mfeconfig.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.dtos.ModuleContentGroupConfigDto;
import vuce2.mfeconfig.dtos.SuceDto;
import vuce2.mfeconfig.models.Suce;
import vuce2.mfeconfig.services.SuceService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/suce")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SuceController {

    @Resource
    private SuceService suceService;

    @PostMapping("/insert-suce")
    public ResponseEntity<StatusResponse> Insert(@RequestBody Suce request) {

        StatusResponse result = suceService.insertSuce(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/read-suce")
    public SuceDto Read() {
        SuceDto result = suceService.readSuce();
        return result;
    }

}
