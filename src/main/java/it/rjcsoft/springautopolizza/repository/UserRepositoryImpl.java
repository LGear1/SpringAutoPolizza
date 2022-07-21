package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.User;

import java.sql.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    @Override
    public int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role) {
        return 0;
    }

    @Override
    public boolean deleteUser(String cf) {
        return false;
    }

    @Override
    public List<User> selectUser(int id) {
        return null;
    }

    @Override
    public List<User> selectAllUsers() {
        return null;
    }

    @Override
    public boolean updateUser(String name, String surname, String cf, Date dateOfBirth, int role) {
        return false;
    }
}
