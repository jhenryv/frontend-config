package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Getter
@Setter
public class SubSeccionesRegistro {
    @Id
    private Object id;
    private String idSubSeccion;
    private ArrayList<OpcionSeleccionada> opcionSeleccionada;
}
