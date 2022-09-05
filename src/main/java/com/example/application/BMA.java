package com.example.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import lombok.extern.java.Log;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme("testapp")
@PWA(name = "Testapp", shortName = "Testapp", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
@Log
public class BMA extends SpringBootServletInitializer implements AppShellConfigurator {

    /**
	 * Wird immer gebraucht.
	 */
	private static final long serialVersionUID = -5170116799200123108L;

	/**
	 * Starter.
	 * @param args String[] Cmd Argumente.
	 */
	public static void main(final String[] args) {
        SpringApplication.run(BMA.class, args);
    }
	
	@Bean
    public CommandLineRunner listVeryDynamicTitleClasses() {
        return args -> {
        	log.info(() -> "List listVeryDynamicTitleClasses for " + this.getClass().getPackageName());
        };
    }

}
