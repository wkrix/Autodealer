package hu.klayton.wade.tests.controller;

import hu.klayton.wade.spring.entity.Seller;
import hu.klayton.wade.spring.service.LoginAttemptService;
import hu.klayton.wade.spring.service.UserService;
import hu.klayton.wade.tests.config.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class SigninControllerTest extends BaseTest {


    @Autowired
    private UserService userService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Test
    public void requiresAuthentication() throws Exception {
        mockMvc.perform(get("/vehicle/list_vehicles"))
                .andExpect(redirectedUrl("http://localhost/signin"));
    }

    @Test
    public void displaysSigninForm() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(view().name("views/signin/signin"));
    }

    @Test
    public void userAuthenticates() throws Exception {
        final String username = "walter";
        final String password = "jelszo";

        ResultMatcher matcher = new ResultMatcher() {
            public void match(MvcResult mvcResult) throws Exception {
                HttpSession session = mvcResult.getRequest().getSession();
                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                Assert.assertEquals(securityContext.getAuthentication().getName(), username);
            }
        };
        mockMvc.perform(post("/authenticate").param("username", username).param("password", password))
                .andExpect(redirectedUrl("/"))
                .andExpect(matcher);
    }

    @Test
    public void userAuthenticationFails() throws Exception {
        final String username = "walter";
        ResultMatcher matcher = new ResultMatcher() {
            public void match(MvcResult mvcResult) throws Exception {
                HttpSession session = mvcResult.getRequest().getSession();
                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                Assert.assertNull(securityContext);
            }
        };
        mockMvc.perform(post("/authenticate").param("username", username).param("password", "badPassword"))
                .andExpect(redirectedUrl("/signin?error=1"))
                .andExpect(matcher);
    }

    @Test
    public void userAuthenticationUntilBan() throws Exception {
        final String username = "userForBan";
        final String password = "password";
        Seller savedSeller = userService.save(new Seller(username, password, "ROLE_USER"));

        ResultMatcher matcher = new ResultMatcher() {
            @Override
            public void match(MvcResult mvcResult) throws Exception {
                HttpSession session = mvcResult.getRequest().getSession();
                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                Assert.assertTrue((securityContext == null)
                        && (loginAttemptService.isBlocked(mvcResult.getRequest().getRemoteAddr())));
                loginAttemptService.clearIP(mvcResult.getRequest().getRemoteAddr());
            }
        };

        ResultActions resultActions = null;
        for (int i = 0; i <= LoginAttemptService.MAX_ATTEMPT + 1; i++) {
            resultActions = mockMvc.perform(post("/authenticate")
                    .param("username", username).param("password", "badPassword"));
        }
        resultActions.andExpect(matcher);

        userService.remove(savedSeller);
    }

}
