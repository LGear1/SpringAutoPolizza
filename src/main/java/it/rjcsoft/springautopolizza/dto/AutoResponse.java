package it.rjcsoft.springautopolizza.dto;

import it.rjcsoft.springautopolizza.modelrest.AutoRest;

import java.util.List;

public class AutoResponse extends BaseResponse{

    private List<AutoRest> auto;

    public AutoResponse(String statusResponse, String message) {
        super(statusResponse, message);
    }

    public List<AutoRest> getAuto() {
        return auto;
    }

    public void setAuto(List<AutoRest> auto) {
        this.auto = auto;
    }
}
