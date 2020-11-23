package com.bcnit14.services;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcnit14.dao.IPlayerDao;
import com.bcnit14.dto.Player;

@Service
public class PlayerServicesImpl implements UserDetailsService,IPlayerService {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public PlayerServicesImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
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
	public List<Player> saveAll() {
		return iPlayerDao.saveAll(iPlayerDao.findAll());
	}

	public List<Player> findByUsername(String userName) {
		return iPlayerDao.findByUsernameContains(userName);
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
	public void deletePlayer(String id) throws Exception{
		iPlayerDao.delete(iPlayerDao.findById(id).get());

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Player player;
		try {
			player = findByUsername(username).get(0);
			System.out.println(player.getUsername() + " logged in.");
			
		} catch (UsernameNotFoundException UserNotFound) {
			throw new UsernameNotFoundException("Username not found");
		}
		return new User(player.getUsername(), passwordEncoder.encode(player.getPassword()), player.getRole().getGrantedAuthorities());
	}
}
