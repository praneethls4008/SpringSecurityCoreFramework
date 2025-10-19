package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigType2 {

	@Bean()
	UserDetailsManager userDetailManagerBean() {
		InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
		System.out.println("[SecurityConfig] InMemoryUserDetailsManager instance: " + System.identityHashCode(mgr));
		return mgr;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean @Order(3)
	SecurityFilterChain globalChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/WEB-INF/views/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults()).build();
	}

	@Bean @Order(2)
	SecurityFilterChain teacherChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/teacher/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/teacher/login", "/teacher/register", "/teacher/newaccount", "/teacher/auth").permitAll()
						.requestMatchers("/teacher/**").authenticated()
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/teacher/login")
						.loginProcessingUrl("/teacher/auth")
						.defaultSuccessUrl("/teacher/dashboard", true)
						.failureUrl("/teacher/login?error=true")
						.usernameParameter("username")
						.passwordParameter("password")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/teacher/logout")
						.logoutSuccessUrl("/teacher/login?logout=true")
						.invalidateHttpSession(true)
				)
				.build();
	}

	@Bean @Order(1)
	SecurityFilterChain studentChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/student/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/student/debug", "/student/login", "/student/register", "/student/newaccount", "/student/auth").permitAll()
						.requestMatchers("/student/**").authenticated()
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/student/login")
						.loginProcessingUrl("/student/auth")
						.defaultSuccessUrl("/student/dashboard", true)
						.failureUrl("/student/login?error=true")
						.usernameParameter("username")
						.passwordParameter("password")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/student/logout")
						.logoutSuccessUrl("/student/login?logout=true")
						.invalidateHttpSession(true)
				)
				.build();
	}

}
