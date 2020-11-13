package com.bcnit14.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcnit14.dto.Player;

@Repository
@Transactional
public interface IPlayerDao extends JpaRepository<Player,Integer>{
	
	public List<Player> findByUserNameContains(String userName);

}
