package project.muleoba.service.userService;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.muleoba.domain.User;
import project.muleoba.repository.UserRepository;
import project.muleoba.token.TokenService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    TokenService tokenService;
    @PersistenceContext
    private EntityManager em;

    private String token;

    @Override
    @Transactional
    public int saveUser(User user) {
        if (findEmailUser(user.getEmail()) && findNickNameUser(user.getNickName())) {
            em.persist(user);
            return 1; // 회원가입 성공
        }
        return 0; // 회원가입 실패
    }

    @Override
    @Transactional // 이메일 중복 확인
    public boolean findEmailUser(String email) {
        return userRepository.findEmailUser(email) == null;
    }

    @Override
    @Transactional // 닉네임 중복 확인
    public boolean findNickNameUser(String nickName) {
        return userRepository.findNickNameUser(nickName) == null;
    }

    @Override
    @Transactional
    public int loginUser(String email, String password) {
        User user = userRepository.userLogin(email, password);
        if ( user != null ) {
            token = TokenService.createToken(user.getUID(), (120*1000*60));
            System.out.println(token);
            return 1; // 로그인 성공
        }
        return 0; // 로그인 실패
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public User findByuID(Long uID) {
        return userRepository.findByuID(uID);
    }


}
