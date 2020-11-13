package com.bcnit14.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcnit14.dto.Dice;

@Repository
@Transactional
public interface IDiceDao extends JpaRepository<Dice,Integer> {

}
