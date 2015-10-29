package hu.klayton.wade.tests.config;

import hu.klayton.wade.spring.configuration.JpaConfig;
import hu.klayton.wade.spring.configuration.WebAppConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.HashSet;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@org.springframework.test.context.web.WebAppConfiguration
@ContextConfiguration(classes = {
        WebAppConfiguration.class,
        JpaConfig.class,
        NoCsrfSecurityConfig.class
})
public abstract class BaseTest {

    public final static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    @Autowired
    protected WebApplicationContext context;


    protected MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilter;

    @Before
    public void before() {
        mockMvc = webAppContextSetup(context).addFilters(springSecurityFilter).build();
    }

    public MockHttpSession makeAuthSession(String username, String... roles) {
        if (StringUtils.isEmpty(username)) {
            username = "walter";
        }

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SEC_CONTEXT_ATTR, SecurityContextHolder.getContext());

        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (roles != null && roles.length > 0) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        Authentication authToken = new UsernamePasswordAuthenticationToken(username, "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
}