package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.*;
import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.modelrest.LoginRest;
import it.rjcsoft.springautopolizza.modelrest.builder.LoginBuilder;
import it.rjcsoft.springautopolizza.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLWarning;
import java.util.List;


@RestController
public class LoginController {

    @Autowired
    private LoginBuilder logBuild;

    @Autowired
    private LoginRepository logRepo;


    @PostMapping(path = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> callLogin(@RequestBody LoginRest request) {
        Credenziali cred = new Credenziali(request);
        List<Credenziali> listaLog;
        try {
            listaLog = logRepo.login(cred);
            if (listaLog.size() == 0) throw new SQLWarning("Credenziali non valide!!");
        } catch (Exception e) {
            e.printStackTrace();
            return buildLoginResponse(e);
        }
        return buildLoginResponse(null);
    }

    private ResponseEntity<LoginResponse> buildLoginResponse(Exception e){
        if(e == null){
            LoginResponse response = new LoginResponse(EnumStatusResponse.ACCESS_CONFIRMED.getStatus(), EnumStatusResponse.ACCESS_CONFIRMED.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            LoginResponse response = new LoginResponse(EnumStatusResponse.ACCESS_DENIED.getStatus(), EnumStatusResponse.ACCESS_DENIED.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        else{
            LoginResponse response = new LoginResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public String callL() {
        return "Ciao";

    }
}
