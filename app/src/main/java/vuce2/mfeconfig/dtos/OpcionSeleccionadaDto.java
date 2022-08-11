package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcionSeleccionadaDto {
    private String idOpcion;
    private String value;
    private Boolean seleccionado;
}
