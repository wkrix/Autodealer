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

//    @Test
//    public void testLoadRootWithAuth() throws Exception {
//        // Test basic home controller request with a session and logged in user
//
//        MvcResult result = mockMvc.perform(get("/").session(session))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        assertNotNull(content);
//        assertTrue(content.contains("Hello Spring Boot"));
//    }