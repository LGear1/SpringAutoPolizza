package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import it.rjcsoft.springautopolizza.model.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AutoRepositoryImpl implements AutoRepository {

    private String QuerySelectAuto2 = "SELECT * FROM test1_auto WHERE id = ?";
    private String QueryInsertAuto="Insert into test1_auto (marca, modello, targa, proprietario, prezzo_auto, datarevisione, inizio_polizza, fine_polizza ) VALUES (?,?,?,?,?,?,?,?)";
    private String QueryDeleteAuto="DELETE FROM test1_auto WHERE id = ?";
    private String QuerySelectAuto="Select ta.*, tu.cf from test1_auto ta INNER JOIN test1_users tu ON tu.id=ta.proprietario WHERE proprietario = ?";
    private String QuerySelectAutoLimitOffset="Select ta.*, tu.cf from test1_auto ta INNER JOIN test1_users tu ON tu.id=ta.proprietario LIMIT ? OFFSET ?";
    private String QueryUpdateAuto="Update test1_auto set  marca=?, modello=?, prezzo_auto=?, datarevisione=?, inizio_polizza=?, fine_polizza=? where id=?";
    private String QuerySelectAllAuto = "SELECT * FROM test1_auto";

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override

    public int insertAuto(String brand, String model, String l_plate, int owner, double carPrice, Date revisionDate, Timestamp s_insurancePolicy, Timestamp f_insurancePolicy)  {

        try {
            return jdbcTemplate.update(QueryInsertAuto,
                    new Object[] { brand, model, l_plate, owner, carPrice, revisionDate, s_insurancePolicy, f_insurancePolicy});
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteAuto(int id) {
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(QueryDeleteAuto, args);
    }

    RowMapper<Auto> rowMapper = (rs, rowNum) -> {
        Auto auto = new Auto();
        auto.setId(rs.getInt("id"));
        auto.setMarca(rs.getString("marca"));
        auto.setModello(rs.getString("modello"));
        auto.setTarga(rs.getString("targa"));
        auto.setProprietario(rs.getInt("proprietario"));
        auto.setPrezzo_auto(rs.getDouble("prezzo_auto"));
        auto.setDatarevisione(""+rs.getDate("datarevisione"));
        auto.setInizio_polizza(""+rs.getTimestamp("inizio_polizza"));
        auto.setFine_polizza_polizza(""+rs.getTimestamp("fine_polizza"));
        return auto;
    };


    @Override
    public List<Auto> selectAuto(int id){
        Object[] args = new Object[] {id};
        return jdbcTemplate.query(QuerySelectAuto2, rowMapper ,args);
    }

    @Override
    public List<Auto> selectAllAuto() {
        return jdbcTemplate.query(QuerySelectAllAuto, rowMapper);

    }

    @Override
    public int updateAuto(int id,String brand, String model, double carPrice, Date revisioneDate, Timestamp s_insurancePolicy, Timestamp f_insurancePolicy) {
         return jdbcTemplate.update(QueryUpdateAuto,
                new Object[] { brand, model, carPrice, revisioneDate, s_insurancePolicy, f_insurancePolicy, id});
    }

}
