package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.SnowStorm;
import it.unibo.unrldef.model.impl.Cannon;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ViewImpl implements View{

    private final GamePanel gamePanel;
    private final JFrame frame;
    private final Player player;
    private final World world;
    private final ButtonsUpdater buttonsUpdater;
    private double xScale = 1;
    private double yScale = 1;
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 600;

    public ViewImpl(Player player, World world, Input inputHandler){
        this.player = player;
        this.world = world;
        this.frame = new JFrame("Unreal Defense");
        this.gamePanel = new GamePanel(world, inputHandler);

        this.frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                xScale = (double)frame.getWidth() / (double)DEFAULT_WIDTH;
                yScale = (double)frame.getHeight() / (double)DEFAULT_HEIGHT;
                gamePanel.setScale(xScale, yScale);
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

            @Override
            public void componentHidden(ComponentEvent e) { }
            
        });

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
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
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"fireball.png"))));
            iceSpell = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, SnowStorm.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"snowStorm.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.buttonsUpdater = new ButtonsUpdater(this.world, 
                List.of(Cannon.NAME, Hunter.NAME, FireBall.NAME, SnowStorm.NAME), 
                List.of(cannon, hunter, fireBall, iceSpell));

		buttonPanel.add(cannon);
		buttonPanel.add(hunter);
		buttonPanel.add(fireBall);
		buttonPanel.add(iceSpell);
		this.frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension preferredSize = new Dimension((int)this.gamePanel.getPreferredSize().getWidth()+PlaceDefenseButton.WIDTH, 
                (int)this.gamePanel.getPreferredSize().getHeight());
        this.frame.setSize(preferredSize);
        this.frame.setMinimumSize(preferredSize);
        this.frame.setPreferredSize(preferredSize);
        this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
    }

    @Override
    public void render() {
        // TO-DO fix method getAvailableTowers
        //this.buttonsUpdater.update(Set.of(this.world.getAvailableTowers() ,this.player.getSpells()));
        this.gamePanel.repaint();
    }
}
