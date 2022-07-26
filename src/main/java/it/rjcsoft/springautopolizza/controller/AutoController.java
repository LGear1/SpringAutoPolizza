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
    public ResponseEntity<BaseResponse> callInsert(@RequestBody AutoRest request) throws ParseException {
        ResponseEntity<BaseResponse> responseEntity = null;
        Auto auto = auB.buildAutoFromRest(request);
        try {
            autoRepository.insertAuto(auto);
        }catch (Exception e) {
            e.printStackTrace();
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    @DeleteMapping(path="deleteAuto/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callDeleteAuto(@PathVariable("id") int id ){
        try{
            int result = autoRepository.deleteAuto(id);
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch(Exception e){
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    @PutMapping(path="updateAuto/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callAggiornaAuto(@PathVariable("id") int id, @RequestBody AutoRest request)    {
        try {
            Auto auto = auB.buildAutoFromRest(request);
            auto.setId(id);
            int result = autoRepository.updateAuto(id,auto);
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch (Exception e) {
            e.printStackTrace();
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    @GetMapping(path="selectAllAuto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutoResponse> callSelectAuto(){
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
    
}
