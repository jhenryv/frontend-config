package vuce2.mfeconfig.models;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericJsonConfigData {

    public GenericJsonConfigData() {
        datos = new ArrayList<HashMap<String, String>>();
    }
    private String codigo;
    //private ArrayList<HashMap<String, String>> datos;
    private ArrayList<HashMap<String, String>> datos;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<HashMap<String, String>> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<HashMap<String, String>> datos) {
        this.datos = datos;
    }
}
