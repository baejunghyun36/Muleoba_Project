package project.muleoba.service.alarmService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.muleoba.domain.Alarm;
import project.muleoba.domain.Item;
import project.muleoba.repository.AlarmRepository;
import project.muleoba.vo.AlarmToReact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Long saveAlarm(Long IID) {
        Alarm al = new Alarm();
        al.setReadStatus(true);
        al.setDeleteStatus(false);
        Item item = entityManager.find(Item.class, IID);
        al.setUser(item.getUser());
        entityManager.persist(al);
        System.out.println("!!al.getID : "+al.getAID());
        return al.getAID();
    }

    @Override
    @Transactional
    public List<AlarmToReact> findAllAlarmList(Long uid) {
        return alarmRepository.findAllAlarmList(uid);
    }

    @Override
    @Transactional
    public void readAlarm(Long aid) {
        alarmRepository.readAlarm(aid);
    }

    @Override
    @Transactional
    public void allDeleteAlarm(Long uid) {
        alarmRepository.allDeleteAlarm(uid);
    }

    @Override
    @Transactional
    public void selectDeleteAlarm(Long uid) {
        alarmRepository.selectDeleteAlarm(uid);
    }

}
