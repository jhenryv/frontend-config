package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.BusquedaDto;
import vuce2.mfeconfig.models.Busqueda;
import vuce2.mfeconfig.models.Suce;
import vuce2.mfeconfig.repositories.BusquedaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class BusquedaService {

    @Resource
    private BusquedaRepository busquedaRepository;

    public StatusResponse insertBusqueda(Busqueda request){
        StatusResponse sr = new StatusResponse();
        Busqueda entity = ObjectMapperUtils.map(request, Busqueda.class);
        entity.setId(new ObjectId());
        busquedaRepository.save(entity);
        sr.setSuccess(true);
        sr.AddMessage("Bsuqueda registrado");
        return  sr;
    }
    public ArrayList<BusquedaDto> readBusqueda(){
        var request = busquedaRepository.findAll();
        ArrayList <BusquedaDto> busquedaDtoArrayList = new ArrayList<BusquedaDto>();
        for ( Busqueda item: request) {
            BusquedaDto entity = ObjectMapperUtils.map(item, BusquedaDto.class);
            busquedaDtoArrayList.add(entity);
        }
        return  busquedaDtoArrayList;
    }
}
