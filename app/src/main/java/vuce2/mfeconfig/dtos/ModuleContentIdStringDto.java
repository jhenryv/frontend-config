package vuce2.mfeconfig.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleContentIdStringDto {
    private String id;
    private String name;
    private String title;
    private String subTitle;
    private ArrayList<ModuleContentItemIdStringConfigDto> items;
}
