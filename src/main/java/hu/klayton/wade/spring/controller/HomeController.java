package hu.klayton.wade.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Controller
public class HomeController {

    private static final String LOCATION = "views/home/home";

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String hello() {
        return LOCATION;
    }

}
