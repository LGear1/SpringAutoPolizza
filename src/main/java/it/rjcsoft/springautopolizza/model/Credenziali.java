package it.rjcsoft.springautopolizza.model;

import it.rjcsoft.springautopolizza.modelrest.LoginRest;
import it.rjcsoft.springautopolizza.modelrest.UserRest;

import java.util.Base64;

public class Credenziali {
    private int iduser;
    private String email;
    private String pwd;

    public Credenziali(UserRest u) {
        this.iduser = u.getIdRole();
        this.email = u.getEmail();
        this.pwd = u.getPassword();
    }

    public Credenziali(LoginRest logR) {
        this.email = logR.getEmail();
        this.pwd = Base64.getEncoder().encodeToString(logR.getPwd().getBytes());
    }

    public Credenziali(){
        this.email = null;
        this.pwd = null;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "iduser=" + iduser +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
