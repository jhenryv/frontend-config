package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.mfeconfig.dtos.RegistroConfiguracionDto;
import vuce2.mfeconfig.dtos.SeccionProcesosDto;
import vuce2.mfeconfig.dtos.SubSeccionesProcesosDto;
import vuce2.mfeconfig.services.SubSeccionesProcesosService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sub-seccion-procesos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SubSeccionesProcesosController {

    @Resource
    SubSeccionesProcesosService subSeccionesProcesosService;

    @PostMapping("/read-sub-seccion-procesos")
    public ResponseEntity<List<SubSeccionesProcesosDto>> ReadAllSubSecciones() {
        ArrayList<SubSeccionesProcesosDto> result = subSeccionesProcesosService.readSubSeccionesProceso();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/read-sub-seccion-proceso-especifico")
    public ResponseEntity<List<SubSeccionesProcesosDto>> ReadAllSubSeccionEspecifico(@RequestBody SubSeccionesProcesosDto request) {
        ArrayList<SubSeccionesProcesosDto> result = subSeccionesProcesosService.readSubSeccionesProcesoEspecifico(request.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
