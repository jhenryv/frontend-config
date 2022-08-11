package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.ProcesosDto;
import vuce2.mfeconfig.models.Procesos;
import vuce2.mfeconfig.repositories.ProcesosRepository;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class ProcesosService {

    @Resource
    ProcesosRepository procesosRepository;


    public ArrayList<ProcesosDto> readProcesos(){
        var request = procesosRepository.findAll();

        ArrayList <ProcesosDto> procesosDtoArrayList = new ArrayList<ProcesosDto>();
        for ( Procesos item: request) {
            ProcesosDto entity = ObjectMapperUtils.map(item, ProcesosDto.class);
            procesosDtoArrayList.add(entity);
        }
        return  procesosDtoArrayList;

    }
}
