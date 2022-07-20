package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.InsertAutoRequest;
import it.rjcsoft.springautopolizza.dto.UpdateAutoRequest;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.repository.AutoRepository;
import it.rjcsoft.springautopolizza.repository.AutoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@RestController
public class AutoController {
    @Autowired
    private AutoRepository autoRepository;
    @PostMapping(path = "insertauto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void callInsert(@RequestBody InsertAutoRequest request) throws ParseException {
        Timestamp inizioPolizza2=Timestamp.valueOf(request.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(request.getFine_polizza());
        Date date=stringToDate(request.getDatarevisione());
        Auto auto = new Auto(request.getMarca(), request.getModello(), request.getTarga(), request.getProprietario(), request.getPrezzo_auto(), date, inizioPolizza2, finePolizza2);
        try {
            autoRepository.insertAuto(auto.getMarca(), auto.getModello(), auto.getTarga(), auto.getProprietario(), auto.getPrezzo_auto(), auto.getDatarevisione(), auto.getInizio_polizza(), auto.getFine_polizza());
        } catch (DuplicateKeyException f){
            System.out.println("chiave duplicata");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private Date stringToDate(String ToBeConverted)throws  ParseException{
        java.util.Date date_casted=null;
        Date dateSql=null;

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        date_casted=sdf.parse(ToBeConverted);
        dateSql=new Date(date_casted.getTime());

        return dateSql;
    }

    @PutMapping(path="updateAuto",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public void callAggiornaAuto(@RequestBody UpdateAutoRequest request)    {
        Timestamp inizioPolizza2=Timestamp.valueOf(request.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(request.getFine_polizza());
        Date date= null;
        try {
            date = stringToDate(request.getDatarevisione());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
       int id=request.getId();
        try {
            autoRepository.updateAuto(id,request.getMarca(), request.getModello(), request.getProprietario(), date, inizioPolizza2, finePolizza2);
        } catch (DuplicateKeyException f){
            System.out.println("chiave duplicata");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
}
