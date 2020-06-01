package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.InsecticideTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsecticideTransactionRepository extends JpaRepository<InsecticideTransaction, Long> {
    List<InsecticideTransaction> findByInsecticideTransactionsID(String insecticideTransactionID);

    List<InsecticideTransaction> findAll();
}
