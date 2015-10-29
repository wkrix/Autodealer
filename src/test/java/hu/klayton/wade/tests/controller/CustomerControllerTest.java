package hu.klayton.wade.tests.controller;

import hu.klayton.wade.spring.entity.Customer;
import hu.klayton.wade.spring.repository.CustomerRepository;
import hu.klayton.wade.tests.config.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class CustomerControllerTest extends BaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void displayCreatorView() throws Exception {
        MockHttpSession session = makeAuthSession("Felicity Smoak", "ROLE_USER");
        mockMvc.perform(get("/customer/create_customer").session(session)).andExpect(view().name("views/customer/customer_creator"));
    }

    @Test
    public void createCustomer() throws Exception {
        MockHttpSession session = makeAuthSession("Oliver Queen", "ROLE_USER");
        mockMvc.perform(post("/customer/create")
                .session(session)
                .param("name", "dummyCustomer1")
                .param("email", "wkrix89@gmail.com")
                .param("country", "USA"));
        Assert.assertNotNull(customerRepository.findByName("dummyCustomer1"));
        Assert.assertNotNull(customerRepository.findByEmail("wkrix89@gmail.com"));
    }

    @Test
    public void createCustomerWithMissingName() throws Exception {
        MockHttpSession session = makeAuthSession("Laurel Lance", "ROLE_USER");
        mockMvc.perform(post("/customer/create")
                .session(session)
                .param("email", "wkrix89@gmail.com")
                .param("country", "PL"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void createCustomerWithInvalidEmail() throws Exception {
        MockHttpSession session = makeAuthSession("John Diggle", "ROLE_USER");
        mockMvc.perform(post("/customer/create")
                .session(session)
                .param("name", "dummyCustomer2")
                .param("email", "badEmailAsHell"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void modifyCustomer() throws Exception {
        final String name = "dummyCustomerForEdit";
        final String email = "wkrix89@gmail.com";
        final String country = "USA";
        MockHttpSession session = makeAuthSession("Thea Queen", "ROLE_USER");
        mockMvc.perform(post("/customer/create")
                .session(session)
                .param("name", name)
                .param("email", email)
                .param("country", country));

        Customer dummyCustomerForEdit = customerRepository.findByName(name);
        final int customerId = dummyCustomerForEdit.getId();
        ModelAndView modelAndView = mockMvc.perform(get("/customer/modify/" + customerId).session(session)).andReturn().getModelAndView();

        Assert.assertNotNull(modelAndView);
        Assert.assertEquals(modelAndView.getViewName(), "views/customer/customer_creator");
        Customer customer = (Customer) modelAndView.getModel().get("customer");

        Assert.assertEquals(customerId, customer.getId());
        Assert.assertEquals(name, customer.getName());
        Assert.assertEquals(email, customer.getEmail());
        Assert.assertEquals(country, customer.getCountry());
    }


    @Test
    public void listCustomers() throws Exception {
        MockHttpSession session = makeAuthSession("Roy Harper", "ROLE_USER");
        ModelAndView modelAndView = mockMvc.perform(get("/customer/list_customers").session(session)).andReturn().getModelAndView();
        List<Customer> customerList = (List<Customer>) modelAndView.getModel().get("customerList");
        List<Customer> expectedList = customerRepository.findAll();
        Assert.assertTrue(customerList.equals(expectedList));
    }

}
