
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
		int scale = 14;
		GameEngine engine = new GameEngine();
		Player p = new PlayerImpl("io");

		World world = new WorldImpl.Builder("mondo1", p, new Position(30*scale, 0), 20)
		.addPathSegment(Path.Direction.DOWN, 7*scale)
		.addPathSegment(Path.Direction.LEFT, 25*scale)
		.addPathSegment(Path.Direction.DOWN, 10*scale)
		.addPathSegment(Path.Direction.RIGHT, 12*scale)
		.addPathSegment(Path.Direction.UP, 2*scale)
		.addPathSegment(Path.Direction.RIGHT, 8*scale)
		.addPathSegment(Path.Direction.DOWN, 11*scale)
		.addPathSegment(Path.Direction.RIGHT, 10*scale)
		.addPathSegment(Path.Direction.DOWN, 8*scale)
		.addPathSegment(Path.Direction.LEFT, 20*scale)
		.addPathSegment(Path.Direction.DOWN, 3*scale)
		.addPathSegment(Path.Direction.END, 0)
		.addWave()
		.addHordeToWave(0, 50000)
		.addMultipleEnemiesToHorde(0, 0, new Orc(Optional.of(new Position(30*scale, 0))), (short)5)
		.addMultipleEnemiesToHorde(0, 0, new Goblin(Optional.of(new Position(30*scale, 0))), (short)5)
		.addAvailableTower("cannon", new Cannon(null))
		.addAvailableTower("hunter", new Hunter(null))
		.addTowerBuildingSpace(14*scale, 3*scale)
		.addTowerBuildingSpace(24*scale, 3*scale)
		.addTowerBuildingSpace(34*scale, 4*scale)
		.addTowerBuildingSpace(9*scale, 12*scale)
		.addTowerBuildingSpace(19*scale, 11*scale)
		.addTowerBuildingSpace(3*scale, 21*scale)
		.addTowerBuildingSpace(29*scale, 21*scale)
		.addTowerBuildingSpace(17*scale, 30*scale)
		.addTowerBuildingSpace(31*scale, 30*scale)
		.addTowerBuildingSpace(23*scale, 38*scale)
		.addTowerBuildingSpace(33*scale, 38*scale)
		.build();
		engine.initGame(p, world);
		engine.GameLoop();
	}

}