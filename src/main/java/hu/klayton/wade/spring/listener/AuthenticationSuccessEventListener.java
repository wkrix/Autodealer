package hu.klayton.wade.spring.listener;

import hu.klayton.wade.spring.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        loginAttemptService.loginSucceeded(auth.getRemoteAddress());
    }

}
