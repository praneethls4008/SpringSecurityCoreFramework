package org.springframeworkcore.mvc.javaannotationbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigType2 {

	@Bean
	UserDetailsManager userDetailManagerBean() {
		String salt = BCrypt.gensalt();
		UserDetails student1 = User.withUsername("student1")
			.password(BCrypt.hashpw("student1", salt))
			.authorities("STUDENT")
			.build();
		
		UserDetails teacher1 = User.withUsername("teacher1")
				.password(BCrypt.hashpw("teacher1", salt))
				.authorities("TEACHER")
				.build();
		
		return new InMemoryUserDetailsManager(student1, teacher1);
			
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
