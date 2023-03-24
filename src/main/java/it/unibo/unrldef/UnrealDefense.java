package it.unibo.unrldef;

import it.unibo.unrldef.core.GameEngine;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.impl.LevelBuilder;
import it.unibo.unrldef.model.impl.PlayerImpl;

/*
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.uniboit
 * @author danilo.maglia@studio.unibo.it
 */
public class UnrealDefense {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		Player p = new PlayerImpl("io");
		LevelBuilder levelBuilder = new LevelBuilder(p);
		engine.initGame(p, levelBuilder.levelOne());
		engine.GameLoop();
	}
}