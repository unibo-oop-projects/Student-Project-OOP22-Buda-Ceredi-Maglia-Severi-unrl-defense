package it.unibo.unrldef;

import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.impl.LevelBuilder;
import it.unibo.unrldef.model.impl.PlayerImpl;
import it.unibo.unrldef.core.GameEngine;

/**
 * Main class.
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.uniboit
 * @author danilo.maglia@studio.unibo.it
 */
public final class UnrealDefense {

    private UnrealDefense() {

    }

    /**
     * Main method.
     * @param args unused
     */
    public static void main(final String[] args) {
        final Player p = new PlayerImpl();
        final LevelBuilder level = new LevelBuilder(p);
        final GameEngine engine = new GameEngine(level.levelOne(), p);
        engine.menuLoop();
    }
}
