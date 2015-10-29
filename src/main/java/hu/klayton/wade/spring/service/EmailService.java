package hu.klayton.wade.spring.service;

import hu.klayton.wade.spring.entity.Customer;
import hu.klayton.wade.spring.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Locale;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    public void sendSimpleMail(final Customer loadedCustomer, final Vehicle vehicle) throws MessagingException {

        // Prepare the evaluation context
        final Locale locale = RequestContextUtils.getLocale(request);
        final WebContext ctx = new WebContext(request, response, request.getServletContext(), locale);
        ctx.setVariable("name", loadedCustomer.getName());
        ctx.setVariable("sellDate", new Date());
        ctx.setVariable("vehicleName", vehicle.getVehicleName());
        ctx.setVariable("vehicleColor", vehicle.getColor());
        ctx.setVariable("vehicleType", vehicle.getType());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        String subject = messageSource.getMessage("email.subject", null, locale);
        message.setSubject(subject);
        message.setFrom(environment.getProperty("mail.from"));
        message.setTo(loadedCustomer.getEmail());

        // Create the HTML body using Thymeleaf
        final String htmlContent = templateEngine.process("mails/email_simple", ctx);
        message.setText(htmlContent, true);

        String host = environment.getProperty("mail.host");
        if (!environment.getProperty("mail.host").equals("")) {
            // Send email
            this.mailSender.send(mimeMessage);
        }
    }
}
