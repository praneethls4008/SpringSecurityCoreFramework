package org.springframeworkcore.mvc.javaannotationbased.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframeworkcore.mvc.javaannotationbased.jwt.JwtAccessDeniedHandler;
import org.springframeworkcore.mvc.javaannotationbased.jwt.JwtAuthenticationEntryPoint;
import org.springframeworkcore.mvc.javaannotationbased.jwt.JwtAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;



@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigType2 {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public SecurityConfigType2(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService, JwtAuthenticationEntryPoint jwtAuthEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}


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

	@Bean @Order(3)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/api/v1/public/**").permitAll()
						.requestMatchers("/actuator/health").permitAll()
						.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(jwtAuthEntryPoint)
						.accessDeniedHandler(jwtAccessDeniedHandler)
				)
				.headers(headers -> headers
						.contentSecurityPolicy(csp -> csp
								.policyDirectives("default-src 'self'; frame-ancestors 'none'")
						)
						.frameOptions(frameOptions -> frameOptions.deny())
						.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
						.contentTypeOptions(cto -> cto.disable())
				);

		return http.build();
	}

	@Bean @Order(4)
	SecurityFilterChain globalChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.securityMatcher("/**")
				.authorizeHttpRequests( authCustomizer -> authCustomizer
						.requestMatchers("/WEB-INF/views/**").permitAll() //all files in views are permitted, they are treated as urls
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults()).build();
	}


	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Configure properly
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



}
