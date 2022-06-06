package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.muleoba.service.alarmService.AlarmService;
import project.muleoba.vo.AlarmToReact;

import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/muleoba/get/alarm/list")
    public List<AlarmToReact> getAlarmList(@RequestBody Map map) {
        Long uid = parseLong(String.valueOf(map.get("uID")));
        return alarmService.findAllAlarmList(uid);
    }
}
