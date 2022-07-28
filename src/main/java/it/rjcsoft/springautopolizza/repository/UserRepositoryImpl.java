package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import it.rjcsoft.springautopolizza.modelrest.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private String QueryInsertCredenziali="Insert into test1_credenziali (email,pwd,iduser) VALUES (?,?,(select last_value from test1_users_id_seq))";
    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";
    private String QueryUpdateUser="Update test1_users set nome=?, cognome=?, cf=?, datanascita=?, ruolo_id=?  where id=?";
    private String QueryDeleteUser="DELETE FROM test1_users WHERE id = ?";
    private String QuerySelectUser2="Select * from test1_users tu JOIN test1_roles tr ON tr.id=ruolo_id JOIN test1_credenziali tc ON tc.iduser=tu.id WHERE tu.cf = ?";
    private String QuerySelectUserID = "SELECT * FROM test1_users WHERE cf = ?";
    private String QuerySelectAllUsers="Select * from test1_users tu INNER JOIN test1_roles tr ON tr.id=tu.ruolo_id INNER JOIN test1_credenziali tc ON tc.iduser = tu.id";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    @Transactional()
    public int insertUser(User u, Ruolo r, Credenziali c) {
        try {
            jdbcTemplate.update(QueryInsertUser,
                    new Object[] {u.getName(), u.getSurname(), u.getCf(), u.getDateOfBirth(), r.getId()});
            jdbcTemplate.update(QueryInsertCredenziali,
                    new Object[] {c.getEmail(), c.getPwd()});
            return 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteUser(int id) {
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(QueryDeleteUser, args);
    }

    RowMapper<UserRest> rowMapper = (rs, rowNum) -> {
        UserRest userR = new UserRest();
        userR.setName(rs.getString("nome"));
        userR.setSurname(rs.getString("cognome"));
        userR.setIdRole(rs.getInt("ruolo_id"));
        userR.setEmail(rs.getString("email"));
        userR.setPassword(rs.getString("pwd"));
        userR.setCf(rs.getString("cf"));
        userR.setDateOfBirth(rs.getDate("datanascita"));
        return userR;
    };

    @Override
    public List<UserRest> selectUser(String cf){
        return jdbcTemplate.query(QuerySelectUser2, rowMapper,
                new Object[] {cf});
    }

    RowMapper<UserRest> rowMapper2 = (rs, rowNum) -> {
        UserRest user = new UserRest();
        user.setId(rs.getInt("id"));
        return user;
    };

    @Override
    public int selectUserID(String cf){
        Object[] args = new Object[] {cf};
        List<UserRest> u = jdbcTemplate.query(QuerySelectUserID, rowMapper2 ,args);
        return u.get(0).getId();
    }

    RowMapper<UserRest> rowMapper3 = (rs, rowNum) -> {
        UserRest user = new UserRest();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("nome"));
        user.setSurname(rs.getString("cognome"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("pwd"));
        user.setCf(rs.getString("cf"));
        user.setDateOfBirth(rs.getDate("datanascita"));
        user.setIdRole(rs.getInt("ruolo_id"));
        return user;
    };

    @Override
    public List<UserRest> selectAllUsers() {
        return jdbcTemplate.query(QuerySelectAllUsers, rowMapper3);
    }

    @Override
    public int updateUser(User u) {

        return jdbcTemplate.update(QueryUpdateUser,
                new Object[] { u.getName(), u.getSurname(), u.getCf(), u.getDateOfBirth(), u.getRole(), u.getId()});
    }

}
