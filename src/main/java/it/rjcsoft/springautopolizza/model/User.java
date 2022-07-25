package it.rjcsoft.springautopolizza.model;

import it.rjcsoft.springautopolizza.modelrest.UserRest;

import java.sql.Date;

public class User {
    private int id;
    private String name;

    private String surname;


    private String cf;

    private Date dateOfBirth;

    private int role;

    public User(UserRest u){
        this.id = u.getId();
        this.name = u.getName();
        this.surname = u.getSurname();
        this.cf = cf;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cf='" + cf + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", role=" + role +
                '}';
    }
}
