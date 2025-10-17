package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableWebSecurity(debug = true)
public class SecurityConfigType1 {

	/**
	 * Without EnableWebSecurity following Exception is thrown: starting filter No bean named 'springSecurityFilterChain' available
	 * @EnableWebSecurity comes from spring-security-config package, which provides default springSecurityFilterChain bean
	 * We can add our customer filter chain here in this class
	 */
	
	@Bean
	UserDetailsManager userDetailsManagerConfig() {
		/**
		 * Exception Cannot pass a null GrantedAuthority collection
		 * You have entered a password with no PasswordEncoder. If that is your intent, it should be prefixed with `{noop}`.
		 * 
		 * UserDetails (Interface ) provided by Spring Security for user deatils and authorities manage
		 */
		
		
		
		UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		
		GrantedAuthority grantedAuthorityStudent = new SimpleGrantedAuthority("STUDENT");
		List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		grantedAuthoritiesList.add(grantedAuthorityStudent);
		
		UserDetails userDetails = new User("user1", "user1", grantedAuthoritiesList);
		
		userDetailsManager.createUser(userDetails);
		return userDetailsManager;
	}
	
	/**
	 * Should always use a password encoder
	 * If not -> 
	 * 			Exception: You have entered a password with no PasswordEncoder. If that is your intent, it should be prefixed with {noop}.
	 */
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
