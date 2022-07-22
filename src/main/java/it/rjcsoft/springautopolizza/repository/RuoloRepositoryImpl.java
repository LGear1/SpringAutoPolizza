package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RuoloRepositoryImpl implements RuoloRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private String QueryRuoli = "select * from test1_roles";
    RowMapper<Ruolo> rowMapper = (rs, rowNum) -> {
        Ruolo ruolo = new Ruolo();
        ruolo.setRuolo(rs.getString("ruolo"));
        ruolo.setId(rs.getInt("id"));
        return ruolo;
    };
    @Override
    public List<Ruolo> selectAllRuoli(){
        return jdbcTemplate.query(QueryRuoli, rowMapper);
    }
}
