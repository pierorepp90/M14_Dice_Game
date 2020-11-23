package com.bcnit14.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiceDto {

	@JsonProperty("roll_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rollTime;
	@JsonProperty("dice_roll_1")
	private Integer dice1;
	@JsonProperty("dice_roll_2")
	private Integer dice2;
	@JsonProperty("win")
	private boolean win;
	@JsonProperty("player_id")
	private String player;

	public DiceDto() {

	}

	public static DiceDto diceToJson(Dice dice) {
		DiceDto dDto = new DiceDto();
		dDto.setRollTime(dice.getRollTime());
		dDto.setDice1(dice.getDice1());
		dDto.setDice2(dice.getDice2());
		dDto.setWin(dice.isWin());
		dDto.setPlayer(dice.getPlayer());

		return dDto;
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
		return player;
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

	public void setPlayer(String player) {
		this.player = player;
	}

}
