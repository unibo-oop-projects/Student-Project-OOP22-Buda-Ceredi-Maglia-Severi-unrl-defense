
package it.unibo.unrldef;

import java.util.Optional;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.core.GameEngine;
import it.unibo.unrldef.model.api.Path;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.Goblin;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.Orc;
import it.unibo.unrldef.model.impl.PlayerImpl;
import it.unibo.unrldef.model.impl.WorldImpl;

/*
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.uniboit
 * @author danilo.maglia@pirla.gardena.it
 */
public class UnrealDefense {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		Player p = new PlayerImpl("io");

		World world = new WorldImpl.Builder("mondo1", p, new Position(30, 0), 20)
		.addPathSegment(Path.Direction.DOWN, 7)
		.addPathSegment(Path.Direction.LEFT, 25)
		.addPathSegment(Path.Direction.DOWN, 10)
		.addPathSegment(Path.Direction.RIGHT, 12)
		.addPathSegment(Path.Direction.UP, 2)
		.addPathSegment(Path.Direction.RIGHT, 8)
		.addPathSegment(Path.Direction.DOWN, 11)
		.addPathSegment(Path.Direction.RIGHT, 10)
		.addPathSegment(Path.Direction.DOWN, 8)
		.addPathSegment(Path.Direction.LEFT, 20)
		.addPathSegment(Path.Direction.DOWN, 3)
		.addWave()
		.addHordeToWave(0, 50000)
		.addMultipleEnemiesToHorde(0, 0, new Orc(Optional.of(new Position(30, 0))), (short)5)
		.addMultipleEnemiesToHorde(0, 0, new Goblin(Optional.of(new Position(30, 0))), (short)5)
		.addAvailableTower("cannon", new Cannon(null))
		.addAvailableTower("hunter", new Hunter(null))
		.addTowerBuildingSpace(14, 3)
		.addTowerBuildingSpace(24, 3)
		.addTowerBuildingSpace(34, 4)
		.addTowerBuildingSpace(9, 12)
		.addTowerBuildingSpace(19, 11)
		.addTowerBuildingSpace(3, 21)
		.addTowerBuildingSpace(29, 21)
		.addTowerBuildingSpace(17, 30)
		.addTowerBuildingSpace(31, 30)
		.addTowerBuildingSpace(23, 38)
		.addTowerBuildingSpace(33, 38)
		.build();
		engine.initGame(p, world);
		engine.GameLoop();
	}

}