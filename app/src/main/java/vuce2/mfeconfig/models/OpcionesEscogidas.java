package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "opcionesEscogidas")
public class OpcionesEscogidas {
    @Id
    private Object id;
    private String idOpcion;
    private String idEntidad;
    private String idSeccion;
    private String idSubSeccion;
    private String dateCreated;
    private String userCreated;


}
