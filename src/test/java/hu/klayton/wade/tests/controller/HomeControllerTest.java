package hu.klayton.wade.tests.controller;

import hu.klayton.wade.tests.config.BaseTest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class HomeControllerTest extends BaseTest {

    @Test
    public void displayHomeView() throws Exception {
        MockHttpSession session = makeAuthSession("andor", "ROLE_USER");
        mockMvc.perform(get("/").session(session)).andExpect(view().name("views/home/home"));
    }
}
