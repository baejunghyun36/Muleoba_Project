package project.muleoba.service.alarmService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.muleoba.domain.Alarm;
import project.muleoba.domain.Item;
import project.muleoba.repository.AlarmRepository;
import project.muleoba.repository.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    ItemRepository itemRepository;
    AlarmRepository alarmRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Long saveAlarm(Long IID) {
        Alarm al = new Alarm();
        al.setReadStatus(false);
        Item item = entityManager.find(Item.class, IID);
        al.setUser(item.getUser());
        entityManager.persist(al);
        System.out.println("!!al.getID : "+al.getAID());
        return al.getAID();
    }

//    public Long getAID(Long IID) {
//    }
}
