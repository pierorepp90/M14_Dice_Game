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
		iPlayerDao.save(new Player("example1","Piero", "notencoded"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));
		iDiceDao.save(new Dice("1"));

		iPlayerDao.save(new Player("example2", "LuckySeven", "casinodestroyer"));
		iDiceDao.save(new Dice("example2"));
		iDiceDao.save(new Dice("example2"));
		iDiceDao.save(new Dice("example2"));
		iDiceDao.save(new Dice("example2"));
		iDiceDao.save(new Dice("example2"));
		iDiceDao.save(new Dice("example2"));

		iPlayerDao.save(new Player("example3", "AzarLover", "wastetimeandomoney"));
		iDiceDao.save(new Dice("example3"));
		iDiceDao.save(new Dice("example3"));
		iDiceDao.save(new Dice("example3"));
		iDiceDao.save(new Dice("example3"));
		iDiceDao.save(new Dice("example3"));
		iDiceDao.save(new Dice("example3"));

	}

}
