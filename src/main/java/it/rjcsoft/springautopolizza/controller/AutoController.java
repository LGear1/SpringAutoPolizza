package it.rjcsoft.springautopolizza.controller;

import ch.qos.logback.core.CoreConstants;
import it.rjcsoft.springautopolizza.dto.*;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import it.rjcsoft.springautopolizza.modelrest.builder.AutoBuilder;
import it.rjcsoft.springautopolizza.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.sql.Date;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@RestController
@RequestMapping("/auto")
public class AutoController {
    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private AutoBuilder auB;
    @PostMapping(path = "insertauto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> callInsert(@RequestBody InsertAutoRequest request) throws ParseException {
        ResponseEntity<BaseResponse> responseEntity = null;
        Auto auto = new Auto(request.getMarca(), request.getModello(), request.getTarga(), request.getProprietario(), request.getPrezzo_auto(), request.getDatarevisione(), request.getInizio_polizza(), request.getFine_polizza());
        AutoBuilder autoB = new AutoBuilder();
        AutoRest auR = new AutoRest();
        auR = autoB.buildRestFromAuto(auto);
        Timestamp inizioPolizza2=Timestamp.valueOf(auR.getInizio_polizza());
        Timestamp finePolizza2=Timestamp.valueOf(auR.getFine_polizza());
        Date date=stringToDate(auR.getDatarevisione());
        try {
            autoRepository.insertAuto(auR.getMarca(), auR.getModello(), auR.getTarga(), auR.getProprietario(), auR.getPrezzo_auto(), date, inizioPolizza2, finePolizza2);
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

    @DeleteMapping(path="deleteAuto/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callDeleteAuto(@PathVariable("id") int id ){
        try{
            Auto auto = new Auto();
            auto.setId(id);
            AutoRest auR;
            auR = auB.buildRestFromAuto(auto);
            int result = autoRepository.deleteAuto(auR.getId());
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch(Exception e){
            return buildBaseResponse(e);
        }
        return buildBaseResponse(null);
    }

    @PutMapping(path="updateAuto/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callAggiornaAuto(@PathVariable("id") int id, @RequestBody UpdateAutoRequest request)    {
        try {
            Auto auto = new Auto(request.getMarca(), request.getModello(), request.getPrezzo_auto(), request.getDatarevisione(), request.getInizio_polizza(), request.getFine_polizza());
            auto.setId(id);
            AutoBuilder autoB = new AutoBuilder();
            AutoRest auR;
            auR = autoB.buildRestFromAuto(auto);
            Timestamp inizioPolizza2=Timestamp.valueOf(auR.getInizio_polizza());
            Timestamp finePolizza2=Timestamp.valueOf(auR.getFine_polizza());
            Date date = stringToDate(auR.getDatarevisione());
            int result = autoRepository.updateAuto(auR.getId(), auR.getMarca(), auR.getModello(), auR.getProprietario(), date, inizioPolizza2, finePolizza2);
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
    public ResponseEntity<AutoResponse> callSelectAll(){
        try{
            List<Auto> a = autoRepository.selectAllAuto();
            if(a.size() == 0) throw new SQLWarning("Auto non trovata!!!");
            return buildAutoResponse(null, a);
        }catch(Exception e){
            return buildAutoResponse(e, null);

        }

    }

    @GetMapping(path="selectAuto/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutoResponse> callSelectAll(@PathVariable("id") int id){
        try{
            Auto au = new Auto();
            au.setId(id);
            AutoRest auR = auB.buildRestFromAuto(au);
            List<Auto> a = autoRepository.selectAuto(auR.getId());
            if(a.size() == 0) throw new SQLWarning("Auto non trovata!!!");
            return buildAutoResponse(null, a);
        }catch(Exception e){
            System.out.println(e);
            return buildAutoResponse(e, null);
        }

    }

    private ResponseEntity<AutoResponse> buildAutoResponse(Exception e, List<Auto> listaAuto){
        if(e == null){
            AutoResponse response = new AutoResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            response.setListaAuto(auB.buildAutoRestList(listaAuto));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            AutoResponse response = new AutoResponse(EnumStatusResponse.CAR_NOT_FOUND.getStatus(), EnumStatusResponse.CAR_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            AutoResponse response = new AutoResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<BaseResponse> buildBaseResponse(Exception e){
        if(e == null){
            BaseResponse response = new BaseResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            BaseResponse response = new BaseResponse(EnumStatusResponse.CAR_NOT_FOUND.getStatus(), EnumStatusResponse.CAR_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            BaseResponse response = new BaseResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
