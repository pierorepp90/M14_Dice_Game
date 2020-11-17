package com.bcnit14.services;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnit14.dao.IPlayerDao;
import com.bcnit14.dto.Player;

@Service
public class PlayerServicesImpl implements IPlayerService {

	@Autowired
	IPlayerDao iPlayerDao;

	@Override
	public Player savePlayer(Player player) {

		if (player.getAccountTime() == null) {
			player.setAccountTime(new Date());
		}
		return iPlayerDao.save(player);
	}
	
	@Override
	public List<Player> saveAll(){
		return iPlayerDao.saveAll(iPlayerDao.findAll());
	}

	public List<Player> findByUserName(String userName) {
		return iPlayerDao.findByUserNameContains(userName);
	}

	@Override
	public Player findPlayerById(String id) {
		return iPlayerDao.findById(id).get();
	}

	@Override
	public List<Player> findAllPlayers() {
		return iPlayerDao.findAll();
	}

	@Override
	public void sortBySuccesRate(List<Player> players) {
		players.sort(Comparator.comparing(Player::getSuccesRate));
	}

	@Override
	public void deletePlayer(String id) {
		iPlayerDao.delete(iPlayerDao.findById(id).get());

	}

}
