package com.bcnit14.controller;

import java.util.List;
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

		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.createPlayer(player)), HttpStatus.OK);

	}

	@PutMapping("/players/{id}")
	public ResponseEntity<PlayerDto> editPlayer(@PathVariable(name = "id") Integer id, @RequestBody Player player)
			throws Exception {
		Player editedPlayer = playerS.findPlayerById(id);
		editedPlayer.setUserName(player.getUserName());
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.createPlayer(editedPlayer)), HttpStatus.OK);
	}

	@PostMapping("/players/{id}/games")
	public ResponseEntity<DiceDto> rollDice(@PathVariable(name = "id") Integer id) throws Exception {

		return new ResponseEntity<>(DiceDto.diceToJson(diceS.rollDices(playerS.findPlayerById(id))), HttpStatus.OK);
	}

	@DeleteMapping("/players/{id}/games")
	public void deleteDicesByPlayerId(@PathVariable(name = "id") Integer id) throws Exception {
		playerS.showPlayerRolls(id).forEach(d -> diceS.deleteById(d.getId()));
	}

	@DeleteMapping("/players/{id}")
	public void deletePlayerById(@PathVariable(name = "id") Integer id) throws Exception {
		playerS.deletePlayer(id);
	}

	@GetMapping("/players")
	public ResponseEntity<List<PlayerDto>> showPlayers() throws Exception {
		List<PlayerDto> playersDto = playerS.findAllPlayers().stream().map(p -> PlayerDto.playerToJson(p))
				.collect(Collectors.toList());

		return new ResponseEntity<>(playersDto, HttpStatus.OK);
	}

	@GetMapping("/players/{id}")
	public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") Integer id) throws Exception {
		Player showingPlayer = playerS.findPlayerById(id);
		showingPlayer.setSuccesRate();
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.createPlayer(showingPlayer)), HttpStatus.OK);
	}

	@GetMapping("/players/{id}/games")
	public ResponseEntity<List<DiceDto>> showGames(@PathVariable(name = "id") Integer id) throws Exception {
		List<DiceDto> dicesDto = diceS.findAllDices().stream().filter(d -> d.getPlayer().getId().equals(id))
				.map(d -> DiceDto.diceToJson(d)).collect(Collectors.toList());

		return new ResponseEntity<>(dicesDto, HttpStatus.OK);
	}

	@GetMapping("/players/rankings")
	public ResponseEntity<StringDto> avgRanking() throws Exception {

		return new ResponseEntity<>(StringDto.stringToJson(diceS.avgRanking(diceS.findAllDices()) + "%"),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings/loser")
	public ResponseEntity<PlayerDto> loserRanking() throws Exception {
		playerS.findAllPlayers().forEach(p -> p.setSuccesRate());
		List<Player> orderedPlayers = playerS.findAllPlayers();
		playerS.sortBySuccesRate(orderedPlayers);
		return new ResponseEntity<>(PlayerDto.playerToJson(orderedPlayers.get(0)), HttpStatus.OK);
	}

	@GetMapping("/players/rankings/winner")
	public ResponseEntity<PlayerDto> winerrRanking() throws Exception {
		playerS.findAllPlayers().forEach(p -> p.setSuccesRate());
		List<Player> orderedPlayers = playerS.findAllPlayers();
		playerS.sortBySuccesRate(orderedPlayers);
		return new ResponseEntity<>(PlayerDto.playerToJson(orderedPlayers.get(orderedPlayers.size() - 1)),
				HttpStatus.OK);
	}

}
