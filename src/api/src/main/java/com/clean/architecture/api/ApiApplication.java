package com.clean.architecture.api;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = { "com.clean.architecture.api", "com.clean.architecture.infrastructure", "com.clean.architecture.core" })
@EntityScan(basePackages = { "com.clean.architecture.core.entities" })
@EnableJpaRepositories(basePackages = { "com.clean.architecture.infrastructure.repositories" })
@EnableSwagger2
public class ApiApplication {

	final static Logger logger = LogManager.getLogger(ApiApplication.class);

	@Value("${environmentName}")
	protected String environmentNameProperty;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@PostConstruct
	public void postConstruct() {
		logger.info(environmentLogMessage(environmentNameProperty));
	}

	protected String environmentLogMessage(String environmentName) throws IllegalArgumentException {
		String msg = "\n\n**** Starting ApiApplication Service in " + environmentName.toUpperCase().trim()
				+ " environment mode. (Using application-" + environmentName.toLowerCase().trim()
				+ ".properties file.) ****\n";
		return msg;
	}
}
