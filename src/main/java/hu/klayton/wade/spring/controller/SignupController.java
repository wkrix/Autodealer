package hu.klayton.wade.spring.controller;

import hu.klayton.wade.spring.dto.SellerDTO;
import hu.klayton.wade.spring.entity.Seller;
import hu.klayton.wade.spring.error.SignupException;
import hu.klayton.wade.spring.repository.SellerRepository;
import hu.klayton.wade.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Controller
public class SignupController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;

    private static final String SIGNUP_VIEW_NAME = "views/signup/signup";


    @RequestMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute(new SellerDTO());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SellerDTO sellerDTO, Errors errors) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        try {
            final Seller seller = sellerRepository.save(sellerDTO.createSeller());
            userService.signin(seller);
        } catch (JpaSystemException ex) {
            throw new SignupException("The username must be unique!");
        }
        return "redirect:/";
    }

}
