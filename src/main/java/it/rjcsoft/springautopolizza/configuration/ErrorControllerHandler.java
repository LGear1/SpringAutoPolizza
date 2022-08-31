package it.rjcsoft.springautopolizza.configuration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerHandler implements ErrorController {

    public String getErrorPath(){
        return "/templates";
    }
    @RequestMapping("/templates")
    public String HandleError(HttpServletRequest request, Model model){
        String errorPage = "templates";
        String pageTitle = "Error";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status!=null){
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()){
                pageTitle = "page not Found";
                errorPage = "templates/404";
            }
        }
        model.addAttribute("pageTitle", pageTitle);
        return errorPage;
    }
}
