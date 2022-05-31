package project.muleoba.service.userService;


import org.springframework.stereotype.Service;
import project.muleoba.domain.User;

@Service
public interface UserService {

    String saveUser(User user);
    boolean findEmailUser(String email); // 이메일 중복체크
    boolean findNickNameUser(String nickName); // 이메일 중복체크

    User findByuID(Long uID);
}
