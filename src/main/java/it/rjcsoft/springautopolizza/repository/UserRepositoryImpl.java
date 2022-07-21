package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String QueryInsertUser="Insert into test1_users (nome, cognome, cf, datanascita, ruolo_id) VALUES (?,?,?,?,?)";
    private String QueryDeleteUser="DELETE FROM test1_users WHERE id = ?";
    private String QuerySelectUser="Select * from test1_users tu JOIN test1_roles tr ON tr.id=ruolo_id JOIN test1_credenziali tc ON tc.iduser=tu.id WHERE tu.id = ?";
    private String QuerySelectUser2="Select * from test1_users tu WHERE tu.cf = ?";
    private String QueryUpdateUser="Update test1_users set nome=?, cf=? where id=?";
    private String QuerySelectAllUsers="Select * from test1_users tu INNER JOIN test1_roles tr ON tr.id=tu.ruolo_id INNER JOIN test1_credenziali tc ON tc.iduser = tu.id";

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("nome"));
        user.setSurname(rs.getString("cognome"));
        user.setEmail(rs.getString("nome"));
        user.setPassword(rs.getString("password"));
        user.setCf(rs.getString("cf"));
        user.setDateOfBirth(rs.getDate("datanascita"));
        user.setRole(rs.getInt("ruolo_id"));
        return user;
    };

    @Override
    public List<User> selectAllUsers(int ruoloid, int idcred) {

        return jdbcTemplate.query(QuerySelectAllUsers, rowMapper,ruoloid,idcred);
    }
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
    public int updateUser(String name, String surname, String cf, Date dateOfBirth, int role) {

        return jdbcTemplate.update(QueryUpdateUser,
                new Object[] { name, surname, cf, dateOfBirth, role});
    }
}
