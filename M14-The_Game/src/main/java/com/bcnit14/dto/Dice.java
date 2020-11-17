package com.bcnit14.dto;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dices")
public class Dice {

	@Id
	private String id;
	private Date rollTime;
	private Integer dice1;
	private Integer dice2;
	private boolean win;
	private String playerId;

	public Dice() {
	}

	public Dice(String playerId) {
		this.playerId = playerId;
		this.rollDice();
	}

	public void rollDice() {
		this.setDice1((int) Math.ceil(Math.random() * 6));
		this.setDice2((int) Math.ceil(Math.random() * 6));
		this.setRollTime(new Date());
		if (this.getDice1() + this.getDice2() == 7) {
			this.setWin(true);
		} else {
			this.setWin(false);
		}
	}

	public String getId() {
		return id;
	}

	public Date getRollTime() {
		return rollTime;
	}

	public Integer getDice1() {
		return dice1;
	}

	public Integer getDice2() {
		return dice2;
	}

	public boolean isWin() {
		return win;
	}

	public String getPlayer() {
		return playerId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRollTime(Date rollTime) {
		this.rollTime = rollTime;
	}

	public void setDice1(Integer dice1) {
		this.dice1 = dice1;
	}

	public void setDice2(Integer dice2) {
		this.dice2 = dice2;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public void setPlayer(Player player) {
		this.playerId = player.getId();
	}
	public void setPlayer(String playerId) {
		this.playerId = playerId;
	}

}
