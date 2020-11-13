package com.bcnit14.services;

import java.util.List;

import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

public interface IPlayerService {

	public Player createPlayer(Player player);

	public Player findPlayerById(Integer id);

	public List<Dice> showPlayerRolls(Integer id);

	public List<Player> findAllPlayers();
	
	public void sortBySuccesRate(List<Player> players);

	public void deletePlayer(Integer id);

}
