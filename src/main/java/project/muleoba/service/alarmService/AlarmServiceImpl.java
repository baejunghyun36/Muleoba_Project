package project.muleoba.service.alarmService;

import org.springframework.stereotype.Service;
import project.muleoba.domain.Alarm;
import project.muleoba.domain.Item;
import project.muleoba.repository.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class AlarmServiceImpl implements AlarmService{

    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveAlarm(Long IID) {
        Alarm al = new Alarm();
        al.setReadStatus(false);
        Item item = itemRepository.findByiID(IID);
        al.setUser(item.getUser());
    }
}
