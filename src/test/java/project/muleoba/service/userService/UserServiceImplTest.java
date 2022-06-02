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
import project.muleoba.token.TokenService;

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
        int n = userService.saveUser(user);
        int n2 = userService.saveUser(user2);
        //int n3 = userService.loginUser("joseyun94@naver.com","1q2w3e4r");
        
        //then
        assertThat("spring").isEqualTo("spring");
        System.out.println("JWT_Token 값 확인 : " + userService.getToken());
        System.out.println("JWT_Token 값 내부 확인 : " + TokenService.getUID(userService.getToken()));
        System.out.println("check1 : " + userService.findEmailUser(user.getEmail()));
        System.out.println("check2 : " + userService.findEmailUser("dd@naver.com"));
    }
}