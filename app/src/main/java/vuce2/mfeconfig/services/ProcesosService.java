package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.EntidadDto;
import vuce2.mfeconfig.dtos.ProcesosDto;
import vuce2.mfeconfig.models.Entidad;
import vuce2.mfeconfig.models.Procesos;
import vuce2.mfeconfig.repositories.EntidadRepository;
import vuce2.mfeconfig.repositories.ProcesosRepository;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class ProcesosService {

    @Resource
    ProcesosRepository procesosRepository;

    @Resource
    private EntidadRepository entidadRepository;


    public ArrayList<ProcesosDto> readProcesos(){
        var request = procesosRepository.findAll();

        ArrayList <ProcesosDto> procesosDtoArrayList = new ArrayList<ProcesosDto>();
        for ( Procesos item: request) {
            ProcesosDto entity = ObjectMapperUtils.map(item, ProcesosDto.class);
            procesosDtoArrayList.add(entity);
        }
        return  procesosDtoArrayList;

    }
    public ArrayList<ProcesosDto> readBusquedaProcedimientoidEntidad(String idEntidad,String nombreProcedimiento){
        var request = procesosRepository.findByIdEntidadAndProcedimientoContaining(idEntidad,nombreProcedimiento);

        ArrayList <ProcesosDto> procedimientoDtoArrayList = new ArrayList<ProcesosDto>();
        for ( Procesos item: request) {
            ProcesosDto entity = ObjectMapperUtils.map(item, ProcesosDto.class);
            procedimientoDtoArrayList.add(entity);
        }
        return  procedimientoDtoArrayList;

    }
    public ArrayList<EntidadDto> readEntidad(){
        var request = entidadRepository.findAll();

        ArrayList <EntidadDto> entidadDtoArrayList = new ArrayList<EntidadDto>();
        for ( Entidad item: request) {
            EntidadDto entity = ObjectMapperUtils.map(item, EntidadDto.class);
            entidadDtoArrayList.add(entity);
        }
        return  entidadDtoArrayList;

    }
}
