package com.group31.scms.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.group31.scms.security.CustomAccessDeniedHandler;
import com.group31.scms.security.CustomAuthenticationFailureHandler;
import com.group31.scms.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication()
	        .withUser("user1").password(passwordEncoder().encode("password@1")).roles("USER")
	        .and()
	        .withUser("user2").password(passwordEncoder().encode("password@1")).roles("USER")
	        .and()
	        .withUser("admin").password(passwordEncoder().encode("password@1")).roles("ADMIN");
	   // @formatter:on
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
	        http.csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/admin/**").hasRole("ADMIN")
	                .antMatchers("/anonymous*").anonymous()
	                .antMatchers("/login*").permitAll()
	                .antMatchers(
	                        HttpMethod.GET,
	                        "/static/**","/*.js","/*.jsp","/*.css", "/*.jpg")
	                        .permitAll()
	                .antMatchers("/h2-console/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .formLogin()
	                .loginPage("/login.html")
	                .loginProcessingUrl("/perform_login")
	                .defaultSuccessUrl("/index.html", true)
	                .failureUrl("/loginInvalidCredential.html")
//	                .failureHandler(authenticationFailureHandler())
	                .and()
	                .logout()
	                .logoutUrl("/perform_logout")
	                .deleteCookies("JSESSIONID")
	                .logoutSuccessHandler(logoutSuccessHandler());
	        
	        http.headers().frameOptions().disable();
	        
	        //.and()
	        //.exceptionHandling().accessDeniedPage("/accessDenied");
	        //.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	        // @formatter:on
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
