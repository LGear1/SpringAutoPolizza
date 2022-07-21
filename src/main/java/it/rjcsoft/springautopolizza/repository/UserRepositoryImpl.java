package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";
    private String QueryDeleteUser="DELETE FROM test1_users WHERE id = ?";

    private String QuerySelectUser="Select * from test1_users tu JOIN test1_roles tr ON tr.id=ruolo_id JOIN test1_credenziali tc ON tc.iduser=tu.id WHERE tu.id = ?";

    private String QuerySelectUser2="Select * from test1_users tu WHERE tu.cf = ?";

    private String QueryUpdateUser="Update test1_users set nome=?, cf=? where id=?";

    private String QuerySelectAllUsers="Select * from test1_users tu INNER JOIN test1_roles tr ON tr.id=tu.ruolo_id INNER JOIN test1_credenziali tc ON tc.iduser = tu.id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role) {
        return 0;
    }

    @Override
    public int deleteUser(String cf) {
        Object[] args = new Object[] {cf};
        return jdbcTemplate.update(QueryDeleteUser, args);
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
