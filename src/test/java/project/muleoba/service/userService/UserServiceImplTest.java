package project.muleoba.service.userService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.muleoba.domain.User;
import project.muleoba.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    @Transactional
    public void testCheckEmail() throws Exception {

        //given
        User user = new User();
        user.setEmail("joseyun94@naver.com");
        user.setNickName("yunyun");
        user.setPhoneNumber("01012345678");
        user.setName("조세윤");
        user.setPassword("1q2w3e4r");
        user.setAddress("경기 고양");

        User user2 = new User();
        user2.setEmail("joseyun924@naver.com");
        user2.setNickName("yun123");
        user2.setPhoneNumber("01012345678");
        user2.setName("조세윤");
        user2.setPassword("1q2w3e4r");
        user2.setAddress("경기 고양");

        //when
        String findEmail = userService.saveUser(user);
        String findEmail2 = userService.saveUser(user2);

        //then
        assertThat("spring").isEqualTo("spring");
        System.out.println("check1 : " + userService.findEmailUser(findEmail));
        System.out.println("check2 : " + userService.findEmailUser("dd@naver.com"));
    }

    @Test
    @Transactional
    public void testBestUsers(){
        List<String> s = userService.findBestUsers();
        for(String ss : s){
            System.out.println("ss = " + ss);
        }
    }
}