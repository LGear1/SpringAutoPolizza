package it.rjcsoft.springautopolizza.modelrest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

public class UserRest {

    private int id;
    @NotNull
    @Size(min = 2, max = 20, message = "Il nome deve avere lunghezza compresa tra 2 e 20")
    private String name;
    @NotNull
    @Size(min = 2, max = 20, message = "Il cognome deve avere lunghezza compresa tra 2 e 20")
    private String surname;
    @NotNull
    @Email(message = "Email non valida")
    private String email;
    @NotNull
    @Size(min = 8, max = 16, message = "la password deve essere almeno di 8 caratteri e massimo 16")
    private String password;

    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", message = "il Codice fiscale inserito non è valido")
    private String cf;

    private Date dateOfBirth;

    private int idRole;
    private String role;

    public UserRest(String name, String surname, String email, String password, String cf, Date dateOfBirth, String role){
        this.id = id;
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
    @JsonProperty("Identificativo")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("nome")
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
