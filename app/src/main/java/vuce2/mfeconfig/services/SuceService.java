package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.SuceDto;
import vuce2.mfeconfig.models.Suce;
import vuce2.mfeconfig.repositories.SuceRepository;

import javax.annotation.Resource;

@Service
@Slf4j
public class SuceService {

    @Resource
    private SuceRepository suceRepository;

    public StatusResponse insertSuce(Suce request){
        StatusResponse sr = new StatusResponse();
        Suce entity = ObjectMapperUtils.map(request, Suce.class);
        entity.setId(new ObjectId());
        suceRepository.save(entity);
        sr.setSuccess(true);
        sr.AddMessage("Suce registrado");
        return  sr;
    }
    public SuceDto readSuce(){
        var request = suceRepository.findAll();
        SuceDto entity = ObjectMapperUtils.map(request.get(0), SuceDto.class);
        return  entity;
    }

}
