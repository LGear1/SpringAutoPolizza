package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Credenziali;

import java.util.List;

public interface LoginRepository {

    public List<Credenziali> login(String email, String pwd);
}
