package vuce2.mfeconfig.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Secciones {

    @Id
    private Object id;
    private String name;
    private String status;
    private String observationMessage;
    private Boolean secondOption;
    private String idSecondEvaluator;
}
