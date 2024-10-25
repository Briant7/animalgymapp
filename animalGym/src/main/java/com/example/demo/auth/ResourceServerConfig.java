package com.example.demo.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/animalgym/**").permitAll()
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/animalgym/clientes", "/animalgym/clientes/page/**", "/animalgym/uploads/img/**",
				"/images/**", "/animalgym/clientes/periodos", "/animalgym/clientes/qr/{id}", "/animalgym/clientes/numero/control/{numcontrol}",
				"/animalgym/clientes/numero/control/**", "/animalgym/clientes/qr/**", "/animalgym/clientes/registros/**",
				"/animalgym/clientes/**", "/animalgym/clientes/enviar/**", "/animalgym/clientes/enviar/{id}", 
				"/animalgym/clientes/vencidos/page/**", "/animalgym/clientes/activos/page/**", "/animalgym/clientes/{id}", "/animalgym/productos/{id}",
				"/animalgym/operaciones/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/clientes/{id}").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/animalgym/upload").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/animalgym/clientes").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/clientes/datos").permitAll()
		.antMatchers(HttpMethod.POST, "/animalgym/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/usuarios/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/usuarios/roles").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/usuarios/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/clientes/enviar/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/clientes/enviar/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/usuarios/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/ventas/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/ventas/filtrar-productos/{nomb}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/animalgym/usuarios/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/historial/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/productos/page/**").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/username/{username}").permitAll()
		.antMatchers(HttpMethod.POST, "/animalgym/productos").permitAll()
		.antMatchers(HttpMethod.PUT, "/animalgym/productos/{id}").permitAll()
		.antMatchers(HttpMethod.DELETE, "/animalgym/productos/**").permitAll()
		.antMatchers(HttpMethod.POST, "/animalgym/ventas").permitAll()
		.antMatchers(HttpMethod.PUT, "/animalgym/clientes").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.DELETE, "/animalgym/clientes").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/animalgym/recupera/contraseña").permitAll()
		.antMatchers(HttpMethod.POST, "/animalgym/actualizar/contraseña").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/enviar/qr/{id}").permitAll()
		.antMatchers(HttpMethod.GET, "/animalgym/registra/operacion/{username}").permitAll()
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/clientes", "/leones-gym-back/animalgym/clientes/page/**", 
//				"/leones-gym-back/animalgym/uploads/img/**",
//				"/leones-gym-back/images/**", "/leones-gym-back/animalgym/clientes/periodos", "/leones-gym-back/animalgym/clientes/qr/{id}", 
//				"/leones-gym-back/animalgym/clientes/numero/control/{numcontrol}",
//				"/leones-gym-back/animalgym/clientes/numero/control/**", "/leones-gym-back/animalgym/clientes/qr/**", "/leones-gym-back/animalgym/clientes/registros/**",
//				"/leones-gym-back/animalgym/clientes/**", "/leones-gym-back/animalgym/clientes/enviar/**", "/leones-gym-back/animalgym/clientes/enviar/{id}", 
//				"/leones-gym-back/animalgym/clientes/vencidos/page/**", "/leones-gym-back/animalgym/clientes/activos/page/**", "/leones-gym-back/animalgym/clientes/{id}", 
//				"/leones-gym-back/animalgym/productos/{id}",
//				"/leones-gym-back/animalgym/operaciones/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/clientes/{id}").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/animalgym/upload").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/animalgym/clientes").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/clientes/datos").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/animalgym/usuarios").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/usuarios/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/usuarios/roles").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/usuarios/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/clientes/enviar/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/clientes/enviar/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/usuarios/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/ventas/{id}").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/ventas/filtrar-productos/{nomb}").permitAll()
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/animalgym/usuarios/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/historial/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/productos/page/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/leones-gym-back/animalgym/username/{username}").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/animalgym/productos").permitAll()
//		.antMatchers(HttpMethod.PUT, "/leones-gym-back/animalgym/productos/{id}").permitAll()
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/animalgym/productos/**").permitAll()
//		.antMatchers(HttpMethod.POST, "/leones-gym-back/animalgym/ventas").permitAll()
//		.antMatchers(HttpMethod.PUT, "/leones-gym-back/animalgym/clientes").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.DELETE, "/leones-gym-back/animalgym/clientes").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://animalgym.web.app", "http://localhost:8090"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
