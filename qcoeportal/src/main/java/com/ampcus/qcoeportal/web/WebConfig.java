package com.ampcus.qcoeportal.web;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.ampcus.qcoeportal.core.dao,"
		+ "com.ampcus.qcoeportal.core.entity, "
		+ "com.ampcus.qcoeportal.usecase, "
		+ "com.ampcus.qcoeportal.web, "
		+ "com.ampcus.qcoeportal.web.controller")
@EnableWebMvc //mvc:annotation-driven
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	
	@Autowired
	private Environment env;

	private HashMap<String, String> appConfigMap = new HashMap();
	@Bean("appConfigMap")
	public HashMap getUiPropertiesMap() {
		appConfigMap.put("CLIENT_ID",env.getProperty("CLIENT_ID"));
		appConfigMap.put("CLIENT_NAME",env.getProperty("CLIENT_NAME"));

		appConfigMap.put("PROJECT_ID",env.getProperty("PROJECT_ID"));
		appConfigMap.put("PROJECt_NAME",env.getProperty("PROJECt_NAME"));
		appConfigMap.put("PROJECT_START_DATE",env.getProperty("PROJECT_START_DATE"));
		appConfigMap.put("PROJECT_END_DATE",env.getProperty("PROJECT_END_DATE"));
		
		appConfigMap.put("RELEASE_ID",env.getProperty("RELEASE_ID"));
		appConfigMap.put("RELEASE_NUMBER",env.getProperty("RELEASE_NUMBER"));
		appConfigMap.put("RELEASE_TITLE",env.getProperty("RELEASE_TITLE"));
		
		appConfigMap.put("SRC_DOC_ID",env.getProperty("SRC_DOC_ID"));
		appConfigMap.put("SRC_DOC_TITLE",env.getProperty("SRC_DOC_TITLE"));
		appConfigMap.put("SRC_DOC_TYPE_ID",env.getProperty("SRC_DOC_TYPE_ID"));
		appConfigMap.put("SRC_DOC_TYPE_NAME",env.getProperty("SRC_DOC_TYPE_NAME"));

		appConfigMap.put("SRC_DOC_VER_ID",env.getProperty("SRC_DOC_VER_ID"));
		appConfigMap.put("SRC_DOC_VER_NUM",env.getProperty("SRC_DOC_VER_NUM"));
		appConfigMap.put("SRC_DOC_VER_FILENAME",env.getProperty("SRC_DOC_VER_FILENAME"));
		
		return appConfigMap;
	}
	
	
	
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
    	configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
	
	@Bean(name="viewResolver")
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/resources/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		return resolver;
	}
	
	
    @Bean(name="messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        String[] strBaseNames = {
                "resources/i18/labels",
                "resources/i18/validations",
                "resources/config"
        };
        
        messageSource.setBasenames(strBaseNames);
        return messageSource;
    }
    
    @Bean(name="localeResolver")
    public LocaleResolver localeResolver()
    {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en"));
		resolver.setCookieName("myLocaleCookie");
		resolver.setCookieMaxAge(4800);
		return resolver;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) 
    {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("mylocale");
		registry.addInterceptor(interceptor);
    }
}
	
