package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    private String QueryInsertCredenziali="Insert into test1_credenziali (email,pwd,iduser) VALUES (?,?,?)";
    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role) {
        try {
            jdbcTemplate.update(QueryInsertCredenziali,
                    new Object[] {email, password });
            jdbcTemplate.update(QueryInsertUser,
                    new Object[] {name, surname, cf, dateOfBirth, role  });
            return 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
