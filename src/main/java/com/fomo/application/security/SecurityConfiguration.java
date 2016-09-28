package com.fomo.application.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	public static final String REMEMBER_ME_KEY = "rememberme_key";
	
	
	@Autowired
	DataSource dataSource;
	@Autowired
	private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
	@Autowired
	private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private RememberMeServices rememberMeServices;
	
	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select Email,Password,'true' from USER where Email=?")
				.authoritiesByUsernameQuery("select Email, Role from USER_ROLES,USER where Email=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests()
				.antMatchers("/index.html", "/views/**", "/", "/js/**", "/components/**")
					.permitAll()
				.anyRequest()
					.authenticated()
					.and()
				.exceptionHandling()
					.authenticationEntryPoint(restAuthenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler)
					.and()
				.formLogin()
					.loginProcessingUrl("/authenticate")
					.successHandler(authenticationSuccessHandler)
					.failureHandler(authenticationFailureHandler)
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
					.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
					.deleteCookies("JSESSIONID")
					.permitAll()
					.and()
				.rememberMe()
					.rememberMeServices(rememberMeServices)
					.key(REMEMBER_ME_KEY)
					.and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf()
				.csrfTokenRepository(csrfTokenRepository());
		;
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
