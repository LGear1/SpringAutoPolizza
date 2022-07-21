package it.rjcsoft.springautopolizza.model;

public class Credenziali {
    private int iduser;
    private String email;
    private String pwd;

    public Credenziali(int iduser, String email, String pwd) {
        this.iduser = iduser;
        this.email = email;
        this.pwd = pwd;
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
