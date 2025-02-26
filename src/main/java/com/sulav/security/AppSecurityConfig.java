package com.sulav.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
//	@Autowired
//    private PasswordEncoder pwdEncoder; 
	
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((req) -> {req
			.requestMatchers("/user/login", "/user/register", "/user/profile/**", "/product/view/**").permitAll()
			.requestMatchers("/user/admin/**", "/product/admin/**", "/cart/admin/**").hasRole("ADMIN")
			.requestMatchers("/product/seller/**").hasAnyRole("ADMIN", "SELLER")
			.requestMatchers("/cart/user/**", "/order/**", "/payment/**").hasAnyRole("ADMIN", "SELLER", "CUSTOMER") // letting users check cart info if they know the user id
			.anyRequest().authenticated();
		}).httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
		


		return http.build();
	}
	
//	@Bean
//	public InMemoryUserDetailsManager inMemoryUsers() {
//		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin@123").roles("ADMIN").build();
//		UserDetails seller = User.withDefaultPasswordEncoder().username("seller").password("seller@123").roles("SELLER").build();
//		UserDetails customer = User.withDefaultPasswordEncoder().username("customer").password("customer@123").roles("CUSTOMER").build();
////
//		
//
//		return new InMemoryUserDetailsManager(admin, seller, customer);
//	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUsers(PasswordEncoder pwdEncoder) {
	    UserDetails admin = User.builder()
	            .username("admin")
	            .password(pwdEncoder.encode("admin@123"))
	            .roles("ADMIN")
	            .build();
	    
	    UserDetails seller = User.builder()
	            .username("seller")
	            .password(pwdEncoder.encode("seller@123"))
	            .roles("SELLER")
	            .build();
	    
	    UserDetails customer = User.builder()
	            .username("customer")
	            .password(pwdEncoder.encode("customer@123"))
	            .roles("CUSTOMER")
	            .build();

	    return new InMemoryUserDetailsManager(admin, seller, customer);
	}

}
