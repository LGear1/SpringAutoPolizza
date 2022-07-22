package it.rjcsoft.springautopolizza.dto;


import it.rjcsoft.springautopolizza.modelrest.UserRest;

import java.util.List;

public class UserResponse extends BaseResponse{

    private List<UserRest> listaUser;

    public UserResponse(String statusResponse, String message) {
        super(statusResponse, message);
    }

    public List<UserRest> getListaUser() {
        return listaUser;
    }

    public void setListaUser(List<UserRest> listaUser) {
        this.listaUser = listaUser;
    }
}
