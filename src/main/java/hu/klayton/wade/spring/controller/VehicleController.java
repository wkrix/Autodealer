package hu.klayton.wade.spring.controller;

import hu.klayton.wade.spring.entity.Customer;
import hu.klayton.wade.spring.entity.Vehicle;
import hu.klayton.wade.spring.repository.CustomerRepository;
import hu.klayton.wade.spring.repository.VehicleRepository;
import hu.klayton.wade.spring.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Controller
@RequestMapping(value = "/vehicle")
public class VehicleController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    private static final String LOCATION = "views/vehicle/";
    private static final String LIST_VEHICLE = LOCATION + "list_vehicle";
    private static final String VEHICLE_CREATOR = LOCATION + "vehicle_creator";
    private static final String SELLER_PAGE = LOCATION + "seller_page";
    private static final String VEHICLE_RESULT = LOCATION + "vehicle_result :: vehicleResultList";

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;


    @PostConstruct
    public void initializeEntities() {
        vehicleRepository.save(new Vehicle("Ford", 4, "Red", Vehicle.Type.AUTO));
        vehicleRepository.save(new Vehicle("BMW", 4, "Blue", Vehicle.Type.AUTO));
        vehicleRepository.save(new Vehicle("KTM", 2, "Orange", Vehicle.Type.BIKE));
        vehicleRepository.save(new Vehicle("Hyundai", 4, "White", Vehicle.Type.AUTO));

        customerRepository.save(new Customer("Miron Costin", "Románia", "wkrix89@gmail.com"));
        customerRepository.save(new Customer("Peter Robinson", "Észak-Írország", "zoltan.szecsko@inbuss.hu"));
        customerRepository.save(new Customer("Kis Emőke", "Magyarország", "wkrix89@gmail.com"));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVehicle(@ModelAttribute @Valid final Vehicle vehicle, final Errors errors) {
        if (errors.hasErrors()) {
            return VEHICLE_CREATOR;
        }
        vehicleRepository.save(vehicle);
        return "redirect:/vehicle/list_vehicles";
    }

    @RequestMapping(value = "/create_vehicle", method = RequestMethod.GET)
    public String createVehicle(final Model model) {
        model.addAttribute(new Vehicle());
        return VEHICLE_CREATOR;
    }

    @ModelAttribute("allTypes")
    public List<Vehicle.Type> allTypes() {
        return Arrays.asList(Vehicle.Type.values());
    }

    @RequestMapping(value = "/list_vehicles", method = RequestMethod.GET)
    public ModelAndView vehicleList(final ModelAndView modelAndView) {
        modelAndView.addObject("vehiclesForSale", vehicleRepository.findByStatus(Vehicle.Status.FOR_SALE));
        modelAndView.setViewName(LIST_VEHICLE);
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{vehicleId}", method = RequestMethod.GET)
    public String deleteVehicle(@PathVariable("vehicleId") final int id) {
        vehicleRepository.delete(id);
        return "redirect:/vehicle/list_vehicles";
    }

    @RequestMapping(value = "/modify/{vehicleId}", method = RequestMethod.GET)
    public String modifyVehicle(@PathVariable("vehicleId") final int id, final Model model) {
        final Vehicle vehicle = vehicleRepository.findOne(id);
        model.addAttribute(vehicle);
        return VEHICLE_CREATOR;
    }

    @RequestMapping(value = "/sell/{vehicleId}", method = RequestMethod.GET)
    public String sellVehicleForm(@PathVariable("vehicleId") final int vehicleId, final Model model, final HttpServletRequest request) {
        final Customer customer = new Customer();
        model.addAttribute(customer);
        final List<Customer> customers = customerRepository.findAll();
        model.addAttribute(customers);
        return SELLER_PAGE;
    }

    @RequestMapping(value = "/sellit/{vehicleId}", method = RequestMethod.POST)
    public String sellVehicle(@PathVariable("vehicleId") final int vehicleId, @ModelAttribute @Valid final Customer customer, final Errors errors, final Model model) {
        if (errors.hasErrors()) {
            final List<Customer> customers = customerRepository.findAll();
            model.addAttribute(customers);
            return SELLER_PAGE;
        }
        final Customer savedCustomer = customerRepository.save(customer);
        final Vehicle vehicle = vehicleRepository.findOne(vehicleId);
        vehicle.setStatus(Vehicle.Status.SOLD);
        vehicle.setCustomer(savedCustomer);
        vehicleRepository.save(vehicle);
        return "redirect:/customer/list_customers";
    }

    @RequestMapping(value = "/sellit/{vehicleId}/{customerId}")
    public String sellVehicleToExistingUser(@PathVariable("vehicleId") final int vehicleId, @PathVariable("customerId") final int customerId) throws MessagingException {
        final Customer loadedCustomer = customerRepository.findOne(customerId);
        final Vehicle vehicle = vehicleRepository.findOne(vehicleId);
        vehicle.setStatus(Vehicle.Status.SOLD);
        vehicle.setCustomer(loadedCustomer);
        vehicleRepository.save(vehicle);
        emailService.sendSimpleMail(loadedCustomer, vehicle);
        logger.debug("The vehicle with this id {} is saved", vehicle.getId());
        return "redirect:/customer/list_customers";
    }

    @RequestMapping(value = "/list_vehicle_block/{customerId}", method = RequestMethod.GET)
    public String showVehicleList(final Model model, @PathVariable("customerId") final int customerId) {
        model.addAttribute("resultVehicles", vehicleRepository.findByCustomerId(customerId));
        return VEHICLE_RESULT;
    }
}
