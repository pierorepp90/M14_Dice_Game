package com.bcnit14.services;

import static com.bcnit14.security.DiceGameRoles.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcnit14.auth.User;
import com.bcnit14.dao.IPlayerDao;
import com.bcnit14.dto.Player;

@Service
public class PlayerServicesImpl implements /*UserDetailsService,*/IPlayerService {
	
//	private final PasswordEncoder passwordEncoder;
//	
//	@Autowired
//	public PlayerServicesImpl(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}
	
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

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		Player player;
//		try {
//			System.out.println("probando");
//			player = this.findByUserName(username).get(0);
//			
//		} catch (UsernameNotFoundException UserNotFound) {
//			throw new UsernameNotFoundException("Username not found");
//		}
//		Set<? extends GrantedAuthority> authorities = PLAYER.getGrantedAuthorities();
//		System.out.println("a ver");
//		return new User(player.getUserName(), passwordEncoder.encode(player.getPassword()), authorities);
//	}
}
