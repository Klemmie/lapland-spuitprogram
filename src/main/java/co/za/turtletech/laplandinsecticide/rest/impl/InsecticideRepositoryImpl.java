package co.za.turtletech.laplandinsecticide.rest.impl;

import co.za.turtletech.laplandinsecticide.model.*;
import co.za.turtletech.laplandinsecticide.model.enums.BlockTransactionEnum;
import co.za.turtletech.laplandinsecticide.persistence.BlockTransactionAuditRepository;
import co.za.turtletech.laplandinsecticide.persistence.BlockTransactionRepository;
import co.za.turtletech.laplandinsecticide.persistence.CultivarRepository;
import co.za.turtletech.laplandinsecticide.persistence.DistributionMethodRepository;
import co.za.turtletech.laplandinsecticide.persistence.InsecticideDetailsRepository;
import co.za.turtletech.laplandinsecticide.persistence.InsecticideTransactionRepository;
import co.za.turtletech.laplandinsecticide.persistence.UsersRepository;
import co.za.turtletech.laplandinsecticide.rest.InsecticideController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InsecticideRepositoryImpl {

    Logger logger = LoggerFactory.getLogger(InsecticideRepositoryImpl.class);

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
        UUID uuid = UUID.randomUUID();
        BlockTransactionAudit blockTransactionAudit = new BlockTransactionAudit(
                uuid.toString(), blockTransaction.getBlockID(),
                blockTransaction.getUser(), blockTransaction.getInsecticideTransaction(),
                blockTransaction.getCultivar(), blockTransaction.getOperator(),
                blockTransaction.getDistributionMethod(), blockTransaction.getDateOfApplication(),
                blockTransaction.getTimeOfCompletion(), blockTransaction.getInsecticideUnitsUsed(),
                blockTransaction.getPestProblem(), blockTransaction.getExpectedHarvestDate(),
                blockTransaction.getWeatherCondition(), blockTransaction.getModifiedBy());
        blockTransactionAuditRepository.save(blockTransactionAudit);
        logger.info("[" + blockTransaction.getModifiedBy() + " added record: " + uuid.toString() +
                " in BlockTransactionAudit on: " + LocalDate.now().toString() + "]");
    }

    public List<BlockTransactionAudit> blockTransactionHistory(BlockTransactionEnum blockID) {
        return blockTransactionAuditRepository.findByBlockID(blockID);
    }

    public void insertCurrentBlockTransaction(String userID, BlockTransaction blockTransaction) {
//        findBlockTransaction(userID, BlockTransactionEnum.toEnum(blockTransaction.getBlockID()))
        blockTransactionRepository.save(blockTransaction);
        logger.info("[" + userID + " added record: " + blockTransaction.getBlockID() +
                " in BlockTransaction on: " + LocalDate.now().toString() + "]");
    }

    public void updateCurrentBlockTransaction(BlockTransaction blockTransaction) {
        insertTransactionToAudit(blockTransaction);
        blockTransactionRepository.delete(blockTransaction);
        logger.info("[" + blockTransaction.getModifiedBy() + " removed record: " + blockTransaction.getBlockID() +
                " in BlockTransaction on: " + LocalDate.now().toString() + "]");
        blockTransactionRepository.save(blockTransaction);
        logger.info("[" + blockTransaction.getModifiedBy() + " added record: " + blockTransaction.getBlockID() +
                " in BlockTransaction on: " + LocalDate.now().toString() + "]");
    }

    public BlockTransaction findBlockTransaction(String userID, String blockName) {
        logger.info("[" + userID + " retrieved record: " + blockName + " in BlockTransaction on: "
                + LocalDate.now().toString() + "]");
        return blockTransactionRepository.findByBlockID(BlockTransactionEnum.valueOf(blockName).getLabel());
    }

    public List<BlockTransaction> allFarmTransactions(String userID) {
        logger.info("[" + userID + " retrieved all records in BlockTransaction on: " + LocalDate.now().toString() + "]");
        return blockTransactionRepository.findAll();
    }

    public void insertCultivar(Cultivar cultivar) {
        if (cultivar.getCultivarID() == null)
            cultivar.setCultivarID(UUID.randomUUID().toString());
        cultivarRepository.save(cultivar);
        logger.info("[" + cultivar.getModifiedBy() + " added record: " + cultivar.getCultivarID() +
                " in Cultivar on: " + LocalDate.now().toString() + "]");
    }

    public int updateCultivar(Cultivar proposedCultivar) {
        Cultivar currentCultivar = findCultivar(proposedCultivar.getCultivarName());
        if (currentCultivar != null) {
            currentCultivar.setDateEnded(LocalDate.now());

            cultivarRepository.save(currentCultivar);
            logger.info("[" + currentCultivar.getModifiedBy() + " updated record: " + currentCultivar.getCultivarID() +
                    " in Cultivar on: " + LocalDate.now().toString() + "]");
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
        if (insecticideDetails.getInsecticideDetailsID() == null)
            insecticideDetails.setInsecticideDetailsID(UUID.randomUUID().toString());
        insecticideDetailsRepository.save(insecticideDetails);
        logger.info("[" + insecticideDetails.getModifiedBy() + " added record: " +
                insecticideDetails.getInsecticideDetailsID() + " in InsecticideDetails on: "
                + LocalDate.now().toString() + "]");
    }

    public int updateInsecticideDetails(InsecticideDetails insecticideDetails) {
        InsecticideDetails currentInsecticideDetails = findInsecticideDetails(insecticideDetails.getStreetName());
        if (currentInsecticideDetails != null) {
            int deleted = deleteInsecticideDetail(insecticideDetails.getStreetName());
            if (deleted == 1) {
                insecticideDetailsRepository.save(insecticideDetails);
                logger.info("[" + insecticideDetails.getModifiedBy() + " updated record: " +
                        insecticideDetails.getInsecticideDetailsID() + " in InsecticideDetails on: "
                        + LocalDate.now().toString() + "]");
                return 1;
            } else return 0;
        } else return 0;
    }

    public int deleteInsecticideDetail(String streetName) {
        InsecticideDetails currentInsecticideDetails = findInsecticideDetails(streetName);
        if (currentInsecticideDetails != null) {
            currentInsecticideDetails.setDateEnded(LocalDate.now());
            insecticideDetailsRepository.save(currentInsecticideDetails);
            logger.info("[" + currentInsecticideDetails.getModifiedBy() + " ended record: " +
                    currentInsecticideDetails.getInsecticideDetailsID() + " in InsecticideDetails on: "
                    + LocalDate.now().toString() + "]");
            return 1;
        } else return 0;
    }

    public InsecticideDetails findInsecticideDetails(String streetName) {
        List<InsecticideDetails> insecticideDetailsList = insecticideDetailsRepository.findByStreetName(streetName);
        for (InsecticideDetails insecticideDetails : insecticideDetailsList) {
            if (insecticideDetails.getDateEnded().isAfter(LocalDate.now()))
                return insecticideDetails;
        }
        return null;
    }

    public void insertDistributionMethod(DistributionMethod distributionMethod) {
        if (distributionMethod.getDistributionID() == null)
            distributionMethod.setDistributionID(UUID.randomUUID().toString());
        distributionMethodRepository.save(distributionMethod);
        logger.info("[" + distributionMethod.getModifiedBy() + " added record: " +
                distributionMethod.getDistributionID() + " in InsecticideDistributionMethod on: "
                + LocalDate.now().toString() + "]");

    }

    public int updateDistributionMethod(DistributionMethod distributionMethod) {
        DistributionMethod currentDistributionMethod = findDistributionMethod(distributionMethod.getName());
        if (currentDistributionMethod != null) {
            int deleted = deleteDistributionMethod(distributionMethod.getName());
            if (deleted == 1) {
                distributionMethodRepository.save(distributionMethod);
                logger.info("[" + distributionMethod.getModifiedBy() + " updated record: " +
                        distributionMethod.getDistributionID() + " in InsecticideDistributionMethod on: "
                        + LocalDate.now().toString() + "]");

                return 1;
            } else return 0;
        } else return 0;
    }

    public int deleteDistributionMethod(String name) {
        DistributionMethod currentDistributionMethod = findDistributionMethod(name);
        if (currentDistributionMethod != null) {
            currentDistributionMethod.setDateEnded(LocalDate.now());
            distributionMethodRepository.save(currentDistributionMethod);
            logger.info("[" + currentDistributionMethod.getModifiedBy() + " removed record: " +
                    currentDistributionMethod.getDistributionID() + " in InsecticideDistributionMethod on: "
                    + LocalDate.now().toString() + "]");

            return 1;
        } else return 0;
    }

    public DistributionMethod findDistributionMethod(String name) {
        List<DistributionMethod> distributionMethodList = distributionMethodRepository.findByName(name);
        for (DistributionMethod distributionMethod : distributionMethodList) {
            if (distributionMethod.getDateEnded().isAfter(LocalDate.now()))
                return distributionMethod;
        }

        return null;
    }

    public void insertInsecticideTransaction(InsecticideTransaction insecticideTransaction) {
        if (insecticideTransaction.getInsecticideTransactionsID() == null)
            insecticideTransaction.setInsecticideTransactionsID(UUID.randomUUID().toString());
        insecticideTransactionRepository.save(insecticideTransaction);
        logger.info("[" + insecticideTransaction.getModifiedBy() + " added record: " +
                insecticideTransaction.getInsecticideTransactionsID() + " in InsecticideTransaction on: "
                + LocalDate.now().toString() + "]");
    }

    public InsecticideTransaction findInsecticideTransaction(String insecticideTransactionID) {
        return insecticideTransactionRepository.findByInsecticideTransactionsID(insecticideTransactionID);
    }

    public List<InsecticideTransaction> findAllInsecticideTransactions() {
        return insecticideTransactionRepository.findAll();
    }

    public void insertUser(Users users) {
        if (users.getUserID() == null)
            users.setUserID(UUID.randomUUID().toString());
        usersRepository.save(users);
        logger.info("[" + users.getModifiedBy() + " added record: " + users.getUserID() +
                " in Users on: " + LocalDate.now().toString() + "]");
    }

    public int updateUser(Users users) {
        Users user = findUser(users.getUsername());
        if (user != null) {
            int deleted = deleteUser(users.getUsername());
            if (deleted == 1) {
                insertUser(users);
                return 1;
            } else return 0;
        } else return 0;
    }

    public int deleteUser(String username) {
        Users user = findUser(username);
        if (user != null) {
            user.setDateEnded(LocalDate.now());
            usersRepository.save(user);
            logger.info("[" + user.getModifiedBy() + " removed record: " + user.getUserID() +
                    " in Users on: " + LocalDate.now().toString() + "]");
            return 1;
        } else return 0;
    }

    public Users findUser(String username) {
        List<Users> usersList = usersRepository.findByUsername(username);
        for (Users user : usersList) {
            if (user.getDateEnded().isAfter(LocalDate.now()))
                return user;
        }

        return null;
    }
}
