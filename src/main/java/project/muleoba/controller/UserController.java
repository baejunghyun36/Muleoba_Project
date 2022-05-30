package project.muleoba.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import project.muleoba.domain.User;
import project.muleoba.service.userService.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/11")
    public String findEmailUser(String email){
       User user = userService.findEmailUser(email);
       if(user == null){

        return "사용자 없을 때 ";
       }
       return "사용자는 user ";
    }

}
