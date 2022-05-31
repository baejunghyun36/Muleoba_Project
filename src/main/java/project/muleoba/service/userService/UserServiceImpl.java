package project.muleoba.service.userService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.muleoba.domain.User;
import project.muleoba.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public String saveUser(User user) {
        if (findEmailUser(user.getEmail()) && findNickNameUser(user.getNickName())) {
            em.persist(user);
        }
        return user.getEmail();
    }

    @Override
    @Transactional
    public boolean findEmailUser(String email) {
        return userRepository.findEmailUser(email) == null;
    }

    @Override
    @Transactional
    public boolean findNickNameUser(String nickName) {
        return userRepository.findNickNameUser(nickName) == null;
    }

    @Override
    public User findByuID(Long uID) {
        return userRepository.findByuID(uID);
    }


}
