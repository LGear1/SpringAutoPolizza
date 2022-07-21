package it.rjcsoft.springautopolizza.modelrest.builder;
import it.rjcsoft.springautopolizza.model.Auto;
import it.rjcsoft.springautopolizza.model.User;
import it.rjcsoft.springautopolizza.modelrest.AutoRest;
import it.rjcsoft.springautopolizza.modelrest.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBuilder {

    public User buildUserFromRest(UserRest userRest){
        User u = new User();
        BeanUtils.copyProperties(userRest,u);
        return u;
    }

    public UserRest buildRestFromUser(User user){
        UserRest ur = new UserRest();
        BeanUtils.copyProperties(user,ur);
        return ur;
    }

    public List<UserRest> buildRestFromUserList(List<User> user){
        List<UserRest> urList = new ArrayList<>();
        for(User each:user){
            urList.add(buildRestFromUser(each));
        }
        return urList;
    }
}
