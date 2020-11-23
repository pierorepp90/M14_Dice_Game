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
		return iDiceDao.save(new Dice(player.getId()));
	}
	@Override
	public Dice rollDicesById(String playerId) {
		return iDiceDao.save(new Dice(playerId));
	}


	@Override
	public List<Dice> findAllDices() {
		return iDiceDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		iDiceDao.deleteById(id);
		
	}

	@Override
	public List<Dice> findDicesByPlayerId(String playerId) throws Exception{
		List<Dice> dicesByPlayerId = iDiceDao.findAll().stream().filter(d->d.getPlayer().equals(playerId)).collect(Collectors.toList());
		return dicesByPlayerId;
	}


}
