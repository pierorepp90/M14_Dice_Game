package com.bcnit14.security;

public enum DiceGamePermission {
	PLAYER_READ("player:read"), PLAYER_DELETE("player:delete"), PLAYER_PLAY("player:play");

	private final String permission;

	DiceGamePermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

}
