package com.bcnit14.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bcnit14.security.DiceGameRoles;

import static com.bcnit14.security.DiceGameRoles.*;

@Document(collection = "players")
public class Player {

	@Id
	private String id;
	private Date accountTime;
	private String username;
	private String password;
	private Double succesRate;
	private DiceGameRoles role = PLAYER;

	public Player() {
	}

	public Player(String id, String username, String password) {
		this.id = id;
		this.accountTime = new Date();
		this.username = username;
		this.password = password;

	}

	public String getId() {
		return id;
	}

	public Date getAccountTime() {
		return accountTime;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getSuccesRate() {
		if(succesRate == null) {
			succesRate = 0.0;
		}
		return this.succesRate;
	}

	public void setSuccesRate(List<Dice> dices) {
		if(dices == null || dices.isEmpty()) {
			this.succesRate = 0.0;
		}else {
			this.succesRate = (double) (dices.stream().filter(d -> d.isWin()).count())/dices.size()*100;
		}
	}

	public DiceGameRoles getRole() {
		return role;
	}

	public void setRole(DiceGameRoles role) {
		this.role = role;
	}
}
