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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLWarning;
import java.util.Base64;
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
    public ResponseEntity<BaseResponse> callLogin(@RequestBody LoginRequest request){
            Credenziali cred = new Credenziali();
            cred.setEmail(request.getEmail());
            cred.setPwd(Base64.getEncoder().encodeToString(request.getPwd().getBytes()));
            LoginRest logRest = new LoginRest();
            logRest = logBuild.buildRestFromLogin(cred);
            try {
                List<Credenziali> listaLog = logRepo.login(logRest.getEmail(), logRest.getPwd());
                if(listaLog.size()==0) throw new SQLWarning("Credenziali non valide!!");
            }catch (Exception e){
                e.printStackTrace();
                return buildBaseResponse(e);
            }
        return buildBaseResponse(null);
        }

    private ResponseEntity<LoginResponse> buildUserResponse(Exception e, List<Credenziali> listaLog){
        if(e == null){
            LoginResponse response = new LoginResponse(EnumStatusResponse.ACCESS_CONFIRMED.getStatus(), EnumStatusResponse.ACCESS_CONFIRMED.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            LoginResponse response = new LoginResponse(EnumStatusResponse.USER_NOT_FOUND.getStatus(), EnumStatusResponse.USER_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            LoginResponse response = new LoginResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<BaseResponse> buildBaseResponse(Exception e){
        if(e == null){
            BaseResponse response = new BaseResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            BaseResponse response = new BaseResponse(EnumStatusResponse.USER_NOT_FOUND.getStatus(), EnumStatusResponse.USER_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            BaseResponse response = new BaseResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
