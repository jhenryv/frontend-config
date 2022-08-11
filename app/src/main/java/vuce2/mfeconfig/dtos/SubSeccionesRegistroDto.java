package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SubSeccionesRegistroDto {
    private String idSubSeccion;
    private ArrayList<OpcionSeleccionadaDto> opcionSeleccionada;
}
