package com.bcnit14.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dices")
public class Dice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "roll_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rollTime;
	@Column(name = "dice_roll_1")
	private Integer dice1;
	@Column(name = "dice_roll_2")
	private Integer dice2;
	@Column(name = "win")
	private boolean win;
	@ManyToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Player.class)
	@JoinColumn(name = "player_id")
	private Player player;

	public Dice() {
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

	public Integer getId() {
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

	@JsonIgnore
	public Player getPlayer() {
		return player;
	}

	public void setId(Integer id) {
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
		this.player = player;
	}

}
