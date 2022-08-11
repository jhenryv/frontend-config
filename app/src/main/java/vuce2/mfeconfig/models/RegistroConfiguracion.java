package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@Document(collection = "registroConfiguracion")
public class RegistroConfiguracion {
    @Id
    private Object id;
    private String idEntidad;
    private String idSeccion;
    private String dateCreated;
    private String userCreated;
    private Integer version;
    private Boolean versionVisible;
    private ArrayList<SubSeccionesRegistro> subSeccionesRegistro;
}
