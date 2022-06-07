package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Alarm;
import project.muleoba.vo.AlarmToReact;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    // 기존
    @Query("SELECT a.aID as alarmNum, i.iID as itemNum, i.item as itemName, t.requestIID as requestItemNum, u.nickName as requestNickName, r.item as requestItem, a.readStatus as isRead, DATE_FORMAT(a.alarmTime, '%y-%m-%d') as timeAl\n" +
            "FROM Transaction t JOIN t.alarm a JOIN t.item i JOIN a.user m, Item r JOIN r.user u\n" +
            "WHERE t.requestIID = r.iID AND a.deleteStatus = false AND m.uID = :uID")
    List<AlarmToReact> findAllAlarmList(@Param("uID") Long uid);

    @Modifying
    @Query("UPDATE Alarm a SET a.readStatus = false WHERE a.aID = :aid")
    void readAlarm(@Param("aid") Long aid);

    @Modifying
    @Query("UPDATE Alarm a SET a.deleteStatus = true WHERE a.user.uID = :uid")
    void allDeleteAlarm(@Param("uid") Long uid);

    @Modifying
    @Query("UPDATE Alarm a SET a.deleteStatus = true WHERE a.readStatus = false AND a.user.uID = :uid")
    void selectDeleteAlarm(@Param("uid") Long uid);

}
