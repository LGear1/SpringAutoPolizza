package it.rjcsoft.springautopolizza;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface AutoDBInterface {
    boolean insertAuto(String brand, String model, String l_plate, int owner, double carPrice, Date revisioneDate, Timestamp s_insurancePolicy, Timestamp f_insurancePolicy);
    boolean deleteAuto(String l_plate);
    List<Auto> selectAuto(int id);
    List<Auto> selectAllAuto();
    boolean updateAuto(String brand, String model, double carPrice, Date revisioneDate, Timestamp s_insurancePolicy, Timestamp f_insurancePolicy);

}
