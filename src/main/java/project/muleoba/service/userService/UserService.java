package project.muleoba.service.userService;


import org.springframework.stereotype.Service;
import project.muleoba.domain.User;
import project.muleoba.vo.UserVO;

import java.util.List;

@Service
public interface UserService {

    int saveUser(User user); // 회원가입
    boolean findEmailUser(String email); // 이메일 중복체크
    boolean findNickNameUser(String nickName); // 이메일 중복체크
    Long loginUser(String email, String password); // 로그인

    UserVO findByuID(Long uID);

    List<String> findBestUsers();
}
