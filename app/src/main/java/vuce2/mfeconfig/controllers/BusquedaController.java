package vuce2.mfeconfig.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.library.common.model.StatusResponse;
import vuce2.mfeconfig.dtos.BusquedaDto;
import vuce2.mfeconfig.dtos.SuceDto;
import vuce2.mfeconfig.models.Busqueda;
import vuce2.mfeconfig.services.BusquedaService;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/busqueda")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class BusquedaController {

    @Resource
    private BusquedaService busquedaService;

    @PostMapping("/insert-busqueda")
    public ResponseEntity<StatusResponse> Insert(@RequestBody Busqueda request) {

        StatusResponse result = busquedaService.insertBusqueda(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/read-busqueda")
    public ArrayList<BusquedaDto> Read() {
        ArrayList<BusquedaDto>  result = busquedaService.readBusqueda();
        return result;
    }
}
