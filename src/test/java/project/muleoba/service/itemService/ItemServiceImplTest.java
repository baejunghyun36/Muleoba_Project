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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
}