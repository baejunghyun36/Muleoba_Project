package project.muleoba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muleoba.domain.Alarm;
import project.muleoba.domain.Transaction;

@Repository
public interface AlarmRepository extends JpaRepository<Transaction, Long> {

}
