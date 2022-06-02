package project.muleoba.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.muleoba.domain.User;
import project.muleoba.service.userService.UserService;
import project.muleoba.vo.UserVO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/muleoba/signup") // 회원가입 [ 로그인성공 : 1, 로그인실패 : 0 ]
    public int saveUser(UserVO vo) {
        User user = new User();
        user.setEmail(vo.getEmail());
        user.setNickName(vo.getNickName());
        user.setPhoneNumber(vo.getPhoneNumber());
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setAddress(vo.getAddress());
        return userService.saveUser(user);
    }

    @PostMapping("/check/email") // 이메일 중복여부 [중복없음:true]
    public boolean findEmailUser(String email){
       return userService.findEmailUser(email);
    }

    @PostMapping("/check/nickname") // 닉네임 중복여부 [중복없음:true]
    public boolean findNickNameUser(String nickName) {
        return userService.findNickNameUser(nickName);
    }

    @PostMapping("/login") // 로그인
    public Long userLogin(String email, String password) {
        return userService.loginUser(email, password);
    }

    @GetMapping("/muleoba/user")
    public UserVO findUserUID(long uID) {
        User user = userService.findByuID(uID);
        return new UserVO(user);
    }

    @GetMapping("/muleoba/bestuser")
    public List<String> bestUser(){
        return userService.findBestUsers();
    }

}
