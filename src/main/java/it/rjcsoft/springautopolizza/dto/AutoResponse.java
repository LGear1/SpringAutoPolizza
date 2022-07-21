package it.rjcsoft.springautopolizza.dto;

import it.rjcsoft.springautopolizza.modelrest.AutoRest;

import java.util.List;

public class AutoResponse extends BaseResponse{

    private List<AutoRest> listaAuto;

    public AutoResponse(String statusResponse, String message) {
        super(statusResponse, message);
    }

    public List<AutoRest> getListaAuto() {
        return listaAuto;
    }

    public void setListaAuto(List<AutoRest> listaAuto) {
        this.listaAuto = listaAuto;
    }
}
