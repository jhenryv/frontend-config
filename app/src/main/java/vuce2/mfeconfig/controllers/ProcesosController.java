package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.mfeconfig.dtos.ProcesosDto;
import vuce2.mfeconfig.services.ProcesosService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/procedimiento")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ProcesosController {

    @Resource
    ProcesosService procesosService;

    @PostMapping("/read-procesos")
    public ResponseEntity<List<ProcesosDto>> ReadAllProcesos() {
        ArrayList<ProcesosDto> result = procesosService.readProcesos();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
