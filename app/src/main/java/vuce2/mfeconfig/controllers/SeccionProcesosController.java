package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.mfeconfig.dtos.ProcesosDto;
import vuce2.mfeconfig.dtos.SeccionProcesosDto;
import vuce2.mfeconfig.services.SeccionProcesosService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seccion-procesos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SeccionProcesosController {

    @Resource
    SeccionProcesosService seccionProcesosService;

    @PostMapping("/read-seccion-procesos")
    public ResponseEntity<List<SeccionProcesosDto>> ReadAllSeccionProcesos() {
        ArrayList<SeccionProcesosDto> result = seccionProcesosService.readSeccionProcesos();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
