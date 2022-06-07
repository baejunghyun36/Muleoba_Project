package project.muleoba.service.alarmService;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import project.muleoba.vo.AlarmToReact;

import java.util.List;

@Service
public interface AlarmService {

    Long saveAlarm(Long IID);
    List<AlarmToReact> findAllAlarmList(Long uid);
    void readAlarm(Long aid);
    void allDeleteAlarm(Long uid);

    void selectDeleteAlarm(Long uID);
}
