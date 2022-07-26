package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public int insertAuto(Auto auto)  {
        Timestamp inizioPolizza=null;
        Timestamp finePolizza=null;
        Date date=null;
        try {
           inizioPolizza=Timestamp.valueOf(auto.getInizio_polizza());
           finePolizza=Timestamp.valueOf(auto.getFine_polizza());
           date=stringToDate(auto.getDatarevisione());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jdbcTemplate.update(QueryInsertAuto,
                new Object[] { auto.getMarca(), auto.getModello(), auto.getTarga(), auto.getProprietario(), auto.getPrezzo_auto(), date, inizioPolizza, finePolizza});
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
        auto.setFine_polizza(""+rs.getTimestamp("fine_polizza"));
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
    public int updateAuto(int id,Auto auto) {
        Timestamp inizioPolizza=null;
        Timestamp finePolizza=null;
        Date date=null;
        try {
            inizioPolizza=Timestamp.valueOf(auto.getInizio_polizza());
            finePolizza=Timestamp.valueOf(auto.getFine_polizza());
            date=stringToDate(auto.getDatarevisione());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return jdbcTemplate.update(QueryUpdateAuto,
                new Object[] {  auto.getMarca(), auto.getModello(), auto.getProprietario(), inizioPolizza, finePolizza, date, id});
    }

    private Date stringToDate(String ToBeConverted)throws ParseException {
        java.util.Date date_casted=null;
        Date dateSql=null;

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        date_casted=sdf.parse(ToBeConverted);
        dateSql=new Date(date_casted.getTime());

        return dateSql;
    }
}
