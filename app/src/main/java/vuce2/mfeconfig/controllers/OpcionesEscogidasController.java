package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.dtos.OpcionesEscogidasDto;
import vuce2.mfeconfig.services.OpcionesEscogidasService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/opciones-escogidas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class OpcionesEscogidasController {

    @Resource
    OpcionesEscogidasService opcionesEscogidasService;

    @PostMapping("/insert-opciones-escogidas")
    public ResponseEntity<StatusResponse> InsertOpcionesEscodigdas(@RequestBody OpcionesEscogidasDto request) {

        StatusResponse result = opcionesEscogidasService.insertOpcionesEscogidas(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
