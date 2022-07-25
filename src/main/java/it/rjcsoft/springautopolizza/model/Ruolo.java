package it.rjcsoft.springautopolizza.model;

import it.rjcsoft.springautopolizza.modelrest.UserRest;

public class Ruolo {

    private static final String ADMIN = "Admin";
    private static final String GUEST = "Guest";
    private int id;
    private String ruolo;

    public Ruolo(UserRest request) {
        switch(request.getIdRole()){
            case 1:{    //Caso Admin
                this.ruolo = ADMIN;
                this.id = 1;
            }break;
            case 2:{    //Caso Guest
                this.ruolo = GUEST;
                this.id = 2;
            }
        }
    }

    public Ruolo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "id=" + id +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}
