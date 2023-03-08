
package it.unibo.unrldef;

import it.unibo.unrldef.core.GameEngine;

/*
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.uniboit
 * @author danilo.maglia@pirla.gardena.it
 */
public class UnrealDefense {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		engine.initGame("Regina Elisabetta dello sdrogo");
		engine.GameLoop();
	}

}