package vuce2.mfeconfig.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.dtos.BusquedaDto;
import vuce2.mfeconfig.dtos.SeccionesDto;
import vuce2.mfeconfig.models.Busqueda;
import vuce2.mfeconfig.models.Secciones;
import vuce2.mfeconfig.services.BusquedaService;
import vuce2.mfeconfig.services.SeccionesService;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/secciones")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SeccionesController {

    @Resource
    private SeccionesService seccionesService;

    @PostMapping("/insert-seccion")
    public ResponseEntity<StatusResponse> Insert(@RequestBody Secciones request) {

        StatusResponse result = seccionesService.insertSeccion(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/read-seccion")
    public ArrayList<SeccionesDto> Read() {
        ArrayList<SeccionesDto>  result = seccionesService.readSeccion();
        return result;
    }

}
