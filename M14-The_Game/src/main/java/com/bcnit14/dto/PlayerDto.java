package com.bcnit14.dto;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerDto {

	@JsonProperty("id")
	private String id;
	@JsonProperty("account_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountTime;
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("password")
	private String password;
	@JsonProperty("succes_rate")
	private Double succesRate;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public static List<PlayerDto> sortBySuccesRate(List<PlayerDto> players) {
		players.sort(Comparator.comparing(PlayerDto::getSuccesRate));
		return players;
	}
}
