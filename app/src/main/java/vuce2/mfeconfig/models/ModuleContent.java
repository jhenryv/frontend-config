package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleContent {
    @Id
    private Object id;
    private String name;
    private String title;
    private String subTitle;
    private ArrayList<ModuleContentItem> items;
}
