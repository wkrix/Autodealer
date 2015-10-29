package hu.klayton.wade.spring.controller;

import hu.klayton.wade.spring.entity.Customer;
import hu.klayton.wade.spring.entity.Vehicle;
import hu.klayton.wade.spring.repository.CustomerRepository;
import hu.klayton.wade.spring.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final String LOCATION = "views/customer/";
    private static final String LIST_CUSTOMER = LOCATION + "list_customer";
    private static final String CUSTOMER_CREATOR = LOCATION + "customer_creator";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @RequestMapping(value = "/list_customers", method = RequestMethod.GET)
    public String listCustomers(final Model model) {
        final List<Customer> customers = customerRepository.findAll();
        model.addAttribute(customers);
        return LIST_CUSTOMER;
    }

    @RequestMapping(value = "/create_customer", method = RequestMethod.GET)
    public String createCustomer(final Model model) {
        model.addAttribute(new Customer());
        return CUSTOMER_CREATOR;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createCustomer(@ModelAttribute @Valid final Customer customer, final Errors errors) {
        if (errors.hasErrors()) {
            return CUSTOMER_CREATOR;
        }
        customerRepository.save(customer);
        return "redirect:/customer/list_customers";
    }

    @RequestMapping(value = "/delete/{customerId}", method = RequestMethod.GET)
    public String deleteCustomer(@PathVariable("customerId") final int id) {
        List<Vehicle> vehicleList = vehicleRepository.findByCustomerId(id);
        for (Vehicle vehicle : vehicleList) {
            vehicle.setCustomer(null);
            vehicleRepository.save(vehicle);
        }
        customerRepository.delete(id);
        return "redirect:/customer/list_customers";
    }

    @RequestMapping(value = "/modify/{customerId}", method = RequestMethod.GET)
    public String modifyCustomer(@PathVariable("customerId") final int id, final Model model) {
        final Customer customer = customerRepository.findOne(id);
        model.addAttribute(customer);
        return CUSTOMER_CREATOR;
    }

}
