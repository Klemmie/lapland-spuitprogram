package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insecticide_transactions")
public class InsecticideTransaction {
    @Id
    private String insecticideTransactionsID;

    @ManyToOne
    @JoinColumn(name = "insecticide_details_ID")
    private InsecticideDetails insecticideDetails;

    @Column(name = "units_used")
    private int unitsUsed;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    public InsecticideTransaction() {
    }

    public InsecticideTransaction(String insecticideTransactionsID, InsecticideDetails insecticideDetails, int unitsUsed, LocalDate dateModified) {
        this.insecticideTransactionsID = insecticideTransactionsID;
        this.insecticideDetails = insecticideDetails;
        this.unitsUsed = unitsUsed;
        this.dateModified = dateModified;
    }

    public String getInsecticideTransactionsID() {
        return insecticideTransactionsID;
    }

    public void setInsecticideTransactionsID(String insecticide_transactions_ID) {
        this.insecticideTransactionsID = insecticide_transactions_ID;
    }

    public InsecticideDetails getInsecticideDetails() {
        return insecticideDetails;
    }

    public void setInsecticideDetails(InsecticideDetails insecticide_details) {
        this.insecticideDetails = insecticide_details;
    }

    public int getUnitsUsed() {
        return unitsUsed;
    }

    public void setUnitsUsed(int units_used) {
        this.unitsUsed = units_used;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate date_modified) {
        this.dateModified = date_modified;
    }
}
