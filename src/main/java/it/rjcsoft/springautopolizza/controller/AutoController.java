package it.rjcsoft.springautopolizza.controller;

import com.google.gson.Gson;
import it.rjcsoft.springautopolizza.dto.EnumStatusResponse;
import it.rjcsoft.springautopolizza.dto.InsertAutoRequest;
import it.rjcsoft.springautopolizza.dto.InsuranceResponse;
import it.rjcsoft.springautopolizza.dto.UpdateAutoRequest;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.repository.AutoRepository;
import it.rjcsoft.springautopolizza.repository.AutoRepositoryImpl;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auto")
public class AutoController {
    @Autowired
    private AutoRepository autoRepository;
    @PostMapping(path = "insertauto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceResponse> callInsert(@RequestBody InsertAutoRequest request) throws ParseException {
        ResponseEntity<InsuranceResponse> responseEntity = null;
        Timestamp inizioPolizza2=Timestamp.valueOf(request.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(request.getFine_polizza());
        Date date=stringToDate(request.getDatarevisione());
        Auto auto = new Auto(request.getMarca(), request.getModello(), request.getTarga(), request.getProprietario(), request.getPrezzo_auto(), date, inizioPolizza2, finePolizza2);
        try {
            autoRepository.insertAuto(auto.getMarca(), auto.getModello(), auto.getTarga(), auto.getProprietario(), auto.getPrezzo_auto(), auto.getDatarevisione(), auto.getInizio_polizza(), auto.getFine_polizza());
        }catch (Exception e) {
            e.printStackTrace();
            return buildBaseResponse(e);
        }
        return buildBaseResponse(null);
    }
    private Date stringToDate(String ToBeConverted)throws  ParseException{
        java.util.Date date_casted=null;
        Date dateSql=null;

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        date_casted=sdf.parse(ToBeConverted);
        dateSql=new Date(date_casted.getTime());

        return dateSql;
    }

    @PutMapping(path="updateAuto/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<InsuranceResponse> callAggiornaAuto(@PathVariable("id") int id, @RequestBody UpdateAutoRequest request)    {
        Timestamp inizioPolizza2=Timestamp.valueOf(request.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(request.getFine_polizza());
        Date date= null;
        try {
            date = stringToDate(request.getDatarevisione());

            int result = autoRepository.updateAuto(id,request.getMarca(), request.getModello(), request.getProprietario(), date, inizioPolizza2, finePolizza2);
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch (Exception e) {
            e.printStackTrace();
            return buildBaseResponse(e);
        }
        return buildBaseResponse(null);
    }

    @GetMapping(path="selectAllAuto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void callSelectAll(){
        List<Auto> a= autoRepository.selectAllAuto();



    }


    private ResponseEntity<InsuranceResponse> buildBaseResponse(Exception e){
        if(e == null){
            InsuranceResponse response = new InsuranceResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            InsuranceResponse response = new InsuranceResponse(EnumStatusResponse.CAR_NOT_FOUND.getStatus(), EnumStatusResponse.CAR_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            InsuranceResponse response = new InsuranceResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
