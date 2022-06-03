package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import project.muleoba.domain.Item;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query("select i from Item i where i.photo = :name")
    Item findByPhoto(@Param("name") String name);

    Item findByiID(Long IID);

    @Query("select i from Item i order by i.uploadTime desc")
    List<Item> findAllOrder();

    @Query("select i.iID from Item i where i.user.uID = :uID")
    List<Long> findByiIDList(Long uID);
}
