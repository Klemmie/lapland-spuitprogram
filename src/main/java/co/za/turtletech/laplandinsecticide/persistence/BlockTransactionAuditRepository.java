package co.za.turtletech.laplandinsecticide.persistence;

import co.za.turtletech.laplandinsecticide.model.BlockTransactionAudit;
import co.za.turtletech.laplandinsecticide.model.enums.BlockTransactionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTransactionAuditRepository extends JpaRepository<BlockTransactionAudit, Long> {
    List<BlockTransactionAudit> findByBlockID(String blockID);

    List<BlockTransactionAudit> findAll();
}
