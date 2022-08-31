package it.rjcsoft.springautopolizza.configuration;

import it.rjcsoft.springautopolizza.controller.AutoController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private static final Logger logger = LogManager.getLogger(AutoController.class);
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/415").setViewName("415");
        registry.addViewController("/500").setViewName("500");
    }

    @GetMapping("/403")
    public ModelAndView ErrorLog403(){
        logger.error(" [Error 403 - Access Denied]");
        return new ModelAndView("403");
    }
    @GetMapping("/404")
    public ModelAndView ErrorLog404(){
        logger.error(" [Error 404 - Page not found]");
        return new ModelAndView("404");
    }
    @GetMapping("/415")
    public ModelAndView ErrorLog415(){
        logger.error(" [Error 415 - Unsupported media file]");
        return new ModelAndView("415");
    }
    @GetMapping("/500")
    public ModelAndView ErrorLog500(){
        logger.error(" [Error 500 - Internal Server Error]");
        return new ModelAndView("500");
    }

}
