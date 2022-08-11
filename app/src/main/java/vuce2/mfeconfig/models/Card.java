package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Getter
@Setter
public class Card {
    @Id
    private String id;
    private String title;
    private Boolean visible;
    private String tipoCampo;
    private ArrayList<Items> items;
    private Boolean modify;
}
