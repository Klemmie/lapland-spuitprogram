package co.za.turtletech.laplandinsecticide.rest.impl;

import co.za.turtletech.laplandinsecticide.model.BlockTransaction;
import co.za.turtletech.laplandinsecticide.model.BlockTransactionAudit;
import co.za.turtletech.laplandinsecticide.model.Cultivar;
import co.za.turtletech.laplandinsecticide.model.InsecticideDetails;
import co.za.turtletech.laplandinsecticide.model.enums.BlockTransactionEnum;
import co.za.turtletech.laplandinsecticide.persistence.BlockTransactionAuditRepository;
import co.za.turtletech.laplandinsecticide.persistence.BlockTransactionRepository;
import co.za.turtletech.laplandinsecticide.persistence.CultivarRepository;
import co.za.turtletech.laplandinsecticide.persistence.DistributionMethodRepository;
import co.za.turtletech.laplandinsecticide.persistence.InsecticideDetailsRepository;
import co.za.turtletech.laplandinsecticide.persistence.InsecticideTransactionRepository;
import co.za.turtletech.laplandinsecticide.persistence.UsersRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsecticideRepositoryImpl {
    final BlockTransactionAuditRepository blockTransactionAuditRepository;
    final BlockTransactionRepository blockTransactionRepository;
    final CultivarRepository cultivarRepository;
    final DistributionMethodRepository distributionMethodRepository;
    final InsecticideDetailsRepository insecticideDetailsRepository;
    final InsecticideTransactionRepository insecticideTransactionRepository;
    final UsersRepository usersRepository;

    public InsecticideRepositoryImpl(BlockTransactionAuditRepository blockTransactionAuditRepository,
                                     BlockTransactionRepository blockTransactionRepository,
                                     CultivarRepository cultivarRepository,
                                     DistributionMethodRepository distributionMethodRepository,
                                     InsecticideDetailsRepository insecticideDetailsRepository,
                                     InsecticideTransactionRepository insecticideTransactionRepository,
                                     UsersRepository usersRepository) {
        this.blockTransactionAuditRepository = blockTransactionAuditRepository;
        this.blockTransactionRepository = blockTransactionRepository;
        this.cultivarRepository = cultivarRepository;
        this.distributionMethodRepository = distributionMethodRepository;
        this.insecticideDetailsRepository = insecticideDetailsRepository;
        this.insecticideTransactionRepository = insecticideTransactionRepository;
        this.usersRepository = usersRepository;
    }

    public void insertTransactionToAudit(BlockTransaction blockTransaction) {
        BlockTransactionAudit blockTransactionAudit = new BlockTransactionAudit(
                blockTransaction.getBlockID(), blockTransaction.getUser(),
                blockTransaction.getInsecticideTransaction(), blockTransaction.getCultivar(),
                blockTransaction.getOperator(), blockTransaction.getDistributionMethod(),
                blockTransaction.getDateOfApplication(), blockTransaction.getTimeOfCompletion(),
                blockTransaction.getInsecticideUnitsUsed(), blockTransaction.getPestProblem(),
                blockTransaction.getExpectedHarvestDate(), blockTransaction.getWeatherCondition());
        blockTransactionAuditRepository.save(blockTransactionAudit);
    }

    public List<BlockTransactionAudit> blockTransactionHistory(BlockTransactionEnum blockID) {
        return blockTransactionAuditRepository.findByBlockID(blockID);
    }

    public void insertCurrentBlockTransaction(BlockTransaction blockTransaction) {
        blockTransactionRepository.save(blockTransaction);
    }

    public void updateCurrentBlockTransaction(BlockTransaction blockTransaction) {
        insertTransactionToAudit(blockTransaction);
        blockTransactionRepository.delete(blockTransaction);
        blockTransactionRepository.save(blockTransaction);
    }

    public BlockTransaction currentBlockTransaction(String blockName) {
        return blockTransactionRepository.findByBlockID(BlockTransactionEnum.valueOf(blockName));
    }

    public List<BlockTransaction> allFarmTransactions() {
        return blockTransactionRepository.findAll();
    }

    public void insertCultivar(Cultivar cultivar) {
        cultivarRepository.save(cultivar);
    }

    public int updateCultivar(Cultivar proposedCultivar) {
        Cultivar currentCultivar = findCultivar(proposedCultivar.getCultivarName());
        if (currentCultivar != null) {
            currentCultivar.setDateEnded(LocalDate.now());

            cultivarRepository.save(currentCultivar);
            insertCultivar(proposedCultivar);
            return 1;
        } else
            return 0;
    }

    public List<Cultivar> allActiveCultivar() {
        List<Cultivar> cultivarList = cultivarRepository.findAll();
        List<Cultivar> activeCultivar = new ArrayList<>();
        for (Cultivar cultivar : cultivarList) {
            if (cultivar.getDateEnded().isAfter(LocalDate.now()))
                activeCultivar.add(cultivar);
        }
        return activeCultivar;
    }

    public Cultivar findCultivar(String cultivarName) {
        Cultivar cultivar = cultivarRepository.findByCultivarName(cultivarName);
        if (cultivar.getDateEnded().isAfter(LocalDate.now()))
            return cultivar;
        else
            return null;
    }

    public void insertInsecticideDetails(InsecticideDetails insecticideDetails) {
        insecticideDetailsRepository.save(insecticideDetails);
    }

    public int updateInsecticideDetails(InsecticideDetails insecticideDetails) {

    }

    public int deleteInsecticideDetail(InsecticideDetails insecticideDetails) {
        InsecticideDetails currentInsecticideDetails = insecticideDetailsRepository.findByStreetName()
    }

    public InsecticideDetails findInsecticideDetails(String streetName) {
        List<InsecticideDetails> insecticideDetailsList = insecticideDetailsRepository.findByStreetName(streetName);
        for (InsecticideDetails insecticideDetails : insecticideDetailsList) {

        }
    }
}
