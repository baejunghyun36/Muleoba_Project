package project.muleoba.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.muleoba.domain.Transaction;
import project.muleoba.service.transactionService.TransactionService;
import project.muleoba.vo.TransactionVO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/1") //본인 아이템, 상대방 아이템 거래 요청 저장.
    public void saveTransaction(Long IID, Long requestIID){
        //IID 본인 아이템, requestIID 상대방 아이템
        transactionService.save(IID, requestIID);
    }

    @PostMapping("/2") //거래 확인
    public Transaction findTransaction(Long tID){
        Transaction transaction = transactionService.findTransaction(tID);
        return transaction;
    }

    @PostMapping("/3") //거래삭제
    public void deleteTransaction(Long tID){
        transactionService.deleteTransaction(tID);
    }

    @PostMapping("/4")//특정 아이템에 교환 신청한 리스트
    public List<TransactionVO> transactionList(Long iID){
        List<TransactionVO> transactionList = transactionService.transactionList(iID);
        return transactionList;
    }

    @PostMapping("/5") //교환 수락
    public void acceptRequest(Long IID, Long requestIID){ //아이템 IID, 요청아이템 IID
        transactionService.acceptRequest(IID, requestIID);
    }




}
