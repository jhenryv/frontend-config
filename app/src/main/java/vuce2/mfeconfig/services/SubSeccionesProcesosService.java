package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuce2.mfeconfig.dtos.SeccionProcesosDto;
import vuce2.mfeconfig.dtos.SubSeccionesProcesosDto;
import vuce2.mfeconfig.models.SeccionProcesos;
import vuce2.mfeconfig.models.SubSeccionesProcesos;
import vuce2.mfeconfig.repositories.SubSeccionesProcesosRepository;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class SubSeccionesProcesosService {

    @Resource
    SubSeccionesProcesosRepository subSeccionesProcesosRepository;

    public ArrayList<SubSeccionesProcesosDto> readSubSeccionesProceso(){
        var request = subSeccionesProcesosRepository.findAll();

        ArrayList <SubSeccionesProcesosDto> subseccionProcesosDtoArrayList = new ArrayList<SubSeccionesProcesosDto>();
        for ( SubSeccionesProcesos item: request) {
            SubSeccionesProcesosDto entity = ObjectMapperUtils.map(item, SubSeccionesProcesosDto.class);
            subseccionProcesosDtoArrayList.add(entity);
        }
        return  subseccionProcesosDtoArrayList;

    }
}
