package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private String QueryInsertCredenziali="Insert into test1_credenziali (email,pwd,iduser) VALUES (?,?,?)";
    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";

    private String QueryDeleteUser="DELETE FROM test1_users WHERE id = ?";

    private String QuerySelectUser="Select * from test1_users tu JOIN test1_roles tr ON tr.id=ruolo_id JOIN test1_credenziali tc ON tc.iduser=tu.id WHERE tu.id = ?";

    private String QuerySelectUser2="Select * from test1_users tu WHERE tu.cf = ?";
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
    public int deleteUser(String cf) {
        Object[] args = new Object[] {cf};
        return jdbcTemplate.update(QueryDeleteUser, args);
    }

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setName(rs.getString("Nome"));
        user.setSurname(rs.getString("Surname"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setCf(rs.getString("cf"));
        user.setDateOfBirth(rs.getDate("dateOfBirth"));
        return user;
    };

    RowMapper<User> rowMapper2 = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        return user;
    };
    @Override
    public List<User> selectUser(String cf){
        Object[] args = new Object[] {cf};
        return jdbcTemplate.query(QuerySelectUser2, rowMapper ,args);
    }

    @Override
    public int selectUser2(String cf){
        Object[] args = new Object[] {cf};
        List<User> u = jdbcTemplate.query(QuerySelectUser2, rowMapper2 ,args);
        return u.get(1).getId();

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
