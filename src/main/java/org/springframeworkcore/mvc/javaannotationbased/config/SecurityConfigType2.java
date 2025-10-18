package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigType2 {

	@Bean
	UserDetailsManager userDetailManagerBean() {
		return new InMemoryUserDetailsManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}



	@Bean @Order(3)
	SecurityFilterChain globalChain(HttpSecurity httpSecurity) throws Exception {
		HttpSecurity h = httpSecurity
				.securityMatcher("/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/WEB-INF/views/**").permitAll()
				)
				.formLogin(Customizer.withDefaults());
		return h.build();
	}

	@Bean @Order(2)
	SecurityFilterChain teacherChain(HttpSecurity httpSecurity) throws Exception {
		HttpSecurity h = httpSecurity
				.securityMatcher("/teacher/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/teacher/login", "/teacher/register").permitAll()
						.requestMatchers("/teacher/**").authenticated()
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults());
				return h.build();
	}

	@Bean @Order(1)
	SecurityFilterChain studentChain(HttpSecurity httpSecurity) throws Exception {
		HttpSecurity h = httpSecurity
				.securityMatcher("/student/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/student/login", "/student/register").permitAll()
						.requestMatchers("/student/**").authenticated()
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults());
				return h.build();
	}

}
