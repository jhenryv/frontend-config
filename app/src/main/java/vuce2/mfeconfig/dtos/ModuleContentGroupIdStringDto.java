package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleContentGroupIdStringDto {
    private String id;
    private String idUser;
    private String name;
    private String icon;
    private String url;
    private Boolean visible;
    private String contentDefault;
    private ArrayList<SubModulesDto> subModules;
    private ArrayList<ModuleContentIdStringDto> contents;
}
