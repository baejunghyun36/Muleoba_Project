package project.muleoba.service.userService;


import org.springframework.stereotype.Service;
import project.muleoba.domain.User;

@Service
public interface UserService {

    User findEmailUser(String email);
}
