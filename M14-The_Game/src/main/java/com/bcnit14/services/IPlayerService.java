package com.bcnit14.services;

import java.util.List;

import com.bcnit14.dto.Player;

public interface IPlayerService {

	public Player savePlayer(Player player);

	public Player findPlayerById(String id);

	public List<Player> findAllPlayers();

	public void sortBySuccesRate(List<Player> players);

	public void deletePlayer(String id);
	
	public List<Player> saveAll();

}
