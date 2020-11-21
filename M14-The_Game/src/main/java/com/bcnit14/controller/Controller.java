package com.bcnit14.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(player)), HttpStatus.OK);

	}

	@PutMapping("/players/{id}")
	@PreAuthorize("hasAuthority('player:read')")
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
	@PreAuthorize("hasAuthority('player:play')")
	public ResponseEntity<DiceDto> rollDice(@PathVariable(name = "id") String id) throws Exception {
		return new ResponseEntity<>(DiceDto.diceToJson(diceS.rollDicesById(id)), HttpStatus.OK);
	}

	@DeleteMapping("/players/{id}/games")
	@PreAuthorize("hasAuthority('player:delete')")
	public void deleteDicesByPlayerId(@PathVariable(name = "id") String id) throws Exception {
		diceS.findDicesByPlayerId(id).forEach(d -> diceS.deleteById(d.getId()));
	}

	@DeleteMapping("/players/{id}")
	@PreAuthorize("hasAnyRole('ROLE_AUTHOR','ROLE_ADMIN')")
	public void deletePlayerById(@PathVariable(name = "id") String id) throws Exception {
		playerS.deletePlayer(id);

	}

	@GetMapping("/players/{id}")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") String id) throws Exception {
		Player showingPlayer = playerS.findPlayerById(id);
		showingPlayer.setSuccesRate(diceS.findDicesByPlayerId(id));
		return new ResponseEntity<>(PlayerDto.playerToJson(playerS.savePlayer(showingPlayer)), HttpStatus.OK);
	}

	@GetMapping("/players")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<List<PlayerDto>> showPlayers() throws Exception {
//		playerS.findAllPlayers().stream().forEach(p -> p.setSuccesRate(diceS.findDicesByPlayerId(p.getId())));
//		playerS.saveAll();
		List<PlayerDto> playersWithSR = playerS.findAllPlayers().stream().map(p -> {
			try {
				return this.getPlayerById(p.getId()).getBody();
			} catch (Exception e) {
				System.out.println("Can you print this?");
			}
			return null;
		}).collect(Collectors.toList());

		return new ResponseEntity<>(playersWithSR, HttpStatus.OK);
	}

	@GetMapping("/players/{id}/games")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<List<DiceDto>> showGames(@PathVariable(name = "id") String id) throws Exception {

		return new ResponseEntity<>(
				diceS.findDicesByPlayerId(id).stream().map(d -> DiceDto.diceToJson(d)).collect(Collectors.toList()),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<StringDto> avgRanking() throws Exception {

		return new ResponseEntity<>(StringDto
				.stringToJson(Double.toString((double) (diceS.findAllDices().stream().filter(d -> d.isWin()).count())
						/ diceS.findAllDices().size() * 100)),
				HttpStatus.OK);
	}

	@GetMapping("/players/rankings/loser")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<PlayerDto> loserRanking() throws Exception {

		return new ResponseEntity<>(PlayerDto.sortBySuccesRate(this.showPlayers().getBody()).get(0), HttpStatus.OK);
	}

	@GetMapping("/players/rankings/winner")
	@PreAuthorize("hasAuthority('player:read')")
	public ResponseEntity<PlayerDto> winerRanking() throws Exception {
		List<PlayerDto> orderedPlayers = PlayerDto.sortBySuccesRate(this.showPlayers().getBody());
		return new ResponseEntity<>(orderedPlayers.get(orderedPlayers.size() - 1), HttpStatus.OK);
	}

}
