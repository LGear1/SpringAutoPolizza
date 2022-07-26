package it.rjcsoft.springautopolizza.modelrest.builder;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AutoBuilder {
    public Auto buildAutoFromRest(AutoRest autorest){
        Auto a = new Auto();
        a.setMarca(autorest.getMarca());
        a.setModello(autorest.getModello());
        a.setProprietario(autorest.getProprietario());
        a.setPrezzo_auto(autorest.getPrezzo_auto());
        a.setDatarevisione(autorest.getDatarevisione());
        a.setTarga(autorest.getTarga());
        a.setInizio_polizza(autorest.getInizio_polizza());
        a.setFine_polizza(autorest.getFine_polizza());
        return a;
    }

    public AutoRest buildRestFromAuto(Auto auto){
        AutoRest ar=new AutoRest();
        BeanUtils.copyProperties(auto,ar);
        return ar;

    }

    public List<AutoRest> buildAutoRestList(List<Auto> auto){
        List<AutoRest> arList = new ArrayList<AutoRest>();
        for(Auto each:auto){
            arList.add(buildRestFromAuto(each));
        }
        return arList;

    }
}
