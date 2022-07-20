package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.repository.AutoRepository;
import it.rjcsoft.springautopolizza.repository.AutoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class AutoController {
    @Autowired
    private it.rjcsoft.springautopolizza.repository.AutoRepository AutoRepository;
    @GetMapping("/")
    public void callInsert() throws ParseException {
        Timestamp inizioPolizza=Timestamp.valueOf("2022-01-01 00:00:00.0");
        Timestamp finePolizza=Timestamp.valueOf("2022-01-10 00:00:00.0");
        Date date=stringToDate("2022-02-02");
        AutoRepository.insertAuto("Lexus","Lfa","EA876II",2,700000,date, inizioPolizza ,finePolizza);

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
