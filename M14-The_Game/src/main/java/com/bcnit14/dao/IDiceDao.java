package com.bcnit14.dao;

import javax.transaction.Transactional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bcnit14.dto.Dice;

@Repository
@Transactional
public interface IDiceDao extends MongoRepository<Dice, String> {

}
