package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cultivar")
public class Cultivar {
    @Id
    private String cultivarID;

    @Column(name = "cultivar_name")
    private String cultivarName;

    @Column(name = "known_pests")
    private String knownPests;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_ended")
    private LocalDate dateEnded;

    @Column(name = "modified_by")
    private String modifiedBy;

    public Cultivar() {
    }

    public Cultivar(String cultivarID, String cultivarName, String knownPests,
                    LocalDate dateAdded, LocalDate dateEnded, String modifiedBy) {
        this.cultivarID = cultivarID;
        this.cultivarName = cultivarName;
        this.knownPests = knownPests;
        this.dateAdded = dateAdded;
        this.dateEnded = dateEnded;
        this.modifiedBy = modifiedBy;
    }

    public String getCultivarID() {
        return cultivarID;
    }

    public void setCultivarID(String cultivar_ID) {
        this.cultivarID = cultivar_ID;
    }

    public String getCultivarName() {
        return cultivarName;
    }

    public void setCultivarName(String cultivar_name) {
        this.cultivarName = cultivar_name;
    }

    public String getKnownPests() {
        return knownPests;
    }

    public void setKnownPests(String known_pests) {
        this.knownPests = known_pests;
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
