package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private String QueryInsertCredenziali="Insert into test1_credenziali (email,pwd,iduser) VALUES (?,?,?)";
    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";

    private String QueryUpdateUser="Update test1_users set nome=?, cf=? where id=?";
    private String QueryDeleteUser="DELETE FROM test1_users WHERE id = ?";

    private String QuerySelectUser="Select * from test1_users tu JOIN test1_roles tr ON tr.id=ruolo_id JOIN test1_credenziali tc ON tc.iduser=tu.id WHERE tu.id = ?";

    private String QuerySelectUser2="Select * from test1_users tu WHERE tu.cf = ?";
    private String QuerySelectAllUsers="Select * from test1_users tu INNER JOIN test1_roles tr ON tr.id=tu.ruolo_id INNER JOIN test1_credenziali tc ON tc.iduser = tu.id";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertUser(String name, String surname, String email, String password, String cf, Date dateOfBirth, int role) {
        try {
            jdbcTemplate.update(QueryInsertUser,
                    new Object[] {name, surname, cf, dateOfBirth, role  });
            int id = selectUser2(cf);
            jdbcTemplate.update(QueryInsertCredenziali,
                    new Object[] {email, password, id });
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
        Ruolo ruolo = new Ruolo();
        user.setName(rs.getString("nome"));
        user.setSurname(rs.getString("cognome"));
        user.setRole(rs.getInt("ruolo_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCf(rs.getString("cf"));
        user.setDateOfBirth(rs.getDate("datanascita"));
        ruolo.setRuolo(rs.getString("ruolo"));
        ruolo.setId(rs.getInt("ruolo_id"));
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
        return jdbcTemplate.query(QuerySelectUser, rowMapper ,args);
    }

    @Override
    public int selectUser2(String cf){
        Object[] args = new Object[] {cf};
        List<User> u = jdbcTemplate.query(QuerySelectUser2, rowMapper2 ,args);
        return u.get(0).getId();

    }

    RowMapper<User> rowMapper3 = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("nome"));
        user.setSurname(rs.getString("cognome"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCf(rs.getString("cf"));
        user.setDateOfBirth(rs.getDate("datanascita"));
        user.setRole(rs.getInt("ruolo_id"));
        return user;
    };

    @Override
    public List<User> selectAllUsers(int ruoloid, int idcred) {

        return jdbcTemplate.query(QuerySelectAllUsers, rowMapper3,ruoloid,idcred);
    }

    @Override
    public int updateUser(String name, String surname, String cf, Date dateOfBirth, int role) {

        return jdbcTemplate.update(QueryUpdateUser,
                new Object[] { name, surname, cf, dateOfBirth, role});
    }

}
