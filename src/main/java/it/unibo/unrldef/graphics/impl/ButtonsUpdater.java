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

public class ButtonsUpdater {
    
    private final World world;
    private final Map<String, JButton> buttons = new HashMap<>();

    public ButtonsUpdater(final World world, final List<String> respectiveNames, final List<JButton> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            this.buttons.put(respectiveNames.get(i), buttons.get(i));
        }
        this.world = world;
    }

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
                        respectiveButton = this.buttons.get(FireBall.NAME);
                        break;
                    default:
                        break;
                }
            }
            respectiveButton.setEnabled(enableState);
        }
    }
}
