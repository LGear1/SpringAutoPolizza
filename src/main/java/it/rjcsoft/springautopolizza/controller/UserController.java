package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.*;
import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import it.rjcsoft.springautopolizza.modelrest.UserRest;
import it.rjcsoft.springautopolizza.modelrest.builder.UserBuilder;
import it.rjcsoft.springautopolizza.repository.RuoloRepository;
import it.rjcsoft.springautopolizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.sql.SQLWarning;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private UserBuilder usB;

    public UserController() {
    }

    @PostMapping(path = "insertuser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> callInsertUser(@RequestBody UserRest request) throws ParseException {
        ResponseEntity<BaseResponse> responseEntity = null;
        User user = new User(request);
        Ruolo ruolo = new Ruolo(request);
        Credenziali credenziali = new Credenziali(request);
        try {
            userRepository.insertUser(user, ruolo, credenziali);
        }catch (Exception e) {
            e.printStackTrace();
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    @DeleteMapping(path="deleteUser/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> calldeleteUser(@PathVariable("id") int id ){
        try{
            int result = userRepository.deleteUser(id);
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch(Exception e){
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    @GetMapping(path="selectUser/{cf}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> callSelectUser(@PathVariable("cf") String cf){
        try{
            List<UserRest> a = userRepository.selectUser(cf);
            List<Ruolo> r = ruoloRepository.selectAllRuoli();
            if(a.size() == 0) throw new SQLWarning("Utente non trovato");
            return buildUserResponse(null, a, r );
        }catch(Exception e){
            System.out.println(e);
            return buildUserResponse(e, null, null);
        }

    }
    @GetMapping(path="selectAllUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> callSelectAuto(){
        try{
            List<UserRest> a = userRepository.selectAllUsers();
            List<Ruolo> r = ruoloRepository.selectAllRuoli();
            if(a.size() == 0) throw new SQLWarning("Utente non trovato");
            return buildUserResponse(null, a, r);
        }catch(Exception e){
            return buildUserResponse(e, null, null);

        }

    }

    @PutMapping(path="updateUser/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callUpdateUser(@PathVariable("id") int id, @RequestBody UserRest request){
        try {
            User user = new User(request);
            int result = userRepository.updateUser(user);
            if(result != 1)  throw new SQLWarning("User non trovato!!");
        }catch (Exception e) {
            e.printStackTrace();
            return BaseController.buildBaseResponse(e);
        }
        return BaseController.buildBaseResponse(null);
    }

    private ResponseEntity<UserResponse> buildUserResponse(Exception e, List<UserRest> listaUser, List<Ruolo> listaRuoli){
        if(e == null){
            UserResponse response = new UserResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            response.setListaUser(usB.buildRestFromUserList(listaUser, listaRuoli));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            UserResponse response = new UserResponse(EnumStatusResponse.USER_NOT_FOUND.getStatus(), EnumStatusResponse.USER_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            UserResponse response = new UserResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
