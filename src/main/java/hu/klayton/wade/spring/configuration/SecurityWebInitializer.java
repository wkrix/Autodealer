package hu.klayton.wade.spring.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 *         <p>
 *         A DelegatingFilterProxy konfiguralasahoz kell az AbstractSecurityWebApplicationInitializer osztalyt extendalni.
 *         Igy tudjuk a spring securityt elesiteni az alkalmazason.
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
}
