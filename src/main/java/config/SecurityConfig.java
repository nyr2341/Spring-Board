package config;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import lombok.Setter;
import secutiry.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Setter(onMethod_= {@Autowired})
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
				.anyRequest().permitAll()
			.and()
				.formLogin()
				.loginPage("/user/customLogin")
				.loginProcessingUrl("/login")
			.and()
				.logout()
				.logoutUrl("/user/customLogout")
				.invalidateHttpSession(true)
				.deleteCookies("remember-me","JSESSIONID")
			.and()
				.rememberMe()
				.key("key")
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(604800);
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
        http.addFilterBefore(characterEncodingFilter,CsrfFilter.class);
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * String queryUser =
		 * "select userid, userpw, enabled from tbl_user where userid=?"; String
		 * queryDetails = "select userid, authority from tbl_authority where userid=?";
		 * 
		 * auth.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder()) .usersByUsernameQuery(queryUser)
		 * .authoritiesByUsernameQuery(queryDetails);
		 */
		
		auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
	}
	
	
	
}
