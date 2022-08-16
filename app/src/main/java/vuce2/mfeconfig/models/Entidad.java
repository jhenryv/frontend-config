package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "entidad")
public class Entidad {
    @Id
    @NotNull
    private String id;

    private String nombreEntidad;

    private String idAdministrado;
}
