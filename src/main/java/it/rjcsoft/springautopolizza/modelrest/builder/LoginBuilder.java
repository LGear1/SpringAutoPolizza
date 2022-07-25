package it.rjcsoft.springautopolizza.modelrest.builder;

import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.modelrest.LoginRest;
import org.springframework.stereotype.Component;

@Component
public class LoginBuilder {

    public LoginRest buildRestFromLogin(Credenziali login){
        LoginRest logR = new LoginRest();
        logR.setEmail(login.getEmail());
        logR.setPwd(login.getPwd());
        return logR;
    }
}
