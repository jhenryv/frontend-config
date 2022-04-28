package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleContentGroup {
    @Id
    private Object id;
    private Object idUser;
    private String name;
    private String icon;
    private String url;
    private Boolean visible;
    private String contentDefault;
    private ArrayList<SubModules> subModules;
    private ArrayList<ModuleContent> contents;
}
