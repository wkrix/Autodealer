package hu.klayton.wade.spring.listener;

import hu.klayton.wade.spring.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        loginAttemptService.loginFailed(auth.getRemoteAddress());
    }
}
