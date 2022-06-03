package project.muleoba.service.transactionService;

import com.sun.istack.NotNull;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import project.muleoba.domain.Item;
import project.muleoba.domain.Status;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;
import project.muleoba.repository.ItemRepository;
import project.muleoba.repository.TransactionRepository;
import project.muleoba.repository.UserRepository;
import project.muleoba.vo.TransactionVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class TransactionServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;


    @Test
    @Transactional
    public void save(){
        User user = new User();
        user.setAddress("11");
        user.setEmail("11");
        user.setName("11");
        user.setPassword("11");
        user.setNickName("11");
        user.setPhoneNumber("11");
        user.setName("11");


        User user2 = new User();
        user2.setAddress("22");
        user2.setEmail("22");
        user2.setName("22");
        user2.setPassword("22");
        user2.setNickName("22");
        user2.setPhoneNumber("22");
        user2.setName("22");

        userRepository.save(user);
        userRepository.save(user2);

        Item item = new Item();
        item.setItem("의자");
        item.setCategory("가구");
        item.setPhoto("url");
        item.setUser(user);

        Item item2 = new Item();
        item2.setItem("책상");
        item2.setCategory("가구");
        item2.setPhoto("url");
        item2.setUser(user2);

        itemRepository.save(item);
        itemRepository.save(item2);

        transactionService.save(1L, 2L);
    }


    @Test
    @Transactional
    public void testTransaction() {
        transactionService.save(1L, 2L);

        Transaction t = transactionService.findTransaction(1L);
        System.out.println(t.getTID());

        System.out.println("요청 아이템 번호 : "+ t.getRequestIID());
        System.out.println("요청 시간 : " + t.getRequestTime());
        System.out.println("게시물 아이템 번호 : " + t.getItem().getIID());
        System.out.println("게시물 아이템 글쓴이 : " + t.getItem().getUser().getUID());
        System.out.println("현재 거래 상태 : " + t.getStatus());

    }

    @Test
    @Transactional
    public void testTransactionDelete() { //거래 삭제 테스트

        transactionService.save(1L, 2L);
        transactionService.save(3L, 4L);
        transactionService.deleteTransaction(1L);
    }


    @Test
    @Transactional
    public void testTransactionAcceptRequest(){ // @PostMapping("/5")  교환 수락 테스트

        save();
        transactionService.save(1L, 2L);
        transactionService.acceptRequest(1L, 2L);
        Transaction transaction = transactionRepository.findByiidRequestIID(1L, 2L);
        Assertions.assertThat(transaction.getStatus()).isEqualTo(Status.Reservation);
    }

    @Test
    @Transactional // @PostMapping("/6")교환 완료 로직
    public void testCompleteRequest(){
        save();
        transactionService.completeRequest(1L, 2L);
        Transaction transaction = transactionRepository.findByiidRequestIID(1L, 2L);
        Assertions.assertThat(transaction.getStatus()).isEqualTo(Status.Complete);
    }

    @Test
    @Transactional //@PostMapping("/7")마이페이지에서 내가 올린 물품 거래 완료된 데이터 리스트
    public void testCompleteRequestList(){


        save();
        testCompleteRequest();
        List<TransactionVO> transactionVOList = transactionService.completeRequestList(1L); //사용자 uID

        for (TransactionVO t : transactionVOList) {
            System.out.println("거래 TID : " + t.getTID());
            System.out.println("요청 물품 IID : " + t.getRequestIID());
            System.out.println("내 물품 IID = " + t.getItem().getIID());
            System.out.println("t.getRequestTime() = " + t.getRequestTime());
            System.out.println("t.getStatus() = " + t.getStatus());
        }
    }

    @Test
    @Transactional   //  @PostMapping("/8") 내가 교환신청한 물품들
    public void testRequestMyItems(){

        save();
        List<TransactionVO> transactionVOList = transactionService.requestMyItems(1L);

        for (TransactionVO t : transactionVOList) {
            System.out.println("거래 TID : " + t.getTID());
            System.out.println("요청 물품 IID : " + t.getRequestIID());
            System.out.println("내 물품 IID = " + t.getItem().getIID());
            System.out.println("t.getRequestTime() = " + t.getRequestTime());
            System.out.println("t.getStatus() = " + t.getStatus());
        }
    }


}