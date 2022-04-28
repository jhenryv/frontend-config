package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class SubModules {
    @Id
    private Object id;
    private String icon;
    private String name;
    private String content;
}
