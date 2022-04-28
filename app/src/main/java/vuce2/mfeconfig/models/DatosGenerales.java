package vuce2.mfeconfig.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DatosGenerales {
    private String tipoDocumento;
    private String actividad;
    private String departamento;
    private String distrito;
    private String referencia;
    private String celular;
    private String numeroDocumento;
    private String razonSocial;
    private String domicilioLegal;
    private String telefono;
    private String fax;
    private String correoElectronico;
    private String representanteLegal;
    private ArrayList<Trazabilidad> trazabilidades;
}
