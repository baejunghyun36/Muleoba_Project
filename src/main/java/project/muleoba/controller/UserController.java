package project.muleoba.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.muleoba.domain.User;
import project.muleoba.service.userService.UserService;
import project.muleoba.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/muleoba/signup") // 회원가입 [ 로그인성공 : 1, 로그인실패 : 0 ]
    public int saveUser(@RequestBody UserVO vo) {
        User user = new User();
        user.setEmail(vo.getEmail());
        user.setNickName(vo.getNickName());
        user.setPhoneNumber(vo.getPhoneNumber());
        user.setName(vo.getName());
        user.setPassword(vo.getPassword());
        user.setAddress(vo.getAddress());
        return userService.saveUser(user);
    }

    @GetMapping("/muleoba/check/email") // 이메일 중복여부 [중복없음:true]
    public boolean findEmailUser(String email){
        System.out.println("[email!!] : " + email);
       return userService.findEmailUser(email);
    }

    @GetMapping("/muleoba/check/nickname") // 닉네임 중복여부 [중복없음:true]
    public boolean findNickNameUser(String nickName) {
        return userService.findNickNameUser(nickName);
    }

    @PostMapping("/muleoba/login") // 로그인
    public Long userLogin(@RequestBody Map map) {
        String email = String.valueOf(map.get("email"));
        String password = String.valueOf(map.get("password"));
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
