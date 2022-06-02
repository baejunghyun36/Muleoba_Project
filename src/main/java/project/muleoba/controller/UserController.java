package project.muleoba.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.muleoba.domain.User;
import project.muleoba.service.userService.UserService;
import project.muleoba.vo.UserVO;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/00") // 회원가입
    public void saveUser(User user) {
        userService.saveUser(user);
    }

    @PostMapping("/11") // 이메일 중복여부 [중복없음:true]
    public boolean findEmailUser(String email){
       return userService.findEmailUser(email);
    }

    @PostMapping("/22") // 닉네임 중복여부 [중복없음:true]
    public boolean findNickNameUser(String nickName) {
        return userService.findNickNameUser(nickName);
    }

    @GetMapping("/muleoba/user")
    public UserVO findUserUID(Long uID) {
        log.info("이쪽이다{}", uID);
        User user = userService.findByuID(uID);
        UserVO uservo = new UserVO();
        uservo.setUID(user.getUID());
        uservo.setEmail(user.getEmail());
        uservo.setNickName(user.getNickName());
        uservo.setPhoneNumber(user.getPhoneNumber());
        uservo.setName(user.getName());
        uservo.setPassword(user.getPassword());
        uservo.setAddress(user.getAddress());
        return uservo;
    }



}
