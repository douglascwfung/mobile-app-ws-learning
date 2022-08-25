package net.icestone.mobileapp.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry
		.addMapping("/users/email-verification")
		.allowedMethods("Get","POST","PUT")
		.allowedOrigins("*")
//		.addMapping("/**")
//		.allowedMethods("*")
//		.allowedOrigins("*")
		;

	}
	
}
