package com.gl.ems2.security;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.gl.ems2.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.authenticationProvider(authenticationProvider());
//		builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//				.password("admin").roles("ADMIN").and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//
//	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui/index.html", "/webjars/**","/h2-console");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(requests -> requests
				.antMatchers(HttpMethod.DELETE, "/api/employee/{Id}", "/api/user/{ID}").hasAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/employee/{Id}", "/api/user/{Id}").hasAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/api/employee", "/api/user").hasAuthority("ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/api/user").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers(HttpMethod.GET, "/api/employee/", "/api/user/", "/api/role/", "/employee/sort",
						"/employee/search/{FirstName}", "/api/employee/{Id}")
				.hasAnyRole("ADMIN", "USER").anyRequest().authenticated())
				.exceptionHandling(handling -> handling.accessDeniedHandler((request, response, e) -> {
					response.setContentType("application/json;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.getWriter().write(new JSONObject().put("timestamp", LocalDateTime.now())
							.put("message", "Access denied Login with Admin Role").toString());
				}));
		http
	    .httpBasic()
	    .and()
	    .cors()
	    .and()
	    .csrf().disable();


//		http.authorizeHttpRequests(requests -> requests
//			    .antMatchers(HttpMethod.DELETE, "/api/employee/{Id}", "/api/user/{ID}").hasRole("ADMIN")
//			    .antMatchers(HttpMethod.PUT, "/api/employee/{Id}", "/api/user/{Id}").hasRole("ADMIN")
//			    .antMatchers(HttpMethod.POST, "/api/employee", "/api/user").hasRole("ADMIN")
//			    .antMatchers(HttpMethod.POST, "/api/user").hasAnyRole("ADMIN", "USER")
//			    .antMatchers(HttpMethod.GET, "/api/employee/", "/api/user/", "/api/role/", "/employee/sort",
//			        "/employee/search/{FirstName}", "/api/employee/{Id}").hasAnyRole("ADMIN", "USER")
//			    .anyRequest().authenticated());


	}


}

