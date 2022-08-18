package vuce2.mfeconfig.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Document(collection = "subSeccionesProcesos")
public class SubSeccionesProcesos {
    @Id
    @NotNull
    private String id;
    private String name;
    private Boolean visible;
    private String idSeccion;
    private ArrayList<Card> card;
    private Integer orden;

}
