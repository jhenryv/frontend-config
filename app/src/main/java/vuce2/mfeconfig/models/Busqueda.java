package vuce2.mfeconfig.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Busqueda {
    @Id
    private Object id;
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
