package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;

public interface UserRepository {
    int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role);
    boolean deleteUser(String cf);
    List<User> selectUser(int id);
    List<User> selectAllUsers();
    boolean updateUser(String name, String surname, String cf, Date dateOfBirth, int role);

}
