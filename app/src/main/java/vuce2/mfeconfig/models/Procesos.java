package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "procesos")
public class Procesos {
    @Id
    @NotNull
    private String id;

    private String procedimiento;

    private String codigoTupa;

    private String idEntidad;


}
