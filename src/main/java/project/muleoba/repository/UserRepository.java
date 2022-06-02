package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuID(Long uID);

    @Query("select u from User u where u.name = :name")
    User findByName(@Param("name") String name);

    @Query("select u from User u where u.email = :email")
    User findEmailUser(@Param("email") String email);

    @Query("select u from User u where u.nickName = :nickName")
    User findNickNameUser(@Param("nickName") String nickName);

    @Query("select u.nickName from User u order by u.completeCnt desc  ")
    List<String> findBestUsers();
}
