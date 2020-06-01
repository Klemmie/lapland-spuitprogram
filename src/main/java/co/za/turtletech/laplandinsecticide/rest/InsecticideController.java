package co.za.turtletech.laplandinsecticide.rest;

import co.za.turtletech.laplandinsecticide.model.BlockTransaction;
import co.za.turtletech.laplandinsecticide.model.enums.BlockTransactionEnum;
import co.za.turtletech.laplandinsecticide.persistence.BlockTransactionRepository;
import co.za.turtletech.laplandinsecticide.rest.impl.InsecticideRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InsecticideController {

    final InsecticideRepositoryImpl insecticideRepository;

    public InsecticideController(InsecticideRepositoryImpl insecticideRepository) {
        this.insecticideRepository = insecticideRepository;
    }

    @GetMapping("/allFarmTransactions")
    public List<BlockTransaction> allFarmTransactions() {
        return blockTransactionRepository.findAll();
    }

    @GetMapping("/blockTransaction/{name}")
    public BlockTransaction blockTransaction(@PathVariable String name) {
        return blockTransactionRepository.findByBlockID(BlockTransactionEnum.valueOf(name));
    }
}
