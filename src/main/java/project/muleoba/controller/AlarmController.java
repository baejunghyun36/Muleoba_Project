package project.muleoba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.muleoba.service.alarmService.AlarmService;
import project.muleoba.vo.AlarmToReact;

import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/muleoba/get/alarm/list") // 알람 가져오기
    public List<AlarmToReact> getAlarmList(@RequestBody Map<String, String> map) {
        Long uid = parseLong(String.valueOf(map.get("uID")));
        return alarmService.findAllAlarmList(uid);
    }

    @GetMapping("/muleoba/alarm/isread") // 알람 읽기
    public void alarmRead(@RequestParam Long alarmNum) {
        alarmService.readAlarm(alarmNum);
    }

    @GetMapping("/muleoba/alarm/alldelete") // 알람 전체삭제
    public void allDeleteAlarm(@RequestParam Long uID) {
        alarmService.allDeleteAlarm(uID);
    }

    @GetMapping("/muleoba/alarm/selectdelete") // 알람 선택삭제
    public void selectDeleteAlarm(@RequestParam Long uID) {
        alarmService.selectDeleteAlarm(uID);
    }


}
