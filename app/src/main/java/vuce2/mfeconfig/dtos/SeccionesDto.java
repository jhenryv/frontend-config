package vuce2.mfeconfig.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SeccionesDto {
    private String id;
    private String name;
    private String status;
    private String observationMessage;
    private Boolean secondOption;
    private String idSecondEvaluator;
}
