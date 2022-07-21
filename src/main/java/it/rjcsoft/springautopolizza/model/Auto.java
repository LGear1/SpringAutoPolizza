package it.rjcsoft.springautopolizza.model;

import java.sql.Timestamp;
import java.sql.Date;

public class Auto {
    private int id;
    private String marca;
    private String modello;
    private String targa;
    private int proprietario;
    private double prezzo_auto;
    private Date datarevisione;
    private Timestamp inizio_polizza;
    private Timestamp fine_polizza;
    public Auto(){
        this.id=0;
        this.marca=null;
        this.modello=null;
        this.targa=null;
        this.proprietario=0;
        this.prezzo_auto=0;
        this.datarevisione=null;
        this.inizio_polizza=null;
        this.fine_polizza=null;
    }

    public Auto(String marca, String modello, String targa, int proprietario, double prezzo_auto, Date datarevisione, Timestamp inizio_polizza, Timestamp fine_polizza) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
        this.proprietario = proprietario;
        this.prezzo_auto = prezzo_auto;
        this.datarevisione = datarevisione;
        this.inizio_polizza = inizio_polizza;
        this.fine_polizza = fine_polizza;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getMarca() {
        return marca;
    }
    public void setModello(String modello) {
        this.modello = modello;
    }
    public String getModello() {
        return modello;
    }
    public void setTarga(String targa) {
        this.targa = targa;
    }
    public String getTarga() {
        return targa;
    }
    public void setProprietario(int proprietario) { this.proprietario = proprietario; }
    public int getProprietario() {
        return proprietario;
    }
    public void setPrezzo_auto(double prezzo_auto) { this.prezzo_auto = prezzo_auto; }
    public double getPrezzo_auto() {
        return prezzo_auto;
    }
    public void setDatarevisione(Date datarevisione) { this.datarevisione = datarevisione; }
    public Date getDatarevisione() { return datarevisione; }
    public void setInizio_polizza(Timestamp inizio_polizza) { this.inizio_polizza = inizio_polizza; }
    public Timestamp getInizio_polizza() { return inizio_polizza; }
    public void setFine_polizza_polizza(Timestamp fine_polizza) { this.fine_polizza = fine_polizza; }
    public Timestamp getFine_polizza() { return fine_polizza; }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modello='" + modello + '\'' +
                ", targa='" + targa + '\'' +
                ", proprietario=" + proprietario +
                ", prezzo_auto=" + prezzo_auto +
                ", datarevisione=" + datarevisione +
                ", inizio_polizza=" + inizio_polizza +
                ", fine_polizza=" + fine_polizza +
                '}';
    }
}
