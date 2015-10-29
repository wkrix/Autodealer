package hu.klayton.wade.tests.config;

import hu.klayton.wade.spring.configuration.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Configuration
@Order(1)
public class NoCsrfSecurityConfig extends SecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }

}
