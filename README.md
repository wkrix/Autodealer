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


**[Note: No additional services are required in order to start the application. Although you can configure the SMTP server for sending e-mail messages in mail.properties as mail.host.]**

