package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleContentItemIdStringConfigDto {
    private String id;
    private String type;
    private String remoteName;
    private String ngName;
    private String componentName;
    private String colMin;
    private String colMax;
    private String rowMin;
    private String rowMax;
    private Boolean hidden;
}
