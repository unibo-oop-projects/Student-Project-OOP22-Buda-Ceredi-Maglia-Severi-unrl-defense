package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.Path.Direction;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Class that builds the levels of the game.
 * 
 * @author francesco.buda3@studio.unibo.it
 *
 */
public class LevelBuilder {

	private final Player player;

	/**
	 * the constructor of the class.
	 * 
	 * @param player
	 */
	public LevelBuilder(final Player player) {
		this.player = player;
	}

	/**
	 * @return the world of the first level
	 */
	public WorldImpl levelOne() {
		return new WorldImpl.Builder("River Castle", this.player, new Position(60, 0), 5, 250)
				.addPathSegment(Direction.DOWN, 15)
				.addPathSegment(Direction.LEFT, 50)
				.addPathSegment(Direction.DOWN, 20)
				.addPathSegment(Direction.RIGHT, 24)
				.addPathSegment(Direction.UP, 6)
				.addPathSegment(Direction.RIGHT, 16)
				.addPathSegment(Direction.DOWN, 22)
				.addPathSegment(Direction.RIGHT, 20)
				.addPathSegment(Direction.DOWN, 18)
				.addPathSegment(Direction.LEFT, 40)
				.addPathSegment(Direction.DOWN, 5)
				.addPathSegment(Direction.END, 0)
				.addWave()
				.addHordeToWave(0, 10000)
				.addMultipleEnemiesToHorde(0, 0, new Orc(), (short) 5)
				.addMultipleEnemiesToHorde(0, 0, new Goblin(), (short) 5)
				.addHordeToWave(0, 10000)
				.addMultipleEnemiesToHorde(0, 1, new Orc(), (short) 5)
				.addMultipleEnemiesToHorde(0, 1, new Goblin(), (short) 5)
				.addWave()
				.addHordeToWave(1, 10000)
				.addMultipleEnemiesToHorde(1, 0, new Orc(), (short) 5)
				.addMultipleEnemiesToHorde(1, 0, new Goblin(), (short) 5)
				.addHordeToWave(1, 10000)
				.addMultipleEnemiesToHorde(1, 1, new Orc(), (short) 5)
				.addMultipleEnemiesToHorde(1, 1, new Goblin(), (short) 5)
				.addAvailableTower(Cannon.NAME, new Cannon())
				.addAvailableTower(Hunter.NAME, new Hunter())
				.addTowerBuildingSpace(28, 6)
				.addTowerBuildingSpace(48, 6)
				.addTowerBuildingSpace(68, 8)
				.addTowerBuildingSpace(18, 24)
				.addTowerBuildingSpace(38, 22)
				.addTowerBuildingSpace(6, 42)
				.addTowerBuildingSpace(58, 42)
				.addTowerBuildingSpace(34, 60)
				.addTowerBuildingSpace(62, 60)
				.addTowerBuildingSpace(46, 74)
				.addTowerBuildingSpace(66, 74)
				.build();
	}

	/**
	 * Method that creates a world from a JSON config file.
	 * 
	 * @author danilo.maglia@studio.unibo.it
	 *
	 * @param fileName the json file path
	 * @return the world created from the file
	 */
	public World fromFile(String fileName) {
		JSONParser parser = new JSONParser();
		WorldImpl.Builder worldBuilder = null;
		JSONObject json = null;
		
		String fileContent = "";
		try {
			// read the whole file passed as argument and put the content in a string
			fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
			json = (JSONObject) parser.parse(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(json == null) {
			return null;
		}
		worldBuilder = this.loadBuilderFromJson(json);
		this.loadPathFromJson(json, worldBuilder);
		// loading waves
		this.loadWavesFromJson(json, worldBuilder);
		// loading available towers
		this.loadAvailableTowers(json, worldBuilder);
		// loading tower building spaces
		this.loadTowerBuildingSpacesFromJson(json, worldBuilder);
		return worldBuilder.build();
	}

	private WorldImpl.Builder loadBuilderFromJson(JSONObject json) {
		JSONObject position = (JSONObject) json.get("startingPosition");
		String worldName = (String) json.get("worldName");
		int castleHearth = ((Long) json.get("castleHearts")).intValue();
		int startingMoney = ((Long) json.get("startingMoney")).intValue();
		return new WorldImpl.Builder(worldName, this.player,
				new Position(((Long) position.get("x")).doubleValue(), ((Long) position.get("y")).doubleValue()),
				castleHearth, startingMoney);
	}

	private void loadPathFromJson(JSONObject json, WorldImpl.Builder worldBuilder) {
		JSONArray path = (JSONArray) json.get("path");
		for (Object p : path) {
			JSONObject pathSegment = (JSONObject) p;
			String direction = (String) pathSegment.get("direction");
			int length = ((Long) pathSegment.get("length")).intValue();
			worldBuilder.addPathSegment(Direction.valueOf(direction), length);
		}
	}
	
	private void loadWavesFromJson(JSONObject json, WorldImpl.Builder worldBuilder) {
		JSONArray waves = (JSONArray) json.get("waves");
		int waveIndex = 0;
		for (Object wave : waves) {
			JSONObject waveObj = (JSONObject) wave;
			JSONArray hordes = (JSONArray) waveObj.get("hordes");
			worldBuilder.addWave();

			// loading hordes
			int hordeIndex = 0;
			for (Object horde : hordes) {
				
				JSONObject hordeObj = (JSONObject) horde;
				int delay = ((Long) hordeObj.get("delay")).intValue();
				worldBuilder.addHordeToWave(waveIndex, delay);
				JSONArray enemies = (JSONArray) hordeObj.get("enemies");
				System.out.println(hordeIndex);
				// loading enemies
				for (Object enemy : enemies) {
					JSONObject enemyObj = (JSONObject) enemy;
					String enemyName = (String) enemyObj.get("type");
					int enemyNumber = ((Long) enemyObj.get("count")).intValue();
					Enemy enemyType = null;
					switch (enemyName) {
						case "orc":
							enemyType = new Orc();
							break;
						case "goblin":
							enemyType = new Goblin();
							break;
						default:
							break;
					}
					worldBuilder.addMultipleEnemiesToHorde(waveIndex, hordeIndex, enemyType,
							(short) enemyNumber);
					
				}
				hordeIndex++;
			}
			waveIndex++;
		}
	}
	private void loadTowerBuildingSpacesFromJson(JSONObject json, WorldImpl.Builder worldBuilder) {
		JSONArray towerBuildingSpaces = (JSONArray) json.get("towerBuildingSpaces");
		for (Object space : towerBuildingSpaces) {
			JSONObject spaceObj = (JSONObject) space;
			int x = ((Long) spaceObj.get("x")).intValue();
			int y = ((Long) spaceObj.get("y")).intValue();
			worldBuilder.addTowerBuildingSpace(x, y);
		}
	}

	private void loadAvailableTowers(JSONObject json, WorldImpl.Builder worldBuilder) {
		JSONArray availableTowers = (JSONArray) json.get("availableTowers");
		for (Object tower : availableTowers) {
			String towerName = (String) tower;
			Tower towerType = null;
			switch (towerName) {
				case "cannon":
					towerType = new Cannon();
					break;
				case "hunter":
					towerType = new Hunter();
					break;
				default:
					break;
			}
			worldBuilder.addAvailableTower(towerName, towerType);
		}
	}
}
