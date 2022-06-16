package vuce2.mfeconfig.services;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.BusquedaDto;
import vuce2.mfeconfig.dtos.SeccionesDto;
import vuce2.mfeconfig.models.Busqueda;
import vuce2.mfeconfig.models.Secciones;
import vuce2.mfeconfig.repositories.SeccionesRepository;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j

public class SeccionesService {

    @Resource
    private SeccionesRepository seccionesRepository;


    public StatusResponse insertSeccion(Secciones request) {
        StatusResponse sr = new StatusResponse();
        Secciones entity = ObjectMapperUtils.map(request, Secciones.class);
        entity.setId(new ObjectId());
        seccionesRepository.save(entity);
        sr.setSuccess(true);
        sr.AddMessage("Seccion Registrado");
        return sr;
    }

    public ArrayList<SeccionesDto> readSeccion(){
        var request = seccionesRepository.findAll();
        ArrayList <SeccionesDto> seccionesDtosArrayList = new ArrayList<SeccionesDto>();
        for ( Secciones item: request) {
            SeccionesDto entity = ObjectMapperUtils.map(item, SeccionesDto.class);
            seccionesDtosArrayList.add(entity);
        }
        return  seccionesDtosArrayList;
    }
}
