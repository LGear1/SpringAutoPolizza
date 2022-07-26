package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Credenziali;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginRepositoryImpl implements  LoginRepository{

    private String QueryCheckLogin = "SELECT * FROM test1_credenziali WHERE email=? AND pwd=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Credenziali> rowMapper = (rs, rowNum) -> {
        Credenziali login = new Credenziali();
        login.setIduser(rs.getInt("iduser"));
        login.setEmail(rs.getString("email"));
        login.setPwd(rs.getString("pwd"));
        return login;
    };


    @Override
    public List<Credenziali> login(Credenziali cred) {
            return jdbcTemplate.query(QueryCheckLogin, rowMapper, new Object[] {cred.getEmail(), cred.getPwd()});
    }
}
