package practica.tecnologias.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import practica.tecnologias.web.app.auth.handler.LoginSuccessHandler;
import practica.tecnologias.web.app.models.service.JpaUserDetailsService;

/**
 * Clase de configuración encargada de la seguridad y de la administración de credenciales de los usuarios de la aplicación.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	protected AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	/** The success handler. */
	@Autowired
	private LoginSuccessHandler successHandler;

	/** The user details service. */
	@Autowired
	private JpaUserDetailsService userDetailsService;

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/inicio", "/eventos/repositorioArchivos/**",
				"/css/**", "/js/**", "/locale", "/images/**", "/webjars/**", "/faqs", "/usuarios/registroUsuario", "/eventos/reunion/**", "/documentos/**")
			.permitAll()

			.antMatchers("/eventos/anteSalaReunion-comprobarAsistente").anonymous()
			.antMatchers("/eventos/anteSalaReunion/**").anonymous()
			.antMatchers("/eventos/reunionNoRegistrado/**").anonymous()
			.antMatchers("/eventos/registroEvento/**").hasAnyRole("JEFE", "ADMIN")
			.antMatchers("/eventos/verEvento/**").hasAnyRole("USER", "JEFE", "ADMIN")
			.antMatchers("/usuarios/listadoUsuarios").hasAnyRole("ADMIN", "JEFE")
			.antMatchers("/salas/listadoSalas").hasAnyRole("ADMIN", "JEFE")
			.antMatchers("/salas/verSala/**").hasAnyRole("ADMIN", "JEFE")
			.antMatchers("/salas/registroSala/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
			.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error_403");

		http.csrf().disable(); //comentando esta línea se puede acceder a la consola de H2.
		http.headers().frameOptions().disable();
	}

	/**
	 * Configurer global.
	 *
	 * @param build the build
	 * @throws Exception the exception
	 */
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		//SPRING SECURITY CON JPA

		build.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}

}
