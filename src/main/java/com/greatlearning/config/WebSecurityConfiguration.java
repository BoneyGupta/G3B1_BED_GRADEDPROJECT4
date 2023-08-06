package com.greatlearning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greatlearning.service.DomainUserDetailsService;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DomainUserDetailsService domainUserDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	// For authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.domainUserDetailsService).passwordEncoder(this.passwordEncoder);
	}

	// For authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().disable();
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests()
		.antMatchers("/h2-console/**", "/h2-console**").permitAll()	
		.antMatchers(HttpMethod.POST, "/api/employees/**").hasRole("ADMIN").anyRequest().fullyAuthenticated()	
		.and().formLogin().and().httpBasic();
	}

}
