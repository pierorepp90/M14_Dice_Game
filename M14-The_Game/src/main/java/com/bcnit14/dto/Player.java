package com.bcnit14.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "players")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountTime;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "succes_rate")
	private Double succesRate;

	@OneToMany(mappedBy = "player", orphanRemoval = true, targetEntity = Dice.class)
	private List<Dice> dices;

	public Player() {
	}

	public Integer getId() {
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

	@JsonIgnore
	public List<Dice> getDices() {
		return dices;
	}

	public void setId(Integer id) {
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

	public void setDices(List<Dice> dices) {
		this.dices = dices;
	}

	public double getSuccesRate() {
		if(this.succesRate == null) {
			this.setSuccesRate();
		}
		return this.succesRate;
	}

	public void setSuccesRate() {
		if(dices == null || dices.isEmpty()) {
			this.succesRate = 0.0;
		} else {
			List<Dice> winDices = this.dices.stream().filter(d -> d.isWin()).collect(Collectors.toList());
			this.succesRate = ((double) winDices.size() / this.dices.size()) * 100;
		}
	}
}
