package project.muleoba.service.transactionService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.muleoba.domain.Status;
import project.muleoba.domain.Transaction;
import project.muleoba.repository.ItemRepository;
import project.muleoba.repository.TransactionRepository;
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

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void save(Long IID, Long requestIID) {
        Transaction t = new Transaction();
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
    public void deleteTransaction(Long tID) {
        transactionRepository.deleteById(tID);
    }

    @Override
    public List<TransactionVO> transactionList(Long iID) {  //자기 특정 아이템으로 교환 신청이 들어온 물품들.
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionVO> transactionVOList = new ArrayList<>();
        for(Transaction t : transactionList ){
            if(t.getItem().getIID()==iID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
                transactionVO.setAlarm(t.getAlarm());
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
            if(t.getStatus()==Status.Complete&&t.getItem().getUser().getUID()==uID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
                transactionVO.setAlarm(t.getAlarm());
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
            if(t.getStatus()==Status.Normal&&t.getItem().getUser().getUID()==uID) {
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setTID(t.getTID());
                transactionVO.setAlarm(t.getAlarm());
                transactionVO.setItem(t.getItem());
                transactionVO.setRequestTime(t.getRequestTime());
                transactionVO.setStatus(t.getStatus());
                transactionVOList.add(transactionVO);
            }
        }
        return transactionVOList;

    }
}
