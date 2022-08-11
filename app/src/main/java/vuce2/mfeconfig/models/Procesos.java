package vuce2.mfeconfig.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "procesos")
public class Procesos {
    @Id
    @NotNull
    private String id;

    private String procedimiento;

    private String codigoTupa;

    public Procesos(String id, String procedimiento, String codigoTupa) {
        this.id = id;
        this.procedimiento = procedimiento;
        this.codigoTupa = codigoTupa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getCodigoTupa() {
        return codigoTupa;
    }

    public void setCodigoTupa(String codigoTupa) {
        this.codigoTupa = codigoTupa;
    }
}
