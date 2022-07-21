package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;
import java.util.List;

import it.rjcsoft.springautopolizza.model.User;

public interface UserRepository {
    int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role);
    int deleteUser(String cf);
    List<User> selectUser(String cf);
    int selectUser2(String cf);
    List<User> selectAllUsers();
    boolean updateUser(String name, String surname, String cf, Date dateOfBirth, int role);

}
