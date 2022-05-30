package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuID(Long uID);

    User findByName(String name);
}
