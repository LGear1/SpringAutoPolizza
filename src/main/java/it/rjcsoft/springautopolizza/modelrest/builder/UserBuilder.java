package it.rjcsoft.springautopolizza.modelrest.builder;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.Ruolo;
import it.rjcsoft.springautopolizza.model.User;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import it.rjcsoft.springautopolizza.modelrest.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBuilder {

    public User buildUserAndRuoloFromUserRest(UserRest userRest){
        User u = new User();
        Ruolo r = new Ruolo();
        BeanUtils.copyProperties(userRest,u);
        r.setRuolo(userRest.getRole());
        return u;
    }

    public UserRest buildRestFromUser(User user, Ruolo ruolo){
        UserRest ur = new UserRest();
        ur.setId(user.getId());
        ur.setName(user.getName());
        ur.setSurname(user.getSurname());
        ur.setEmail(user.getEmail());
        ur.setPassword(user.getPassword());
        ur.setCf(user.getCf());
        ur.setDateOfBirth(user.getDateOfBirth());
        ur.setRole(ruolo.getRuolo());
        ur.setIdRole(ruolo.getId());
        return ur;
    }

    public List<UserRest> buildRestFromUserList(List<User> user, List<Ruolo> ruolo){
        List<UserRest> urList = new ArrayList<>();
        for(User each:user){
            for(Ruolo each2:ruolo){
                if(each2.getId() == each.getRole()){
                    urList.add(buildRestFromUser(each, each2));
                }
            }
        }
        return urList;
    }
}
