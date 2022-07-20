package it.rjcsoft.springautopolizza.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AutoRepositoryImpl implements AutoRepository {

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

    public AutoRepositoryImpl(){


    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override

    public int insertAuto(String brand, String model, String l_Plate, int owner, double carPrice, Date revisionDate, Timestamp startInsurancePolicy, Timestamp endInsurancePolicy)  {

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

}
