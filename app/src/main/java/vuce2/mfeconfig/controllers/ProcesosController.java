package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.mfeconfig.dtos.EntidadDto;
import vuce2.mfeconfig.dtos.ProcesosDto;
import vuce2.mfeconfig.models.Procesos;
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

    @PostMapping("/read-procedimiento-entidad")
    public ResponseEntity<List<ProcesosDto>> ReadProcedimientoEntidad(@RequestBody Procesos solicitud) {
        ArrayList<ProcesosDto> result = procesosService.readBusquedaProcedimientoidEntidad(solicitud.getIdEntidad(),solicitud.getProcedimiento());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/read-entidad")
    public ResponseEntity<List<EntidadDto>> ReadAllEntidad() {
        ArrayList<EntidadDto> result = procesosService.readEntidad();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
