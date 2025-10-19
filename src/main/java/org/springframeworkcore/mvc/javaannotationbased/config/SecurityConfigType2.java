package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigType2 {

	/**
	 * in memory user details manager
	 */
//	@Bean()
//	UserDetailsManager userDetailManagerBean() {
//		InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
//		System.out.println("[SecurityConfig] InMemoryUserDetailsManager instance: " + System.identityHashCode(mgr));
//		return mgr;
//	}


	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//		manager.setUsersByUsernameQuery(
//				"SELECT username, password, true as enabled FROM users WHERE username = ?");
//		manager.setAuthoritiesByUsernameQuery(
//				"SELECT username, 'ROLE_STUDENT' as authority FROM users WHERE username = ?");
		return manager;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/*
		only goes through /student chain other urls won't get in here or authenticated student won't work with other chain
	 */
	@Bean @Order(1)
	SecurityFilterChain studentChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/student/**")
				.authorizeHttpRequests( auth -> auth
						.requestMatchers("/student/debug",
								"/student/login",
								"/student/register",
								"/student/newaccount",
								"/student/auth"
						).permitAll()
						.requestMatchers("/student/**").hasRole("STUDENT")
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

	@Bean @Order(2)
	SecurityFilterChain teacherChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/teacher/**")
				.authorizeHttpRequests( auth -> auth
						.requestMatchers("/teacher/login",
								"/teacher/register",
								"/teacher/newaccount",
								"/teacher/auth"
						).permitAll()
						.requestMatchers("/teacher/**").hasRole("TEACHER")
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

	/*
		all req other than student teacher get here -> needs separate auth if needed, student (or) teacher auth doesn't work for this route
	 */
	@Bean @Order(3)
	SecurityFilterChain globalChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/WEB-INF/views/**").permitAll() //all files in views are permitted, they are treated as urls
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults()).build();
	}



}
