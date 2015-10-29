package hu.klayton.wade.spring.configuration;

import hu.klayton.wade.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(true)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/resources/**", "/signup", "/signin", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .permitAll()
                .failureUrl("/signin?error=1")
                .loginProcessingUrl("/authenticate")
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/signin?logout")
                .and()
                .csrf()
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices()).key("remember-me-key")
                .and()
                .addFilterBefore(getFilter(), CsrfFilter.class)
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
    }

    /**
     * A thymeleaf input UTF-8 kezelesehez szukseges.
     *
     * @return
     */
    private CharacterEncodingFilter getFilter() {
        final CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

}
