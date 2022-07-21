package it.rjcsoft.springautopolizza.modelrest.builder;

import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import org.springframework.beans.BeanUtils;

public class AutoBuilder {
    public Auto buildAutoFromRest(AutoRest autorest){
        Auto a = new Auto();
        BeanUtils.copyProperties(autorest,a);
        return a;
    }

    public AutoRest buildRestFromAuto(Auto auto){
        AutoRest ar=new AutoRest();
        BeanUtils.copyProperties(auto,ar);
        return ar;

    }
}
