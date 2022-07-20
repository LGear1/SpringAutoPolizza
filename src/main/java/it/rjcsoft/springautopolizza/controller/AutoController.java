package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.InsertAutoRequest;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.repository.AutoRepositoryImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@RestController
public class AutoController {
    @PostMapping(path = "insertauto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void callInsert(@RequestBody InsertAutoRequest request) throws ParseException {
        Timestamp inizioPolizza2=Timestamp.valueOf(request.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(request.getFine_polizza());
        Date date=stringToDate(request.getDatarevisione());
        Auto auto = new Auto(request.getMarca(), request.getModello(), request.getTarga(), request.getProprietario(), request.getPrezzo_auto(), date, inizioPolizza2, finePolizza2);

        AutoRepositoryImpl.insertAuto(auto.getMarca(), auto.getModello(), auto.getTarga(), auto.getProprietario(), auto.getPrezzo_auto(), auto.getDatarevisione(), auto.getInizio_polizza(), auto.getFine_polizza());

    }
    private Date stringToDate(String ToBeConverted)throws  ParseException{
        java.util.Date date_casted=null;
        Date dateSql=null;

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        date_casted=sdf.parse(ToBeConverted);
        dateSql=new Date(date_casted.getTime());

        return dateSql;
    }
}
