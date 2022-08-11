package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;
import vuce2.mfeconfig.dtos.OpcionesEscogidasDto;
import vuce2.mfeconfig.models.OpcionesEscogidas;
import vuce2.mfeconfig.repositories.OpcionesEscogidasRepository;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class OpcionesEscogidasService {

    @Resource
    OpcionesEscogidasRepository opcionesEscogidasRepository;

    public StatusResponse insertOpcionesEscogidas(OpcionesEscogidasDto request){
        StatusResponse sr = new StatusResponse();

        Date date = new Date();
        String strDateFormat = "dd/MM/yy hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);

        OpcionesEscogidas entity = ObjectMapperUtils.map(request, OpcionesEscogidas.class);
        entity.setId(new ObjectId());
        entity.setDateCreated(formattedDate);
        opcionesEscogidasRepository.save(entity);
        sr.AddMessage("Opciones Escogidas guardado correctamente");
        sr.setSuccess(true);
        return  sr;
    }
}
