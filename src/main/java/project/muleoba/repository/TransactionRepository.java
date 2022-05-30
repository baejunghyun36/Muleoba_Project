package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Transaction;
import project.muleoba.domain.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Transaction findBytID(Long tID);


    @Query("select t from Transaction t where t.item.iID = :iid and t.requestIID = :requestIID")
    Transaction findByiidRequestIID(Long iid, Long requestIID);
}
