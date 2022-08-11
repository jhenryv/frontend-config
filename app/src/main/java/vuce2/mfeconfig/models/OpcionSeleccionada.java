package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class OpcionSeleccionada {
    @Id
    private Object id;
    private String idOpcion;
    private String value;
    private Boolean seleccionado;
}
