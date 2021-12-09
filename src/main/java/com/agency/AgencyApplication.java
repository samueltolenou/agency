package com.agency;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class AgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgencyApplication.class, args);
	}

	
	 @Bean
	    public CorsFilter corsFilter() {
		 
		 /*"http://localhost:4200","http://192.168.99.100:4200","http://10.4.153.21:4200"*/
		 
		 	String[] originList = {"*"};
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(false);
	        corsConfiguration.setAllowedOrigins(Arrays.asList(originList));
	        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
	                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
	                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
	        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
	                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
	        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
	        return new CorsFilter(urlBasedCorsConfigurationSource);
	    }


}
