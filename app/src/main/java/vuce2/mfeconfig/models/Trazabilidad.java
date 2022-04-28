package vuce2.mfeconfig.models;

import lombok.Data;


@Data
public class Trazabilidad {
    private String fechaRegistro;
    private String etapa;
    private String descripcion;
    private String fechaIngreso;
    private String fechaSalida;
    private String secuencia;
    private String area;
    private String subArea;
}
