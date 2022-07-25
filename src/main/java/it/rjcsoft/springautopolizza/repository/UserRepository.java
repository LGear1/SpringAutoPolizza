package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;
import java.util.List;

import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import it.rjcsoft.springautopolizza.modelrest.UserRest;

public interface UserRepository {
    int insertUser(User u, Ruolo r, Credenziali c);

    int deleteUser(int id);

    List<UserRest> selectUser(String cf);

    int selectUserID(String cf);

    List<UserRest> selectAllUsers();

    int updateUser(User u);
}
