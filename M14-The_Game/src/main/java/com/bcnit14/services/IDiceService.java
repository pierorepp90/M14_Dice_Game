package com.bcnit14.services;

import java.util.List;

import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

public interface IDiceService {

	public Dice rollDices(Player player);
	
	public Dice rollDicesById(String playerId);

	public List<Dice> findAllDices();
	
	public void deleteById(String id);
	
	public List<Dice> findDicesByPlayerId(String playerId) throws Exception;

}
