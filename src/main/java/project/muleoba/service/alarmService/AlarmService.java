package project.muleoba.service.alarmService;

import org.springframework.stereotype.Service;
import project.muleoba.vo.AlarmToReact;

import java.util.List;

@Service
public interface AlarmService {

    Long saveAlarm(Long IID);
    List<AlarmToReact> findAllAlarmList(Long uid);
}
