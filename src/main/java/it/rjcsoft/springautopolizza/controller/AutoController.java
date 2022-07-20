package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.repository.AutoRepository;
import it.rjcsoft.springautopolizza.repository.AutoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@RestController
public class AutoController {
    @PostMapping("/InsertAuto")
    public void callInsert(@RequestParam(name="brand") String brand,
                           @RequestParam(name="model") String model,
                           @RequestParam(name="l_plate") String l_plate,
                           @RequestParam(name="owner") int owner,
                           @RequestParam(name="carPrice") Double carPrice,
                           @RequestParam(name="revisionDate") Date revisionDate,
                           @RequestParam(name="inizioPolizza") String inizioPolizza,
                           @RequestParam(name="finePolizza") String finePolizza) throws ParseException {
        Timestamp inizioPolizza2=Timestamp.valueOf(inizioPolizza);
        Timestamp finePolizza2=Timestamp.valueOf(finePolizza);
        Date date=stringToDate("2022-02-02");
        AutoRepositoryImpl.insertAuto(brand,model,l_plate,owner,carPrice,revisionDate, inizioPolizza2 ,finePolizza2);

    }
    private static Date stringToDate(String ToBeConverted)throws  ParseException{
        java.util.Date date_casted=null;
        Date dateSql=null;

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        date_casted=sdf.parse(ToBeConverted);
        dateSql=new Date(date_casted.getTime());

        return dateSql;
    }
}
