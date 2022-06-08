package project.muleoba.service.transactionService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.muleoba.domain.*;
import project.muleoba.repository.AlarmRepository;
import project.muleoba.repository.ItemRepository;
import project.muleoba.repository.TransactionRepository;
import project.muleoba.repository.UserRepository;
import project.muleoba.service.alarmService.AlarmService;
import project.muleoba.vo.TransactionVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void save(Long IID, Long requestIID) {
        Transaction t = new Transaction();
        t.setAlarm(entityManager.find(Alarm.class, alarmService.saveAlarm(IID)));
        t.setRequestIID(requestIID);
        t.setItem(itemRepository.findByiID(IID));
        t.setStatus(Status.Normal);
        transactionRepository.save(t);
    }

    @Override
    public Transaction findTransaction(Long tID) {
        Transaction t = transactionRepository.findBytID(tID);
        entityManager.refresh(t);
        return t;
    }

    @Override
    @Transactional
    public void deleteTransaction(Long requestiID, Long iID) {
        transactionRepository.deleteById(transactionRepository.findDeleteTID(requestiID, iID));
        Item item = itemRepository.findByiID(iID);
        item.setRequestNum(item.getRequestNum() - 1);
        itemRepository.save(item);
    }

    @Override
    public List<TransactionVO> transactionList(Long iID) {  //자기 특정 아이템으로 교환 신청이 들어온 물품들.
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionVO> transactionVOList = new ArrayList<>();
        for(Transaction t : transactionList ){
            if(t.getItem().getIID()==iID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
              //  transactionVO.setAlarm(t.getAlarm());
                transactionVO.setRequestIID(t.getRequestIID());
                transactionVO.setItem(t.getItem());
                transactionVO.setRequestTime(t.getRequestTime());
                transactionVO.setStatus(t.getStatus());
                transactionVOList.add(transactionVO);
            }
        }
        return transactionVOList;
    }

    @Transactional
    @Override//교환 승낙
    public void acceptRequest(Long iid, Long requestIID) {
        Transaction t = transactionRepository.findByiidRequestIID(iid, requestIID);
        t.setStatus(Status.Reservation);
        transactionRepository.save(t);
    }

    @Override
    public void completeRequest(Long iid, Long requestIID) {
        Transaction t = transactionRepository.findByiidRequestIID(iid, requestIID);
        t.setStatus(Status.Complete);
        transactionRepository.save(t);
    }

    @Override
    public List<TransactionVO> completeRequestList(Long uID) {

        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionVO> transactionVOList = new ArrayList<>();
        for(Transaction t : transactionList ){
            entityManager.refresh(t);
            if(t.getStatus()==Status.Complete&&t.getItem().getUser().getUID()==uID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
                transactionVO.setRequestIID(t.getRequestIID());
                transactionVO.setItem(t.getItem());
                transactionVO.setRequestTime(t.getRequestTime());
                transactionVO.setStatus(t.getStatus());
                transactionVOList.add(transactionVO);
            }
        }

        return transactionVOList;
    }

    @Override
    public List<TransactionVO> requestMyItems(Long uID) {

        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionVO> transactionVOList = new ArrayList<>();
        for(Transaction t : transactionList ){
            entityManager.refresh(t);
            if(t.getStatus()==Status.Normal&&t.getItem().getUser().getUID()==uID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
                transactionVO.setRequestIID(t.getRequestIID());
                transactionVO.setItem(t.getItem());
                transactionVO.setRequestTime(t.getRequestTime());
                transactionVO.setStatus(t.getStatus());
                transactionVOList.add(transactionVO);
            }
        }
        return transactionVOList;

    }

    @Transactional
    @Override
    public void requestmodal(Long uID, Long requestiid, Long iid) {
        Item item = itemRepository.findByiID(iid);
        item.setRequestNum(item.getRequestNum()+1);
        itemRepository.save(item);
        Alarm alarm = new Alarm();
        Transaction transaction = new Transaction();
        alarm.setReadStatus(true);
        alarm.setDeleteStatus(false);
        alarm.setUser(userRepository.findByuID(itemRepository.findByiID(iid).getUser().getUID()));
        alarm.setTransaction(transaction);
        transaction.setRequestIID(requestiid);
        transaction.setAlarm(alarm);
        transaction.setItem(itemRepository.findByiID(iid));
        alarmRepository.save(alarm);
        transactionRepository.save(transaction);

    }
}
