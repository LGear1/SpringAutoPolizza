package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.*;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;

import it.rjcsoft.springautopolizza.modelrest.UserRest;

import it.rjcsoft.springautopolizza.modelrest.builder.UserBuilder;

import it.rjcsoft.springautopolizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLWarning;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserBuilder usB;

    @PostMapping(path = "insertuser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> callInsert(@RequestBody InsertUserRequest request) throws ParseException {
        ResponseEntity<BaseResponse> responseEntity = null;
        User user = new User(request.getNome(), request.getCognome(), request.getEmail(), request.getPassword(), request.getCf(), request.getDateOfBirth(), request.getRuolo());
        Ruolo ruolo = new Ruolo();
        if(request.getRuolo() == 1){
            ruolo.setId(1);
            ruolo.setRuolo("Admin");
        }else{
            ruolo.setId(2);
            ruolo.setRuolo("Guest");
        }
        UserBuilder userB = new UserBuilder();
        UserRest usR = new UserRest();
        usR = userB.buildRestFromUser(user, ruolo);
        try {
            userRepository.insertUser(usR.getName(),usR.getSurname(),usR.getEmail(),usR.getPassword(),usR.getCf(),usR.getDateOfBirth(),usR.getIdRole());
        }catch (Exception e) {
            e.printStackTrace();
            return buildBaseResponse(e);
        }
        return buildBaseResponse(null);
    }

    private ResponseEntity<UserResponse> buildUserResponse(Exception e, List<User> listaUser, List<Ruolo> listaRuoli){
        if(e == null){
            UserResponse response = new UserResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            response.setListaUser(usB.buildRestFromUserList(listaUser, listaRuoli));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            UserResponse response = new UserResponse(EnumStatusResponse.CAR_NOT_FOUND.getStatus(), EnumStatusResponse.CAR_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            UserResponse response = new UserResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
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
