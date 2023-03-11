
package it.unibo.unrldef;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.core.GameEngine;
import it.unibo.unrldef.model.api.Path;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
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
		Player p = new PlayerImpl("io", null);
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

		.build();
		p.setGameMap(world);
		engine.initGame("Regina Elisabetta dello sdrogo");
		engine.GameLoop();
	}

}