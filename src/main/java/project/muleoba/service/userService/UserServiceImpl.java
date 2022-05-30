package project.muleoba.service.userService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.muleoba.domain.User;
import project.muleoba.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User findEmailUser(String email) {
        return userRepository.findEmailUser(email);
    }
}
