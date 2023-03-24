package it.unibo.unrldef.graphics.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.SnowStorm;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.SpellImpl;
import it.unibo.unrldef.model.impl.TowerImpl;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Builds the panel used for the buttons in the game
 * @author tommaso.severi2@studio.unibo.it
 * @author danilo.maglia@studio.unibo.it
 */
public class DefenseButtonPanel extends JPanel {
    
    public final static int WIDTH = 80;
    public final static int HEIGHT = 80;
    private final World world;
    private final Map<String, JButton> buttons = new HashMap<>();

    public DefenseButtonPanel(final GamePanel gamePanel, final World world) {
        super();
        this.world = world;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new java.awt.Color(194, 148, 103));
        JButton cannon = null;
        JButton hunter = null;
        JButton fireBall = null;
        JButton snowStorm = null;
        try {
            cannon = this.placeDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Cannon.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"cannonIcon.png"))));
            hunter = this.placeDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Hunter.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"hunterIcon.png"))));
            fireBall = this.placeDefenseButton(GamePanel.ViewState.SPELL_SELECTED, FireBall.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"fireballIcon.png"))));
            snowStorm = this.placeDefenseButton(GamePanel.ViewState.SPELL_SELECTED, SnowStorm.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"snowstormIcon.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(cannon);
        this.buttons.put(Cannon.NAME, cannon);
		this.add(hunter);
        this.buttons.put(Hunter.NAME, hunter);
		this.add(fireBall);
        this.buttons.put(FireBall.NAME, fireBall);
		this.add(snowStorm);
        this.buttons.put(SnowStorm.NAME, snowStorm);
    }

    /**
     * Creates a defensive type button and sets its parameters
     */
    private JButton placeDefenseButton(GamePanel.ViewState state, String selectedEntity, GamePanel gamePanel, ImageIcon icon) {
        final JButton button = new JButton(new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH)));
        button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.setState(state);
                gamePanel.setSelectedEntity(selectedEntity);
            }
        });
        return button;
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

    /**
     * Disable all the buttons in the panel
     */
    public void disableAllButtons() {
        this.buttons.values().forEach(b -> b.setEnabled(false));
    }
}
