package practica.tecnologias.web.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import practica.tecnologias.web.app.models.dao.IUsuarioDao;
import practica.tecnologias.web.app.models.entity.Role;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Clase de servicio para el logueo de usuarios.
 * 
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	/** The usuario dao. */
	@Autowired
	private IUsuarioDao usuarioDao;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findByUsername(username);

		if (usuario == null) {

			throw new UsernameNotFoundException("Username " + username + " no registrado en el sistema!");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority> ();

		for (Role role: usuario.getRoles()) {

			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {

			throw new UsernameNotFoundException("El usuario '" + username + "' no tiene roles asignados!");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
			authorities);
	}

}
