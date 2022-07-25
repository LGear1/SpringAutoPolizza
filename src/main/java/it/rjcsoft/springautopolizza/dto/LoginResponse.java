package it.rjcsoft.springautopolizza.dto;

import it.rjcsoft.springautopolizza.model.Credenziali;
import it.rjcsoft.springautopolizza.modelrest.UserRest;

import java.util.List;

public class LoginResponse extends BaseResponse{

    private List<Credenziali> listaLog;

    public LoginResponse(String statusResponse, String message) {
        super(statusResponse, message);
    }

    public List<Credenziali> getListLog() {
        return listaLog;
    }

    public void setListaLog(List<Credenziali> listaLog) {
        this.listaLog = listaLog;
    }
}
