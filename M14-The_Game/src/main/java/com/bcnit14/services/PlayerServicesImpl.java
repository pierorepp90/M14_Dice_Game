package com.bcnit14.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnit14.dao.IPlayerDao;
import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

import io.jsonwebtoken.lang.Arrays;

@Service
public class PlayerServicesImpl implements IPlayerService {

	@Autowired
	IPlayerDao iPlayerDao;

	@Override
	public Player createPlayer(Player player) {

		if (player.getAccountTime() == null) {
			player.setAccountTime(new Date());
		}
		return iPlayerDao.save(player);
	}

	public List<Player> findByUserName(String userName) {
		return iPlayerDao.findByUserNameContains(userName);
	}


	@Override
	public Player findPlayerById(Integer id) {

		return iPlayerDao.findById(id).get();
	}

	@Override
	public List<Dice> showPlayerRolls(Integer id) {

		return iPlayerDao.findById(id).get().getDices();
	}

	@Override
	public List<Player> findAllPlayers() {

		return iPlayerDao.findAll();
	}
	
	@Override
	public void sortBySuccesRate(List<Player> players) {
		players.sort(Comparator.comparing(Player::getSuccesRate));
//		 Comparator<Player> playerSuccesComparator
//	      = Comparator.comparingDouble(Player::getSuccesRate);
//	    
//		 iPlayerDao.findAll().sort(playerSuccesComparator);
	    
	}


	@Override
	public void deletePlayer(Integer id) {
		iPlayerDao.delete(iPlayerDao.findById(id).get());

	}


}
