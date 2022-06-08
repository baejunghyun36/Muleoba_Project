package project.muleoba.vo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ItemVO {

    private Long iID;
    private String item;
    private String category;
    private String content;
    private String photo;
    private String nickName;
    private Long requestNum;
    private LocalDateTime uploadTime;
    private String address;
    private Long requestiID;
    private Long itemUid;




}
