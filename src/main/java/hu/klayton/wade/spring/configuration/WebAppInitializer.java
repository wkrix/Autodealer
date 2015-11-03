package hu.klayton.wade.spring.configuration;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebAppConfiguration.class, SecurityConfig.class, JpaConfig.class, MailConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebAppConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    /**
     * HttpSessionEventPublisher listenert letrehozzuk, mivel kelleni fog a belepeshez, ha egyszerre csak 1 user lehet 1 tokennel 1 sessionben
     */
    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        HttpSessionEventPublisher httpSessionEventPublisher = new HttpSessionEventPublisher();
        servletContext.addListener(httpSessionEventPublisher);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter)
                .addMappingForUrlPatterns(null, false, "/*");

        super.registerContextLoaderListener(servletContext);
    }




}
