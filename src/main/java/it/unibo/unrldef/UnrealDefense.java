package it.unibo.unrldef;

import it.unibo.unrldef.model.impl.PlayerImpl;
import it.unibo.unrldef.model.impl.WorldImpl;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.graphics.impl.MenuPanel;
import it.unibo.unrldef.graphics.impl.ViewImpl;

/*
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.uniboit
 * @author danilo.maglia@studio.unibo.it
 */
public class UnrealDefense {

	public static void main(String[] args) {
		ViewImpl view = new ViewImpl(new PlayerImpl("DAJE"), null, null);
		while(true){
			view.updateMenu();
		}
		/*
		GameEngine engine = new GameEngine();
		Player p = new PlayerImpl("Sdrogo");
        LevelBuilder level = new LevelBuilder(p);
        engine.initGame(p, level.levelOne());
		engine.GameLoop();
		 */
	}
}