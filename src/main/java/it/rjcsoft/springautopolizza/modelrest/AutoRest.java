package it.rjcsoft.springautopolizza.modelrest;

import java.sql.Date;
import java.sql.Timestamp;

public class AutoRest {

    private int id;
    private String marca;
    private String modello;
    private String targa;
    private int proprietario;
    private double prezzo_auto;
    private String datarevisione;
    private String inizio_polizza;
    private String fine_polizza;

    public AutoRest() {
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

    public AutoRest(int id, String marca, String modello, String targa, int proprietario, double prezzo_auto, String datarevisione, String inizio_polizza, String fine_polizza) {
        this.id = id;
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
        this.proprietario = proprietario;
        this.prezzo_auto = prezzo_auto;
        this.datarevisione = datarevisione;
        this.inizio_polizza = inizio_polizza;
        this.fine_polizza = fine_polizza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public int getProprietario() {
        return proprietario;
    }

    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }

    public double getPrezzo_auto() {
        return prezzo_auto;
    }

    public void setPrezzo_auto(double prezzo_auto) {
        this.prezzo_auto = prezzo_auto;
    }

    public String getDatarevisione() {
        return datarevisione;
    }

    public void setDatarevisione(String datarevisione) {
        this.datarevisione = datarevisione;
    }

    public String getInizio_polizza() {
        return inizio_polizza;
    }

    public void setInizio_polizza(String inizio_polizza) {
        this.inizio_polizza = inizio_polizza;
    }

    public String getFine_polizza() {
        return fine_polizza;
    }

    public void setFine_polizza(String fine_polizza) {
        this.fine_polizza = fine_polizza;
    }
}
