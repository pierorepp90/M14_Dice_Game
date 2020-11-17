package com.bcnit14.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
public class Player {

	@Id
	private String id;
	private Date accountTime;
	private String userName;
	private String password;
	private Double succesRate;

	public Player() {
	}

	public Player(String id, String userName, String password) {
		this.id = id;
		this.accountTime = new Date();
		this.userName = userName;
		this.password = password;

	}

	public String getId() {
		return id;
	}

	public Date getAccountTime() {
		return accountTime;
	}

	public String getUserName() {
		return userName;
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

	public void setUserName(String userName) {
		this.userName = userName;
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
}
