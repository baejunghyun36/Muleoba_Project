package project.muleoba.service.transactionService;


import org.springframework.stereotype.Service;
import project.muleoba.domain.Transaction;
import project.muleoba.vo.TransactionVO;

import java.util.List;

@Service
public interface TransactionService {


    void save(Long IID, Long requestIID);

    Transaction findTransaction(Long tID);

    void deleteTransaction(Long requestiID, Long iID);

    List<TransactionVO> transactionList(Long iID);

    void acceptRequest(Long iid, Long requestIID);

    void completeRequest(Long iid, Long requestIID);

    List<TransactionVO> completeRequestList(Long uID);

    List<TransactionVO> requestMyItems(Long uID);

    void requestmodal(Long uID, Long requestiid, Long iid);
}
