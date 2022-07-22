package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Ruolo;

import java.util.List;

public interface RuoloRepository {
    public List<Ruolo> selectAllRuoli(String a);
}
