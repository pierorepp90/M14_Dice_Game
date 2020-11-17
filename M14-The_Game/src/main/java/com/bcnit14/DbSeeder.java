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
		this.iPlayerDao.deleteAll();
		this.iDiceDao.deleteAll();
		// create data
		this.iPlayerDao.save(new Player("example1", "Piero", "123456"));
		this.iDiceDao.save(new Dice("example1"));
		this.iDiceDao.save(new Dice("example1"));
		this.iDiceDao.save(new Dice("example1"));
		this.iDiceDao.save(new Dice("example1"));
		this.iDiceDao.save(new Dice("example1"));
		this.iDiceDao.save(new Dice("example1"));

		this.iPlayerDao.save(new Player("example2", "LuckySeven", "casinodestroyer"));
		this.iDiceDao.save(new Dice("example2"));
		this.iDiceDao.save(new Dice("example2"));
		this.iDiceDao.save(new Dice("example2"));
		this.iDiceDao.save(new Dice("example2"));
		this.iDiceDao.save(new Dice("example2"));
		this.iDiceDao.save(new Dice("example2"));

		this.iPlayerDao.save(new Player("example3", "AzarLover", "wastetimeandomoney"));
		this.iDiceDao.save(new Dice("example3"));
		this.iDiceDao.save(new Dice("example3"));
		this.iDiceDao.save(new Dice("example3"));
		this.iDiceDao.save(new Dice("example3"));
		this.iDiceDao.save(new Dice("example3"));
		this.iDiceDao.save(new Dice("example3"));

	}

}
