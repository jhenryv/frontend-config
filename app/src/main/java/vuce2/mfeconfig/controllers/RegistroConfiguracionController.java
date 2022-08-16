package vuce2.mfeconfig.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.dtos.RegistroConfiguracionDto;
import vuce2.mfeconfig.services.RegistroConfiguracionService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/registro-configuracion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class RegistroConfiguracionController {

    @Resource
    RegistroConfiguracionService registroConfiguracionService;

    @PostMapping("/insert-registro-configuracion")
    public ResponseEntity<StatusResponse> InsertRegistroConfiguracion(@RequestBody RegistroConfiguracionDto request) {

        StatusResponse result = registroConfiguracionService.insertRegistroConfiguracion(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
