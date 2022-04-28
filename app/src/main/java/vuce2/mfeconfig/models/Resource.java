package vuce2.mfeconfig.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;

//@Setter
//@Getter
public class Resource {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<HashMap<String, Object>> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<HashMap<String, Object>> datos) {
        this.datos = datos;
    }

    private String codigo;
    private String tipo;
    private ArrayList<HashMap<String, Object>> datos;

}
