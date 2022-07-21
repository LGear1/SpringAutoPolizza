package it.rjcsoft.springautopolizza.model;

public class Ruolo {
    private int id;
    private String ruolo;

    public Ruolo(int id, String ruolo) {
        this.id = id;
        this.ruolo = ruolo;
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
