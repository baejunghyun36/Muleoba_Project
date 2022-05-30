package project.muleoba.vo;

import lombok.Getter;
import lombok.Setter;
import project.muleoba.domain.Alarm;
import project.muleoba.domain.Item;
import project.muleoba.domain.Status;

import java.time.LocalDateTime;


@Getter
@Setter
public class TransactionVO {


    private Long tID;
    private Long requestIID;
    private LocalDateTime requestTime;
    private Status status;
    private Item item;
    private Alarm alarm;

}
