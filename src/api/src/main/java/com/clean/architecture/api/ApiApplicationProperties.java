package com.clean.architecture.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ApiApplicationProperties {

	private static String environmentName;
	private static String corsDomain;
	
	public static String getEnvironmentName() {
		return environmentName;
	}
	public static void setEnvironmentName(String envName) {
		environmentName = envName;
	}
	public static String getCorsDomain() {
		return corsDomain;
	}
	public static void setCorsDomain(String corsDOMAIN) {
		corsDomain = corsDOMAIN;
	}
}
