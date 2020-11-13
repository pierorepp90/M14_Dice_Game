package com.bcnit14.services;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnit14.dao.IDiceDao;
import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

@Service
public class DiceServicesImpl implements IDiceService {
	
	@Autowired
	IDiceDao iDiceDao;

	@Override
	public Dice rollDices(Player player) {
		Dice rolledDice = new Dice();
		rolledDice.rollDice();
		rolledDice.setPlayer(player);
		return iDiceDao.save(rolledDice);
	}

	@Override
	public List<Dice> findAllDices() {
		return iDiceDao.findAll();
	}

	@Override
	public String avgRanking(List<Dice> dicesList) {
		List<Dice> winnersDices = dicesList.stream().filter(d -> d.isWin()).collect(Collectors.toList());
		return Double.toString((double)(winnersDices.size())/(dicesList.size())*100);
	}

	@Override
	public void deleteById(Integer id) {
		iDiceDao.deleteById(id);
		
	}

}
