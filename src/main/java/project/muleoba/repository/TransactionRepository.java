package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {




    Transaction findBytID(Long tID);

    @Query("select t from Transaction t where t.item.iID = :iid and t.requestIID = :requestIID")
    Transaction findByiidRequestIID(@Param("iid") Long iid,@Param("requestIID") Long requestIID);

    @Query("select distinct t from Transaction  t, Item i where i.user.uID=:uid and t.requestIID = i.iID and t.status = 'Normal' order by t.requestTime desc")
    List<Transaction> findAllDateDesc(@Param("uid") Long uid);


    @Query("select t.tID from Transaction t where t.requestIID = :requestiID and t.item.iID=:iID ")
    Long findDeleteTID(@Param("requestiID") Long requestiID, @Param("iID") Long iID);

    @Modifying
    @Query("delete from Transaction t where t.item.iID = :iid")
    void deleteIid(@Param("iid") Long iid);
}
