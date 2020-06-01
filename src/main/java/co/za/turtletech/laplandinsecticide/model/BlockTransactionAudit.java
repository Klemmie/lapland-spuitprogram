package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "insecticide_block_transactions_audit")
public class BlockTransactionAudit implements Serializable {
    @Id
    private String ID;

    @Column
    private String blockID;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "insecticide_ID")
    private InsecticideTransaction insecticideTransaction;

    @ManyToOne
    @JoinColumn(name = "cultivar_ID")
    private Cultivar cultivar;

    @ManyToOne
    @JoinColumn(name = "operator_ID")
    private Users operator;

    @ManyToOne
    @JoinColumn(name = "distribution_method_ID")
    private DistributionMethod distributionMethod;

    @Column(name = "date_of_application")
    private LocalDate dateOfApplication;

    @Column(name = "time_of_completion")
    private LocalDateTime timeOfCompletion;

    @Column(name = "insecticide_units_used")
    private int insecticideUnitsUsed;

    @Column(name = "pest_problem")
    private String pestProblem;

    @Column(name = "expected_harvest_date")
    private LocalDate expectedHarvestDate;

    @Column(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "modified_by")
    private String modifiedBy;

    public BlockTransactionAudit() {
    }

    public BlockTransactionAudit(String ID, String blockID, Users user, InsecticideTransaction insecticideTransaction,
                                 Cultivar cultivar, Users operator, DistributionMethod distributionMethod,
                                 LocalDate dateOfApplication, LocalDateTime timeOfCompletion, int insecticideUnitsUsed,
                                 String pestProblem, LocalDate expectedHarvestDate, String weatherCondition,
                                 String modifiedBy) {
        this.ID = ID;
        this.blockID = blockID;
        this.user = user;
        this.insecticideTransaction = insecticideTransaction;
        this.cultivar = cultivar;
        this.operator = operator;
        this.distributionMethod = distributionMethod;
        this.dateOfApplication = dateOfApplication;
        this.timeOfCompletion = timeOfCompletion;
        this.insecticideUnitsUsed = insecticideUnitsUsed;
        this.pestProblem = pestProblem;
        this.expectedHarvestDate = expectedHarvestDate;
        this.weatherCondition = weatherCondition;
        this.modifiedBy = modifiedBy;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String block_ID) {
        this.blockID = block_ID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public InsecticideTransaction getInsecticideTransaction() {
        return insecticideTransaction;
    }

    public void setInsecticideTransaction(InsecticideTransaction insecticideTransaction) {
        this.insecticideTransaction = insecticideTransaction;
    }

    public Cultivar getCultivar() {
        return cultivar;
    }

    public void setCultivar(Cultivar cultivar) {
        this.cultivar = cultivar;
    }

    public Users getOperator() {
        return operator;
    }

    public void setOperator(Users operator) {
        this.operator = operator;
    }

    public DistributionMethod getDistributionMethod() {
        return distributionMethod;
    }

    public void setDistributionMethod(DistributionMethod distributionMethod) {
        this.distributionMethod = distributionMethod;
    }

    public LocalDate getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(LocalDate date_of_application) {
        this.dateOfApplication = date_of_application;
    }

    public LocalDateTime getTimeOfCompletion() {
        return timeOfCompletion;
    }

    public void setTimeOfCompletion(LocalDateTime time_of_completion) {
        this.timeOfCompletion = time_of_completion;
    }

    public int getInsecticideUnitsUsed() {
        return insecticideUnitsUsed;
    }

    public void setInsecticideUnitsUsed(int insecticide_units_used) {
        this.insecticideUnitsUsed = insecticide_units_used;
    }

    public String getPestProblem() {
        return pestProblem;
    }

    public void setPestProblem(String pest_problem) {
        this.pestProblem = pest_problem;
    }

    public LocalDate getExpectedHarvestDate() {
        return expectedHarvestDate;
    }

    public void setExpectedHarvestDate(LocalDate expected_harvest_date) {
        this.expectedHarvestDate = expected_harvest_date;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weather_condition) {
        this.weatherCondition = weather_condition;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
