package vuce2.mfeconfig.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
public class Suce {
    @Id
    private Object id;

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
