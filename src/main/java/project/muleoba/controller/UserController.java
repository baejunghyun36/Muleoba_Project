package project.muleoba.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.muleoba.domain.User;
import project.muleoba.service.userService.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/00") // 회원가입 [ 로그인성공 : 1, 로그인실패 : 0 ]
    public int saveUser(User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/11") // 이메일 중복여부 [중복없음:true]
    public boolean findEmailUser(String email){
       return userService.findEmailUser(email);
    }

    @PostMapping("/22") // 닉네임 중복여부 [중복없음:true]
    public boolean findNickNameUser(String nickName) {
        return userService.findNickNameUser(nickName);
    }

    @PostMapping("/33") // 로그인
    public int userLogin(String email, String password) {
        return userService.loginUser(email, password);
    }



}
