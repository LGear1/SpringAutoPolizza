package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.*;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;

import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import it.rjcsoft.springautopolizza.modelrest.UserRest;

import it.rjcsoft.springautopolizza.modelrest.builder.AutoBuilder;
import it.rjcsoft.springautopolizza.modelrest.builder.UserBuilder;

import it.rjcsoft.springautopolizza.repository.RuoloRepository;
import it.rjcsoft.springautopolizza.repository.RuoloRepositoryImpl;
import it.rjcsoft.springautopolizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public ResponseEntity<BaseResponse> callInsertUser(@RequestBody InsertUserRequest request) throws ParseException {
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

    @DeleteMapping(path="deleteUser/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> calldeleteUser(@PathVariable("id") int id ){
        try{
            User user = new User();
            user.setId(id);
            UserRest usR = new UserRest();
            usR.setId(user.getId());
            int result = userRepository.deleteUser(usR.getId());
            if(result != 1)  throw new SQLWarning("Auto non trovata!!!");
        }catch(Exception e){
            return buildBaseResponse(e);
        }
        return buildBaseResponse(null);
    }

    @GetMapping(path="selectUser/{cf}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> callSelectUser(@PathVariable("cf") String cf){
        try{
            User user = new User();
            user.setCf(cf);
            UserRest usR = new UserRest();
            usR.setCf(user.getCf());
            List<User> a = userRepository.selectUser(usR.getCf());
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
            List<User> a = userRepository.selectAllUsers();
            List<Ruolo> r = ruoloRepository.selectAllRuoli();
            if(a.size() == 0) throw new SQLWarning("Utente non trovato");
            return buildUserResponse(null, a, r);
        }catch(Exception e){
            return buildUserResponse(e, null, null);

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
    @PutMapping(path="updateUser/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BaseResponse> callUpdateUser(@PathVariable("id") int id, @RequestBody UpdateUserRequest request){
        try {
            User user = new User();
            user.setId(id);
            user.setName(request.getName());
            user.setSurname(request.getCognome());
            user.setCf(request.getCf());
            user.setDateOfBirth(request.getDateOfBirth());
            user.setRole(request.getRole());
            Ruolo ruolo = new Ruolo();
            ruolo.setId(request.getRole());
            if(request.getRole() == 1){
                ruolo.setRuolo("Admin");
            }else{
                ruolo.setRuolo("Guest");
            }
            UserRest auR;
            auR = usB.buildRestFromUser(user, ruolo);
            int result = userRepository.updateUser(auR.getName(), auR.getSurname(), auR.getCf(), auR.getDateOfBirth(),auR.getIdRole(), auR.getId());
            if(result != 1)  throw new SQLWarning("User non trovato!!");
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
            UserResponse response = new UserResponse(EnumStatusResponse.USER_NOT_FOUND.getStatus(), EnumStatusResponse.USER_NOT_FOUND.getMessage() + " - "+ e.getMessage());
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
            BaseResponse response = new BaseResponse(EnumStatusResponse.USER_NOT_FOUND.getStatus(), EnumStatusResponse.USER_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            BaseResponse response = new BaseResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
