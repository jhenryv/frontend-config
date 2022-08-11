package vuce2.mfeconfig.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RegistroConfiguracionDto {
    private String idEntidad;
    private String idSeccion;
    private String userCreated;
    private Integer version;
    private Boolean versionVisible;
    private ArrayList<SubSeccionesRegistroDto> subSeccionesRegistro;
}
