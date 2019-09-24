package com.fakecorp.invoicing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fakecorp.invoicing.app.service.UserService;
import com.fakecorp.invoicing.app.utils.auth.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private UserService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/", "/css/**", "/js/**", "/img/**", "/locale", "/error/**", "/api/**").permitAll()
		.antMatchers("/profile").hasAnyRole("USER")
		.antMatchers("/clients").hasAnyRole("USER")
		.antMatchers("/products").hasAnyRole("USER")
		.antMatchers("/invoices").hasAnyRole("USER")
		.antMatchers("/upload").hasAnyRole("ADMIN")
		.antMatchers("/**/new/**").hasAnyRole("ADMIN")
		.antMatchers("/**/edit/**").hasAnyRole("ADMIN")
		.antMatchers("/**/delete/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin().successHandler(successHandler).loginPage("/login").permitAll()
		.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.userDetailsService(userService)
		.passwordEncoder(passwordEncoder());
	}
}
