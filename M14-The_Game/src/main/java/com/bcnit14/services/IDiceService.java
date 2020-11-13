package com.bcnit14.services;

import java.util.List;

import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

public interface IDiceService {

	public Dice rollDices(Player player);

	public List<Dice> findAllDices();

	public String avgRanking(List<Dice> dices);
	
	public void deleteById(Integer id);

}
