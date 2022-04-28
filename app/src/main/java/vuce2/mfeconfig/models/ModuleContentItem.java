package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ModuleContentItem {
    @Id
    private Object id;
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
