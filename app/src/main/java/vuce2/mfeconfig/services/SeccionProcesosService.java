package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.SeccionProcesosDto;
import vuce2.mfeconfig.models.SeccionProcesos;
import vuce2.mfeconfig.repositories.SeccionProcesosRepository;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class SeccionProcesosService {
    @Resource
    SeccionProcesosRepository seccionProcesosRepository;

    public ArrayList<SeccionProcesosDto> readSeccionProcesos(){
        var request = seccionProcesosRepository.findAll();

        ArrayList <SeccionProcesosDto> seccionProcesosDtoArrayList = new ArrayList<SeccionProcesosDto>();
        for ( SeccionProcesos item: request) {
            SeccionProcesosDto entity = ObjectMapperUtils.map(item, SeccionProcesosDto.class);
            seccionProcesosDtoArrayList.add(entity);
        }
        return  seccionProcesosDtoArrayList;

    }
}
