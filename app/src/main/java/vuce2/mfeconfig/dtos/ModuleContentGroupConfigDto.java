package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ModuleContentGroupConfigDto {
    private String id;
    private String idUser;
    private String name;
    private String icon;
    private String url;
    private Boolean visible;
    private String contentDefault;
}
