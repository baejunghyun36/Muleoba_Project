package project.muleoba.vo;

import lombok.Getter;
import lombok.Setter;
import project.muleoba.domain.Status;

@Getter
@Setter
public class ItemVO {

    private Long iID;

    private String item;

    private String category;

    private String content;

    private String photo;

}
