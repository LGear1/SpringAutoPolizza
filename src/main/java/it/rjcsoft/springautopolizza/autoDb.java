package it.rjcsoft.springautopolizza;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class autoDb implements AutoDBInterface {

    private String QuerySelectAuto2 = "SELECT * FROM test1_auto WHERE id = ?";
    private String QueryInsertAuto="Insert into test1_auto (marca, modello, targa, proprietario, prezzo_auto, datarevisione, inizio_polizza, fine_polizza ) VALUES (?,?,?,?,?,?,?,?)";
    private String QueryDeleteAuto="DELETE FROM test1_auto WHERE targa = ?";
    private String QuerySelectAuto="Select ta.*, tu.cf from test1_auto ta INNER JOIN test1_users tu ON tu.id=ta.proprietario WHERE proprietario = ?";
    private String QuerySelectAutoLimitOffset="Select ta.*, tu.cf from test1_auto ta INNER JOIN test1_users tu ON tu.id=ta.proprietario LIMIT ? OFFSET ?";
    private String QueryUpdateAuto="Update test1_auto set  marca=?, modello=?, prezzo_auto=?, datarevisione=?, inizio_polizza=?, fine_polizza=? where id=?";

    private String id="id";
    private String marca="marca";
    private String modello="modello";
    private String targa="targa";
    private String proprietario="proprietario";
    private String prezzo_auto="prezzo_auto";
    private String datarevisione="datarevisione";
    private String inizio_polizza="inizio_polizza";
    private String fine_polizza="fine_polizza";
    private String cf="cf";
    private String iduser="iduser";

    public autoDb(){


    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override

    public int insertAuto(String brand, String model, String l_Plate, int owner, double carPrice, Date revisionDate, Timestamp startInsurancePolicy, Timestamp endInsurancePolicy )  {

            return jdbcTemplate.update(QueryInsertAuto,
                    new Object[] { brand, model, l_Plate,owner, carPrice, revisionDate, startInsurancePolicy, endInsurancePolicy});

    }

    @Override
    public boolean deleteAuto(String l_plate) {
        return false;
    }

    @Override
    public List<Auto> selectAuto(int id) {
        return null;
    }

    @Override
    public List<Auto> selectAllAuto() {
        return null;
    }

    @Override
    public boolean updateAuto(String brand, String model, double carPrice, Date revisioneDate, Timestamp s_insurancePolicy, Timestamp f_insurancePolicy) {
        return false;
    }
    /*
    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE tutorials SET title=?, description=?, published=? WHERE id=?",
                new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId() });
    }
    @Override
    public Tutorial findById(Long id) {
        try {
            Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * FROM tutorials WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class), id);
            return tutorial;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM tutorials WHERE id=?", id);
    }
    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * from tutorials", BeanPropertyRowMapper.newInstance(Tutorial.class));
    }
    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return jdbcTemplate.query("SELECT * from tutorials WHERE published=?",
                BeanPropertyRowMapper.newInstance(Tutorial.class), published);
    }
    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        String q = "SELECT * from tutorials WHERE title ILIKE '%" + title + "%'";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
    }
    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from tutorials");
    }

     */
}
