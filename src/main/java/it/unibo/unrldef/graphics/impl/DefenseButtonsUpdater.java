package it.unibo.unrldef.graphics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.SpellImpl;
import it.unibo.unrldef.model.impl.TowerImpl;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.SnowStorm;

/**
 * A simple class that updates the buttons used in the tower defense game
 * @author tommaso.severi2@studio.unibo.it
 */
public class DefenseButtonsUpdater {
    
    private final World world;
    private final Map<String, JButton> buttons = new HashMap<>();

    /**
     * Creates a new buttons updater
     * @param world the world where the entities are based
     * @param respectiveNames the names associated with the buttons
     * @param respectiveButtons the buttons that correspond the the names having the same order in the list
     */
    public DefenseButtonsUpdater(final World world, final List<String> respectiveNames, final List<JButton> respectiveButtons) {
        for (int i = 0; i < respectiveButtons.size(); i++) {
            this.buttons.put(respectiveNames.get(i), respectiveButtons.get(i));
        }
        this.world = world;
    }

    /**
     * Updates the state of the buttons
     * @param referenceEntities the entities from which are taken the informations used to update the buttons
     */
    public void update(Set<Entity> referenceEntities) {
        for (Entity entity : referenceEntities) {
            boolean enableState = false;
            JButton respectiveButton = new JButton();
            if (entity instanceof Tower) {
                enableState = ((TowerImpl)entity).getCost() <= this.world.getMoney();
                switch (entity.getName()) {
                    case Hunter.NAME:
                        respectiveButton = this.buttons.get(Hunter.NAME);
                        break;
                    case Cannon.NAME:
                        respectiveButton = this.buttons.get(Cannon.NAME);
                        break;
                    default:
                        break;
                }
            } else {
                enableState = ((SpellImpl)entity).isReady();
                switch (entity.getName()) {
                    case FireBall.NAME:
                        respectiveButton = this.buttons.get(FireBall.NAME);
                        break;
                    case SnowStorm.NAME:
                        respectiveButton = this.buttons.get(SnowStorm.NAME);
                        break;
                    default:
                        break;
                }
            }
            respectiveButton.setEnabled(enableState);
        }
    }
}
