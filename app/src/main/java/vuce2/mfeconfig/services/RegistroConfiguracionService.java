package vuce2.mfeconfig.services;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.mfeconfig.dtos.RegistroConfiguracionDto;
import vuce2.mfeconfig.models.*;
import vuce2.mfeconfig.repositories.RegistroConfiguracionRepository;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RegistroConfiguracionService {
    @Resource
    RegistroConfiguracionRepository registroConfiguracionRepository;

    public StatusResponse insertRegistroConfiguracion(RegistroConfiguracionDto request){
        StatusResponse sr = new StatusResponse();


        List<RegistroConfiguracion> listaRegistroConfigUpdate=registroConfiguracionRepository.findByIdEntidadAndIdSeccion(request.getIdEntidad(),request.getIdSeccion());
        for ( RegistroConfiguracion item: listaRegistroConfigUpdate){
            item.setVersionVisible(false);
            registroConfiguracionRepository.save(item);
        }

        Date date = new Date();
        String strDateFormat = "dd/MM/yy hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);

        RegistroConfiguracion entity = ObjectMapperUtils.map(request, RegistroConfiguracion.class);
        entity.setId(new ObjectId());
        entity.setDateCreated(formattedDate);
        for ( SubSeccionesRegistro item: entity.getSubSeccionesRegistro()){
            for ( OpcionSeleccionada itemOpcion: item.getOpcionSeleccionada()){
                itemOpcion.setId(new ObjectId());
            }
            item.setId(new ObjectId());
        }
        try{
            registroConfiguracionRepository.save(entity);
            sr.AddMessage("Registro de configuración guardado correctamente");
            sr.setSuccess(true);
        } catch (Exception ex){
            sr.AddMessage("Error en el registro de configuración");
            sr.setSuccess(false);
        }


        return  sr;
    }
}
