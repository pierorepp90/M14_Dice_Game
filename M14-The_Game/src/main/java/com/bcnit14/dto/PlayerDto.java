package com.bcnit14.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerDto {
	
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("account_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountTime;
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("password")
	private String password;
	@JsonProperty("succes_rate")
	private Double succesRate;
	private List<Dice> dices;

	public PlayerDto() {

	}

	public static PlayerDto playerToJson(Player player) {
		PlayerDto pDto = new PlayerDto();
		pDto.setId(player.getId());
		pDto.setAccountTime(player.getAccountTime());
		pDto.setUserName(player.getUserName());
		pDto.setPassword(player.getPassword());
		pDto.setSuccesRate(player.getSuccesRate());

		return pDto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getSuccesRate() {
		return succesRate;
	}
	@JsonIgnore
	public List<Dice> getDices() {
		return dices;
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

	public void setSuccesRate(Double succesRate) {
		this.succesRate = succesRate;
	}

	public void setDices(List<Dice> dices) {
		this.dices = dices;
	}

}
