package com.bcnit14.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.bcnit14.security.DiceGameRoles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 

public class DiceGameSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public DiceGameSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()//	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
//			 Other way to handle ROLES and Auth in config:
			.antMatchers(HttpMethod.POST, "/api/players").permitAll()  //Allows everyone to create a player
//			.antMatchers(HttpMethod.DELETE, "/api/players/**","/api/players/*")   Allows certain methods by permissions   
//			.hasAuthority(DiceGamePermission.PLAYER_DELETE.getPermission())   J
//			.antMatchers(HttpMethod.POST, "/api/players/{id}/games")
//			.hasAuthority(DiceGamePermission.PLAYER_PLAY.getPermission())
//			.antMatchers("/api/players/*").hasAuthority(DiceGamePermission.PLAYER_READ.getPermission())
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();//   basic authentication(can't logout)
//			.formLogin();	form based authentication(JSESSIONID)
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails piero = User.builder()
			.username("Piero")
			.password(passwordEncoder.encode("notencoded"))
//			.roles(DiceGameRoles.AUTHOR.name())  Rol setter(non static import)
			.authorities(AUTHOR.getGrantedAuthorities())
			.build();
		
		UserDetails jonatan = User.builder()
			.username("Jonatan")
			.password(passwordEncoder.encode("notallowedtoplay"))
//			.roles(DiceGameRoles.ADMIN.name())
			.authorities(ADMIN.getGrantedAuthorities())
			.build();
		
		UserDetails testPlayer = User.builder()
				.username("TestPlayer")
				.password(passwordEncoder.encode("cantdelete"))
//				.roles(DiceGameRoles.PLAYER.name())
				.authorities(PLAYER.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(piero,jonatan,testPlayer);
	}
	

}
