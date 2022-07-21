package it.rjcsoft.springautopolizza.modelrest.builder;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;

public class AutoBuilder {
    public Auto builAutoFromRest(AutoRest autorest){
        return null;
    }

    public AutoRest buildRestFromAuto(Auto auto){
        AutoRest ar=new AutoRest(auto.getId(), auto.getMarca(), auto.getModello(), auto.getTarga(), auto.getProprietario(), auto.getPrezzo_auto(), auto.getDatarevisione(), auto.getInizio_polizza(), auto.getFine_polizza());
        return ar;

    }
}
