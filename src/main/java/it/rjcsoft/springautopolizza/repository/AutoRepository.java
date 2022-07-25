package it.rjcsoft.springautopolizza.repository;

import it.rjcsoft.springautopolizza.model.Auto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface AutoRepository {
    int insertAuto(Auto auto);
    int deleteAuto(int id);

    List<Auto> selectAuto(int id);

    List<Auto> selectAllAuto();

    int updateAuto(int id, Auto auto);
}

