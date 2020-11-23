package com.bcnit14.security;

//read, play permissions unused
public enum DiceGamePermission {
	PLAYER_READ("player:read"), PLAYER_ADMIN("player:admin"), PLAYER_PLAY("player:play");

	private final String permission;

	DiceGamePermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

}
