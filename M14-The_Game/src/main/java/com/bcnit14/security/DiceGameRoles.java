package com.bcnit14.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.bcnit14.security.DiceGamePermission.*;
import com.google.common.collect.Sets;

public enum DiceGameRoles {

	AUTHOR(Sets.newHashSet(PLAYER_READ, PLAYER_ADMIN, PLAYER_PLAY)),
	PLAYER(Sets.newHashSet(PLAYER_PLAY)),
	ADMIN(Sets.newHashSet(PLAYER_READ, PLAYER_ADMIN));

	private final Set<DiceGamePermission> permissions;

	DiceGameRoles(Set<DiceGamePermission> permissions) {
		this.permissions = permissions;
	}

	public Set<DiceGamePermission> getPermissions() {
		return permissions;
	}
	
	 public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
	        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
	                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
	                .collect(Collectors.toSet());
	        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
	        return permissions;
    }

}
