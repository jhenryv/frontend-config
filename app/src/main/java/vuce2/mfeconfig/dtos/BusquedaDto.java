package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusquedaDto {
    private String id;
    private String tupa;
    private String suce;
    private String expediente;
    private String solicitante;
    private String inicioTramite;
    private String plazo;
    private String estado;
    private Boolean esImportante;
    private String formato;
    private String nombre;
    private String area;
    private String plazoExtensible;
    private String fechaActualizacion;
}
