package com.bcnit14;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bcnit14.dao.IDiceDao;
import com.bcnit14.dao.IPlayerDao;
import com.bcnit14.dto.Dice;
import com.bcnit14.dto.Player;

@Component
public class DbSeeder implements CommandLineRunner {
	private IPlayerDao iPlayerDao;
	private IDiceDao iDiceDao;

	public DbSeeder(IPlayerDao iPlayerDao, IDiceDao iDiceDao) {
		this.iPlayerDao = iPlayerDao;
		this.iDiceDao = iDiceDao;
	}

	@Override
	public void run(String... args) throws Exception {
		// drop existing data
		iPlayerDao.deleteAll();
		iDiceDao.deleteAll();
		// create data
		Player piero = new Player("1","Piero", "notencoded");
		piero.setRole(com.bcnit14.security.DiceGameRoles.AUTHOR);
		Player jonatan = new Player("2","Jonatan", "iknowitsnosafe");
		jonatan.setRole(com.bcnit14.security.DiceGameRoles.ADMIN);
		iPlayerDao.save(piero);
		iPlayerDao.save(jonatan);
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));

		iPlayerDao.save(new Player("3", "LuckySeven", "casinodestroyer"));
		iDiceDao.save(new Dice("3"));
		iDiceDao.save(new Dice("3"));
		iDiceDao.save(new Dice("3"));
		iDiceDao.save(new Dice("3"));
		iDiceDao.save(new Dice("3"));
		iDiceDao.save(new Dice("3"));

		iPlayerDao.save(new Player("4", "AzarLover", "wastetimeandomoney"));
		iDiceDao.save(new Dice("4"));
		iDiceDao.save(new Dice("4"));
		iDiceDao.save(new Dice("4"));
		iDiceDao.save(new Dice("4"));
		iDiceDao.save(new Dice("4"));
		iDiceDao.save(new Dice("4"));

	}

}
