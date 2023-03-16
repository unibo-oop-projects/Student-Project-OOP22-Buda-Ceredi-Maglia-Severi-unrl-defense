package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.SnowStorm;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.SpellImpl;
import java.io.File;
import java.io.IOException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ViewImpl implements View{

    private final GamePanel gamePanel;
    private final JFrame frame;
    private final Player player;
    private PlaceDefenseButton fireBall;
    private PlaceDefenseButton iceSpell;
    private double xScale = 1;
    private double yScale = 1;
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 600;

    public ViewImpl(Player player, World world, Input inputHandler){
        this.player = player;
        this.frame = new JFrame("Unreal Defense");
        this.frame.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.frame.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.gamePanel = new GamePanel(world, inputHandler);
        this.frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                xScale = (double)frame.getWidth() / (double)DEFAULT_WIDTH;
                yScale = (double)frame.getHeight() / (double)DEFAULT_HEIGHT;
                System.out.println("New Scale: " + xScale + " " + yScale);
                System.out.println("New Size: " + frame.getWidth() + " " + frame.getHeight());
                gamePanel.setScale(xScale, yScale);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
            
        });


		//TODO: resize handler with scale on GamePanel
		
        final Box mapPanel = Box.createVerticalBox();
        mapPanel.add(Box.createVerticalGlue());
        mapPanel.add(this.gamePanel);
        mapPanel.add(Box.createVerticalGlue());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        PlaceDefenseButton cannon = null;
        PlaceDefenseButton hunter = null;
        this.fireBall = null;
        this.iceSpell = null;

        try {
            cannon = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Cannon.NAME, gamePanel, new ImageIcon(ImageIO.read(new File("assets"+File.separator+"cannonIcon.png"))));
            hunter = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Hunter.NAME, gamePanel,new ImageIcon(ImageIO.read(new File("assets"+File.separator+"hunterIcon.png"))));
            this.fireBall = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, FireBall.NAME, gamePanel,new ImageIcon(ImageIO.read(new File("assets"+File.separator+"fireball.png"))));
            this.iceSpell = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, SnowStorm.NAME, gamePanel,new ImageIcon(ImageIO.read(new File("assets"+File.separator+"snowStorm.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fireBall.setEnabled(false);
        this.iceSpell.setEnabled(false);

		buttonPanel.add(cannon);
		buttonPanel.add(hunter);
		buttonPanel.add(this.fireBall);
		buttonPanel.add(this.iceSpell);
		this.frame.getContentPane().add(mapPanel);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize(this.gamePanel.getMinimumSize());
        this.frame.setMaximumSize(this.gamePanel.getMaximumSize());
        this.frame.setPreferredSize(this.gamePanel.getPreferredSize());
		//this.frame.pack();
        this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
    }

    @Override
    public void render() {
        this.updateButtons();
        this.gamePanel.repaint();
    }

    private void updateButtons() {
        for (Spell spell : player.getSpells()) {
            switch (spell.getName()) {
                case FireBall.NAME:
                    this.fireBall.setEnabled(((SpellImpl)spell).isReady());
                    break;
                case SnowStorm.NAME:
                    this.iceSpell.setEnabled(((SpellImpl)spell).isReady());
                    break;
                default:
                    break;
            }
        }
    }
}
