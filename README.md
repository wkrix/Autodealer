Autodealer
=========================================

Summary
-------
Web application for Vehicle Sales. It's a simple application to demonstrate how to configure Spring MVC. Using Thymeleaf, Ajax, Spring Security, etc.

Include
-------------------------
* No-xml Spring MVC 4 web application for Servlet 3.0 environment
* Thymeleaf, Bootstrap
* JPA 2.0 (Hibernate/HSQLDB/Spring Data JPA)
* JUnit/Mockito
* Spring Security 4.0

Installation
------------

To install the archetype in your local repository execute following commands:

```bash
    git clone https://github.com/wkrix/Autodealer.git
    cd Autodealer
    mvn clean install
```

Run the project
----------------

```bash
	mvn test tomcat7:run
```

Test on the browser
-------------------

	http://localhost:8080/
	
Sign in
-------

    username: dummySeller
    password: password
    
Features
--------
* Sending AJAX request with Thymeleaf (I'm using jQuery, I didn't want to use Webflow or Tiles frameworks)
    
    __list_customer.html__
    
    ```html
            <td>
                <button id="list-vehicles" th:attr="data-vehicle_id=${c.id}" type="button" class="btn btn-info">Info
                </button>
            </td>
            <td>
                <div th:id="resultsBlock+ ${c.id}"></div>
            </td>
    ```
    ```js
     $(document).ready(function () {
            $("body").on("click", "#list-vehicles", function (e) {
                var id = $(this).attr("data-vehicle_id");
                var url = '/vehicle/list_vehicle_block/';
                url = url + id;
                $("#resultsBlock" + id).load(url);
            });
        });
    ```
  
    __VehicleController.java__

    ```java
    private static final String VEHICLE_RESULT = LOCATION + "vehicle_result :: vehicleResultList";

    @RequestMapping(value = "/list_vehicle_block/{customerId}", method = RequestMethod.GET)
    public String showVehicleList(final Model model, @PathVariable("customerId") final int customerId) {
        model.addAttribute("resultVehicles", vehicleRepository.findByCustomerId(customerId));
        return VEHICLE_RESULT;
    }
    ```


* Prevent Brute Force authentication attempts with Spring Security

    __AuthenticationFailureListener.java__

    ```java
    @Component
    public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    
        @Autowired
        private LoginAttemptService loginAttemptService;
    
        @Override
        public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
            WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
            loginAttemptService.loginFailed(auth.getRemoteAddress());
        }
    }
    ```
    
    __AuthenticationSuccessEventListener.java__

    ```java
    @Component
    public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    
        @Autowired
        private LoginAttemptService loginAttemptService;
    
        @Override
        public void onApplicationEvent(AuthenticationSuccessEvent e) {
            WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
            loginAttemptService.loginSucceeded(auth.getRemoteAddress());
        }
    
    }
    ```
    
     __SigninController.java__
    
      ```java
         @RequestMapping(value = "signin")
                public String signin() {
                    final String ip = request.getRemoteAddr();
                    if (loginAttemptService.isBlocked(ip)) {
                        throw new LoginException("Bad login. IP BAN.");
                    }
                    return LOCATION;
                }
      ```
   
   
* Change the user interface language by clicking the country flags

    __lang-change.js__
    
    ```js
         $(document).ready(function () {
             $("#en_flag").on("click", function () {
                 updateParam("lang", "en");
             });
             $("#hu_flag").on("click", function () {
                 updateParam("lang", "hu");
             });
         });
         
         function updateParam(key, value) {
             var q = String(location.search);
         
             if (q.length > 0) {
                 if (q.indexOf(key) > -1) {
                     var regExpPattern = key + "=([a-zA-Z]+)?";
                     var regExp = new RegExp(regExpPattern);
         
                     q = q.replace(regExp, key + "=" + value);
                 } else {
                     q += "\u0026" + key + "=" + value;
                 }
             } else {
                 q += "?" + key + "=" + value;
             }
         
             location.search = q;
     }
    ```
    
    __WebAppConfiguration.java__
    
    ```java
        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver slr = new SessionLocaleResolver();
            return slr;
        }
    
        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
            lci.setParamName("lang");
            return lci;
        }
    
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }
    ```
* Sending e-mail messages with Spring by configuring JavaMail API with JavaMailSenderImpl

    __MailConfiguration.java__
    
    ```java
        @Configuration
        @PropertySource("classpath:mail.properties")
        public class MailConfiguration {
        
            @Value("${mail.protocol}")
            private String protocol;
            @Value("${mail.host}")
            private String host;
            @Value("${mail.port}")
            private int port;
            @Value("${mail.smtp.auth}")
            private boolean auth;
            @Value("${mail.smtp.starttls.enable}")
            private boolean starttls;
        
            @Bean
            public JavaMailSender javaMailSender() {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                Properties mailProperties = new Properties();
                mailProperties.put("mail.smtp.auth", auth);
                mailProperties.put("mail.smtp.starttls.enable", starttls);
                mailSender.setJavaMailProperties(mailProperties);
                mailSender.setHost(host);
                mailSender.setPort(port);
                mailSender.setProtocol(protocol);
                return mailSender;
            }
    ```
    
    __EmailService.java__
        
    ```java
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
    ```
    
 Get Inspired By These articles: 
 - http://xpadro.blogspot.com.es/2014/02/thymeleaf-integration-with-spring-part-1.html
 - http://blog.codeleak.pl/2013/11/is-it-worth-upgrading-to-thymeleaf-21.html


**[Note1: No additional services are required in order to start the application. Although you can configure the SMTP server for sending e-mail messages in mail.properties as mail.host.]**

**[Note2: You can ignore SchemaExport errors. Combination of create-drop and in-memory database produces these for every database object it tries to drop. Reason being that there is not any database objects to remove - DROP statements are executed against empty database.
Also with normal permanent database such an errors do come, because Hibernate does not figure out before executing DROP statements does added object exist in database or is it new.]**

