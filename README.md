Autodealer
=========================================

Summary
-------
Web application for Vehicle Sales. It's a simple application to demonstrate how to configure Spring MVC. Using Thymeleaf, Ajax, Spring Security, etc.

Generated project characteristics
-------------------------
* No-xml Spring MVC 4 web application for Servlet 3.0 environment
* Thymeleaf, Bootstrap, Ajax
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


Note: No additional services are required in order to start the application. Although you can configure the SMTP server for sending e-mail messages in mail.properties as mail.host.
