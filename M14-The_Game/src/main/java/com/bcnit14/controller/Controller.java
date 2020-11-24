package com.bcnit14.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcnit14.dto.Dice;
import com.bcnit14.dto.DiceDto;
import com.bcnit14.dto.Player;
import com.bcnit14.dto.PlayerDto;
import com.bcnit14.services.DiceServicesImpl;
import com.bcnit14.services.PlayerServicesImpl;

@RestController
@RequestMapping("/api")
public class Controller {

	@Autowired
	DiceServicesImpl diceS;
	@Autowired
	PlayerServicesImpl playerS;

	@PostMapping("/players")
	public ResponseEntity<PlayerDto> addPlayer(@RequestBody Player player) throws Exception {

		if (player.getUserName() == null || player.getUserName() == "") {
			player.setUserName("Anonymus");

		} else if (playerS.findByUserName(player.getUserName()).size() > 0) {
			player.setUserName(player.getUserName() + " already exists.Please choose another.");
			player.setPassword("*******");

			return new ResponseEntity<>(PlayerDto.playerToJson(player), HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(player)), HttpStatus.OK);

	}

	@PutMapping("/players/{id}")
	public ResponseEntity<PlayerDto> editPlayer(@PathVariable(name = "id") String id, @RequestBody Player player)
			throws Exception {
		Player editedPlayer = null;
		try {
			editedPlayer = playerS.findPlayerById(id);
			editedPlayer.setUserName(player.getUserName());
		} catch (Exception e) {
			System.out.println("That id is not in our database");
			e.printStackTrace();
		}
		return new ResponseEntity<>(this.addPlayer(editedPlayer).getBody(), HttpStatus.OK);
	}

	@PostMapping("/players/{id}/games")
	public ResponseEntity<DiceDto> rollDice(@PathVariable(name = "id") String id) throws Exception {
		@SuppressWarnings("unused")
		Player playerById = playerS.findPlayerById(id);
		return new ResponseEntity<>(DiceDto.diceToJson(diceS.rollDicesById(id)), HttpStatus.OK);
	}

	@DeleteMapping("/players/{id}/games")
	public void deleteDicesByPlayerId(@PathVariable(name = "id") String id) throws Exception {
		List<Dice> dicesByPlayerId = diceS.findDicesByPlayerId(id);
		if(dicesByPlayerId.size() == 0) {
			throw new NoSuchElementException("There are no games registred with the provided id");
		}
		dicesByPlayerId.forEach(d -> diceS.deleteById(d.getId()));
	}

	@DeleteMapping("/players/{id}")
	public void deletePlayerById(@PathVariable(name = "id") String id) throws Exception {
		@SuppressWarnings("unused")
		Player playerById = playerS.findPlayerById(id);
		playerS.deletePlayer(id);

	}

	@GetMapping("/players/{id}")
	public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") String id) throws Exception {
		Player showingPlayer = playerS.findPlayerById(id);
		showingPlayer.setSuccesRate(diceS.findDicesByPlayerId(id));
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(showingPlayer)), HttpStatus.OK);
	}

	@GetMapping("/players")
	public ResponseEntity<List<PlayerDto>> showPlayers() throws Exception {
//		playerS.findAllPlayers().stream().forEach(p -> p.setSuccesRate(diceS.findDicesByPlayerId(p.getId())));
//		playerS.saveAll();
		List<PlayerDto> playersWithSR = playerS.findAllPlayers().stream().map(p -> {
			try {
				return this.getPlayerById(p.getId()).getBody();
			} catch (Exception e) {
			}
			return null;
		}).collect(Collectors.toList());

		return new ResponseEntity<>(playersWithSR, HttpStatus.OK);
	}

	@GetMapping("/players/{id}/games")
	public ResponseEntity<List<DiceDto>> showGames(@PathVariable(name = "id") String id) throws Exception {

		return new ResponseEntity<>(
				diceS.findDicesByPlayerId(id).stream().map(d -> DiceDto.diceToJson(d)).collect(Collectors.toList()),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings")
	public ResponseEntity<StringDto> avgRanking() throws Exception {

		return new ResponseEntity<>(StringDto
				.stringToJson(Double.toString((double) (diceS.findAllDices().stream().filter(d -> d.isWin()).count())
						/ diceS.findAllDices().size() * 100)),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings/loser")
	public ResponseEntity<PlayerDto> loserRanking() throws Exception {

		return new ResponseEntity<>(PlayerDto.sortBySuccesRate(this.showPlayers().getBody()).get(0), HttpStatus.OK);
	}

	@GetMapping("/players/rankings/winner")
	public ResponseEntity<PlayerDto> winerrRanking() throws Exception {
		List<PlayerDto> orderedPlayers = PlayerDto.sortBySuccesRate(this.showPlayers().getBody());
		return new ResponseEntity<>(orderedPlayers.get(orderedPlayers.size() - 1), HttpStatus.OK);
	}

}
