package vuce2.mfeconfig.models;

import lombok.Data;

@Data
public class Notificaciones {
    private String fechaRegistro;
    private String notificacion;
    private String mensaje;
    private String estado;
    private String fechaRespuesta;
}
