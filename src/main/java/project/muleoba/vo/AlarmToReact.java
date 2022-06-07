package project.muleoba.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Date;


public interface AlarmToReact {

    Long getAlarmNum(); // 알람 번호
    Long getItemNum(); // 내 아이템 번호
    String getItemName(); // 내 아이템 이름

    Long getRequestItemNum(); // 교환요청 아이템 번호
    String getRequestNickName(); // 교환요청자 닉네임
    String getRequestItem(); // 교환요청자 아이템 이름
    Boolean getIsRead(); // 읽음 유무
    String getTimeAl(); // 날짜

}
