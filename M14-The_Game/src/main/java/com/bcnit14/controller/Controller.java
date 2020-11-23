package com.bcnit14.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bcnit14.dto.Dice;
import com.bcnit14.dto.DiceDto;
import com.bcnit14.dto.Player;
import com.bcnit14.dto.PlayerDto;
import com.bcnit14.services.DiceServicesImpl;
import com.bcnit14.services.PlayerServicesImpl;

@RestController
@RequestMapping("/api")
@PreAuthorize("authenticated")
public class Controller {

	@Autowired
	DiceServicesImpl diceS;
	@Autowired
	PlayerServicesImpl playerS;

	@PostMapping("/players")
	@PreAuthorize("permitAll")
	public ResponseEntity<PlayerDto> addPlayer(@RequestBody Player player) throws Exception {

		if (player.getUsername() == null || player.getUsername() == "") {
			player.setUsername("Anonymus");

		} else if (playerS.findByUsername(player.getUsername()).size() > 0) {
			player.setUsername(player.getUsername() + " already exists.Please choose another.");
			player.setPassword("*******");

			return new ResponseEntity<>(PlayerDto.playerToJson(player), HttpStatus.CONFLICT);
		}
			System.out.println("player created");
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(player)), HttpStatus.OK);

	}

	@PutMapping("/players/{id}")
	public ResponseEntity<PlayerDto> editPlayer(@PathVariable(name = "id") String id, @RequestBody Player player)
			throws Exception {
		Player editedPlayer = null;
		try {
			editedPlayer = playerS.findPlayerById(id);
			editedPlayer.setUsername(player.getUsername());
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
	@PreAuthorize("hasAuthority('player:admin')")
	public void deleteDicesByPlayerId(@PathVariable(name = "id") String id) throws Exception {
		List<Dice> dicesById = diceS.findDicesByPlayerId(id);
		if(dicesById.size() == 0) {
			throw new NoSuchElementException("There are no games registred with the provided id");
		}
			dicesById.forEach(d -> diceS.deleteById(d.getId()));
	}
	
	@DeleteMapping("/players/{id}")
	@PreAuthorize("hasAuthority('player:admin')")
	public void deletePlayerById(@PathVariable(name = "id") String id) throws Exception {
		playerS.deletePlayer(id);

	}

	@GetMapping("/players/{id}")
	public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") String id) throws Exception {
		Player showingPlayer = playerS.findPlayerById(id);
		showingPlayer.setSuccesRate(diceS.findDicesByPlayerId(id));
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(showingPlayer)), HttpStatus.OK);
	}
	
	@GetMapping("/players")
	@PreAuthorize("hasAuthority('player:admin')")
	public ResponseEntity<List<PlayerDto>> showPlayers() throws Exception {
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
	@PreAuthorize("hasAuthority('player:admin')")
	public ResponseEntity<StringDto> avgRanking() throws Exception {

		return new ResponseEntity<>(StringDto
				.stringToJson(Double.toString((double) (diceS.findAllDices().stream().filter(d -> d.isWin()).count())
						/ diceS.findAllDices().size() * 100)),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings/loser")
	@PreAuthorize("hasAuthority('player:admin')")
	public ResponseEntity<PlayerDto> loserRanking() throws Exception {

		return new ResponseEntity<>(PlayerDto.sortBySuccesRate(this.showPlayers().getBody()).get(0), HttpStatus.OK);
	}

	@GetMapping("/players/rankings/winner")
	@PreAuthorize("hasAuthority('player:admin')")
	public ResponseEntity<PlayerDto> winerRanking() throws Exception {
		List<PlayerDto> orderedPlayers = PlayerDto.sortBySuccesRate(this.showPlayers().getBody());
		return new ResponseEntity<>(orderedPlayers.get(orderedPlayers.size() - 1), HttpStatus.OK);
	}

}
