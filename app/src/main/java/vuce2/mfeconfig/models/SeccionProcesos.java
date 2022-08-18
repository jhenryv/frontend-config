package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Document(collection = "seccionProcesos")
public class SeccionProcesos {
    @Id
    @NotNull
    private String id;
    private String name;
    private Integer orden;


}
