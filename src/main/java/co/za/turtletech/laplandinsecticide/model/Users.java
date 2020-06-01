package co.za.turtletech.laplandinsecticide.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class Users {
    @Id
    private String userID;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_ended")
    private LocalDate dateEnded;

    public Users() {
    }

    public Users(String userID, String username, String password, boolean isAdmin, LocalDate dateAdded, LocalDate dateEnded) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.dateAdded = dateAdded;
        this.dateEnded = dateEnded;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String user_ID) {
        this.userID = user_ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean is_admin) {
        this.isAdmin = is_admin;
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
}
