package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insecticide_distribution_method")
public class DistributionMethod {
    @Id
    private String distributionID;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_ended")
    private LocalDate dateEnded;

    @Column(name = "modified_by")
    private String modifiedBy;

    public DistributionMethod() {
    }

    public DistributionMethod(String distributionID, String name, int size,
                              LocalDate dateAdded, LocalDate dateEnded, String modifiedBy) {
        this.distributionID = distributionID;
        this.name = name;
        this.size = size;
        this.dateAdded = dateAdded;
        this.dateEnded = dateEnded;
        this.modifiedBy = modifiedBy;
    }

    public String getDistributionID() {
        return distributionID;
    }

    public void setDistributionID(String distribution_ID) {
        this.distributionID = distribution_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate date_added) {
        this.dateAdded = date_added;
    }

    public LocalDate getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(LocalDate date_ended) {
        this.dateEnded = date_ended;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
