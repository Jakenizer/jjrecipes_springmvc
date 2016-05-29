package se.jjrecipes.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class GeneralUtil {
	
	public static boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
	    return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
	}
}
