package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insecticide_details")
public class InsecticideDetails {
    @Id
    private String insecticideDetailsID; //Primary key in database

    @Column(name = "street_name")
    private String streetName; //Normal every day name

    @Column(name = "usages")
    private String usages; //Which pests does this insecticide treat

    @Column(name = "active_ingredients")
    private String activeIngredients; //Active ingredients in insecticide

    @Column(name = "concentration")
    private int concentration; //Dosage needed alongside conversion_method to mix properly

    @Column(name = "conversion_method")
    private String conversionMethod; //Conversion to litre i.e. ml/L

    @Column(name = "abstinence_period")
    private int abstinencePeriod; //How long (days) does the crop need to wait before being harvested

    @Column(name = "quantity_per_unit")
    private int quantityPerUnit; //Dosages per packaged portion i.e. how many times can one bought package be used before it's empty

    @Column(name = "price_per_unit")
    private String pricePerUnit; //String variable to allow for decimal points as Postgres does not have double variable type

    @Column(name = "acquired_quantity")
    private int acquiredQuantity; //Cumulative acquired quantity for x period

    @Column(name = "availability_threshold")
    private int availabilityThreshold; //Minimum available to be useful, can be calculated or submitted

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_ended")
    private LocalDate dateEnded;

    public InsecticideDetails() {
    }

    public InsecticideDetails(String insecticideDetailsID, String streetName, String usages,
                              String activeIngredients, int concentration, String conversionMethod,
                              int abstinencePeriod, int quantityPerUnit, String pricePerUnit,
                              int acquiredQuantity, int availabilityThreshold, LocalDate dateAdded,
                              LocalDate dateEnded) {
        this.insecticideDetailsID = insecticideDetailsID;
        this.streetName = streetName;
        this.usages = usages;
        this.activeIngredients = activeIngredients;
        this.concentration = concentration;
        this.conversionMethod = conversionMethod;
        this.abstinencePeriod = abstinencePeriod;
        this.quantityPerUnit = quantityPerUnit;
        this.pricePerUnit = pricePerUnit;
        this.acquiredQuantity = acquiredQuantity;
        this.availabilityThreshold = availabilityThreshold;
        this.dateAdded = dateAdded;
        this.dateEnded = dateEnded;
    }

    public String getInsecticideDetailsID() {
        return insecticideDetailsID;
    }

    public void setInsecticideDetailsID(String insecticide_details_ID) {
        this.insecticideDetailsID = insecticide_details_ID;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String street_name) {
        this.streetName = street_name;
    }

    public String getUsages() {
        return usages;
    }

    public void setUsages(String usages) {
        this.usages = usages;
    }

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String active_ingredients) {
        this.activeIngredients = active_ingredients;
    }

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public String getConversionMethod() {
        return conversionMethod;
    }

    public void setConversionMethod(String conversion_method) {
        this.conversionMethod = conversion_method;
    }

    public int getAbstinencePeriod() {
        return abstinencePeriod;
    }

    public void setAbstinencePeriod(int abstinence_period) {
        this.abstinencePeriod = abstinence_period;
    }

    public int getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(int quantity_pre_unit) {
        this.quantityPerUnit = quantity_pre_unit;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String price_per_unit) {
        this.pricePerUnit = price_per_unit;
    }

    public int getAcquiredQuantity() {
        return acquiredQuantity;
    }

    public void setAcquiredQuantity(int acquired_quantity) {
        this.acquiredQuantity = acquired_quantity;
    }

    public int getAvailabilityThreshold() {
        return availabilityThreshold;
    }

    public void setAvailabilityThreshold(int availability_threshold) {
        this.availabilityThreshold = availability_threshold;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(LocalDate dateEnded) {
        this.dateEnded = dateEnded;
    }
}
