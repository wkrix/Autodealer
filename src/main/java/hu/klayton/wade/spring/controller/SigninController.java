package hu.klayton.wade.spring.controller;

import hu.klayton.wade.spring.error.LoginException;
import hu.klayton.wade.spring.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Controller
public class SigninController {

    private static final String LOCATION = "views/signin/signin";

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "signin")
    public String signin() {
        final String ip = request.getRemoteAddr();
        if (loginAttemptService.isBlocked(ip)) {
            throw new LoginException("Bad login. IP BAN.");
        }
        return LOCATION;
    }
}
