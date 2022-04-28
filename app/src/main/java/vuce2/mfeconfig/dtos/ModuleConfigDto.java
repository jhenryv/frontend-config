package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleConfigDto {

    private String id = null;
    private String remoteEntry;
    private String remoteName;
    private String exposedModule;
    private String displayName;
    private String routePath;
    private String ngName;
    private String[] components;
}
