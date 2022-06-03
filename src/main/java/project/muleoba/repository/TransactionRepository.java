package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Transaction findBytID(Long tID);

    @Query("select t from Transaction t where t.item.iID = :iid and t.requestIID = :requestIID")
    Transaction findByiidRequestIID(@Param("iid") Long iid,@Param("requestIID") Long requestIID);

    @Query("select distinct t from Transaction  t, Item i where i.user.uID=:uid and t.requestIID = i.iID and t.status = 0 order by t.requestTime desc")
    List<Transaction> findAllDateDesc(@Param("uid") Long uid);
}
