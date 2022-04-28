package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;
import vuce2.mfeconfig.models.*;

import java.util.ArrayList;

@Getter
@Setter
public class SuceDto {
    private String id;
    private String suceNro;
    private String fechaRegistro;
    private String numeroExpediente;
    private ArrayList<String> tabs;
    private DatosGenerales datosGenerales;
    private ArrayList<Escritos> escritos;
    private ArrayList<Notificaciones> notificaciones;
    private ArrayList<Subsanaciones> subsanaciones;
    private ArrayList<DocumentosResolutivos> documentosResolutivos;
}
