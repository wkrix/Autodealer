package hu.klayton.wade.spring.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@ControllerAdvice
public class AppExceptionHandler {

    private static final String LOCATION = "views/error/";


    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(final Exception e) {
        final ModelAndView model = new ModelAndView(LOCATION + "error");
        model.addObject("errorMsg", e.getMessage());

        return model;
    }

    @ExceptionHandler(LoginException.class)
    public ModelAndView loginExceptionHandler(final Exception e, final HttpServletRequest request) {
        final ModelAndView model = new ModelAndView(LOCATION + "error");
        model.addObject("errorMsg", e.getMessage());
        model.addObject("ip", request.getRemoteAddr());

        return model;
    }

    @ExceptionHandler(SignupException.class)
    public ModelAndView signupExceptionHandler() {
        final ModelAndView modelAndView = new ModelAndView("redirect:/signup");
        modelAndView.addObject("error", 1);
        return modelAndView;
    }

}
