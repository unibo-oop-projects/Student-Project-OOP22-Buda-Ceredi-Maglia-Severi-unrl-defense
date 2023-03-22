package it.unibo.unrldef.graphics.impl;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.SnowStorm;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.api.World;
import java.util.List;

/**
 * Builds the panel used for the button in the game
 * @author tommaso.severi2@studio.unibo.it
 */
public class ButtonPanel extends JPanel {
    
    private final DefenseButtonsUpdater buttonsUpdater;

    public ButtonPanel(final GamePanel gamePanel, final World world) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new java.awt.Color(194, 148, 103));
        PlaceDefenseButton cannon = null;
        PlaceDefenseButton hunter = null;
        PlaceDefenseButton fireBall = null;
        PlaceDefenseButton iceSpell = null;
        try {
            cannon = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Cannon.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"cannonIcon.png"))));
            hunter = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Hunter.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"hunterIcon.png"))));
            fireBall = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, FireBall.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"fireballIcon.png"))));
            iceSpell = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, SnowStorm.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"snowstormIcon.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(cannon);
		this.add(hunter);
		this.add(fireBall);
		this.add(iceSpell);
        this.buttonsUpdater = new DefenseButtonsUpdater(world, 
                List.of(Cannon.NAME, Hunter.NAME, FireBall.NAME, SnowStorm.NAME), 
                List.of(cannon, hunter, fireBall, iceSpell));
    }

    public DefenseButtonsUpdater getUpdater() {
        return this.buttonsUpdater;
    }
}
