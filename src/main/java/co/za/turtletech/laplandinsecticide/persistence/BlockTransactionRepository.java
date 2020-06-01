package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.BlockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTransactionRepository extends JpaRepository<BlockTransaction, Long> {
    BlockTransaction findByBlockID(String blockID);

    List<BlockTransaction> findAll();
}
