package project.muleoba.service.itemService;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import project.muleoba.domain.Item;
import project.muleoba.domain.User;
import project.muleoba.repository.ItemRepository;
import project.muleoba.repository.UserRepository;
import project.muleoba.service.transactionService.TransactionService;
import project.muleoba.vo.ItemVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class ItemServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ItemService itemService;

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
        user.setAddress("서울특별시 강남구");


        User user2 = new User();
        user2.setAddress("22");
        user2.setEmail("22");
        user2.setName("22");
        user2.setPassword("22");
        user2.setNickName("22");
        user2.setPhoneNumber("22");
        user2.setName("22");
        user2.setAddress("서울특별시 영등포구");

        userRepository.save(user);
        userRepository.save(user2);

        Item item = new Item();
        item.setItem("의자");
        item.setCategory("가구");
        item.setPhoto("1");
        item.setUser(user);

        Item item2 = new Item();
        item2.setItem("책상");
        item2.setCategory("가구");
        item2.setPhoto("2");
        item2.setUser(user2);


        Item item3 = new Item();
        item3.setItem("샴푸");
        item3.setCategory("생활용품");
        item3.setPhoto("3");
        item3.setUser(user2);

        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);





        transactionService.save(1L, 2L);

    }



    @Test
    @Transactional
    public void testItem() throws Exception{
        User user = new User();
        user.setAddress("11");
        user.setEmail("11");
        user.setName("11");
        user.setPassword("11");
        user.setNickName("11");
        user.setPhoneNumber("11");
        user.setName("11");

        userRepository.save(user);
        User find = userRepository.findByName("11");

        Item item = new Item();
        item.setItem("22");
        item.setCategory("22");
        item.setPhoto("22");
        item.setUser(user);

        itemRepository.save(item);
        Item findItem = itemRepository.findByPhoto("22");
        entityManager.refresh(findItem);

        System.out.println(user.getName());
        System.out.println(find.getName());
        System.out.println(item.getUser().getName());
        System.out.println(findItem.getUser().getName());
        System.out.println(findItem.getUploadTime());
        System.out.println(findItem.getRequestNum());
        System.out.println(user.getItems().size());

        Assertions.assertThat(find.getName()).isEqualTo(user.getAddress());
        Assertions.assertThat(findItem.getPhoto()).isEqualTo(item.getPhoto());
    }

    @Test
    public void mainList(){
        save();
        List<ItemVO> itemVOList = itemService.itemList("서울특별시 강남구");
        for(ItemVO i : itemVOList){
            System.out.println("i.getPhoto() = " + i.getPhoto());
            System.out.println("i.getCategory() = " + i.getCategory());
            System.out.println("i.getRequestNum() = " + i.getRequestNum());
            System.out.println("i.getItem() = " + i.getItem());
        }
       // itemService.itemCategoryList(category);
    }

    @Test
    public void mainListCategory(){

        List<ItemVO> itemVOList = itemService.itemCategoryList("가구", "서울특별시 강남구");
        for(ItemVO i : itemVOList){
            System.out.println("i.getPhoto() = " + i.getPhoto());
            System.out.println("i.getCategory() = " + i.getCategory());
            System.out.println("i.getRequestNum() = " + i.getRequestNum());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getUploadTime() = " + i.getUploadTime());
            System.out.println();
        }
        // itemService.itemCategoryList(category);
    }

    @Test
    public void mylist(){


        List<ItemVO> itemVOList = itemService.itemMyList(1L, "서울특별시 강남구");
        for(ItemVO i : itemVOList){
            System.out.println("i.getPhoto() = " + i.getPhoto());
            System.out.println("i.getCategory() = " + i.getCategory());
            System.out.println("i.getRequestNum() = " + i.getRequestNum());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getUploadTime() = " + i.getUploadTime());
            System.out.println();
        }
        // itemService.itemCategoryList(category);
    }

    @Test
    public void successlist(){

        List<ItemVO> itemVOList = itemService.itemSuccessList(1L, "서울특별시 강남구");
        for(ItemVO i : itemVOList){
            System.out.println("i.getPhoto() = " + i.getPhoto());
            System.out.println("i.getCategory() = " + i.getCategory());
            System.out.println("i.getRequestNum() = " + i.getRequestNum());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getUploadTime() = " + i.getUploadTime());
            System.out.println();
        }
        // itemService.itemCategoryList(category);
    }

    @Test
    public void myItemRequestIId(){
        List<ItemVO> itemVOList= itemService.myItemRequestIId(1L);
        for(ItemVO i : itemVOList){
            System.out.println("i.getIID() = " + i.getIID());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getAddress() = " + i.getAddress());
            System.out.println("i.getRequestiID() = " + i.getRequestiID());
            System.out.println("i.time() = " + i.getUploadTime());
            System.out.println();
        }
    }
    @Test
    public void requestItem(){
        List<ItemVO> itemVOList= itemService.requestItem(1L);
        for(ItemVO i : itemVOList){
            System.out.println("i.getIID() = " + i.getIID());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getAddress() = " + i.getAddress());
            System.out.println("i.time() = " + i.getUploadTime());
            System.out.println();
        }
    }

    @Test
    public void searchItem(){

        List<ItemVO> itemVOList = itemService.searchItem("의", 1L);
        for(ItemVO i : itemVOList){
            System.out.println("i.getIID() = " + i.getIID());
            System.out.println("i.getItem() = " + i.getItem());
            System.out.println("i.getAddress() = " + i.getAddress());
            System.out.println("i.time() = " + i.getUploadTime());
            System.out.println();
        }
    }
}