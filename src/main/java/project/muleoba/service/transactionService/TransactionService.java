package project.muleoba.service.transactionService;


import org.springframework.stereotype.Service;
import project.muleoba.domain.Transaction;
import project.muleoba.vo.TransactionVO;

import java.util.List;

@Service
public interface TransactionService {


    void save(Long IID, Long requestIID);

    Transaction findTransaction(long tID);

    void deleteTransaction(Long tID);

    List<TransactionVO> transactionList(Long iID);

    void acceptRequest(Long iid, Long requestIID);
}
