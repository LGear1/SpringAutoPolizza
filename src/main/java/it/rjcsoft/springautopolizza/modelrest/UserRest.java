package it.rjcsoft.springautopolizza.modelrest;
import java.sql.Date;

public class UserRest {
    private String name;

    private String surname;

    private String email;

    private String password;

    private String cf;

    private Date dateOfBirth;

    private int idRole;
    private String role;

    public UserRest(String name, String surname, String email, String password, String cf, Date dateOfBirth, String role){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.cf = cf;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public UserRest() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public java.sql.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
