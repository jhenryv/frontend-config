package vuce2.mfeconfig.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Items {
    @Id
    private String id;
    private String idTitle;
    private String title;
}
