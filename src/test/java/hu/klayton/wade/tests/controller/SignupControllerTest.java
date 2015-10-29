package hu.klayton.wade.tests.controller;

import hu.klayton.wade.tests.config.BaseTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class SignupControllerTest extends BaseTest {

    @Test
    public void displaysSignupForm() throws Exception {

        mockMvc.perform(get("/signup"))
                .andExpect(model().attributeExists("sellerDTO"))
                .andExpect(view().name("views/signup/signup"));
    }
}

