package vuce2.mfeconfig.dtos;


import lombok.Getter;
import lombok.Setter;
import vuce2.mfeconfig.models.Card;


import java.util.ArrayList;
@Getter
@Setter
public class SubSeccionesProcesosDto {
    private String id;
    private String name;
    private ArrayList<Card> card;
}
