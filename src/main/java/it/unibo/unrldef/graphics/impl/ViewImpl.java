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
        this.gamePanel = new GamePanel(world, inputHandler);

        this.gamePanel.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent arg0) {
                System.out.println("New Map Size: " + gamePanel.getWidth() + " " + gamePanel.getHeight());;
            }

            @Override
            public void componentHidden(ComponentEvent arg0) { }

            @Override
            public void componentMoved(ComponentEvent arg0) { }

            @Override
            public void componentShown(ComponentEvent arg0) { }
            
        });
        this.frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                xScale = (double)frame.getWidth() / (double)DEFAULT_WIDTH;
                yScale = (double)frame.getHeight() / (double)DEFAULT_HEIGHT;
                System.out.println("New Size: " + frame.getWidth() + " " + frame.getHeight());
                //gamePanel.setScale(xScale, yScale);
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

            @Override
            public void componentHidden(ComponentEvent e) { }
            
        });


		//TODO: resize handler with scale on GamePanel
		
        final Box mapPanel1 = Box.createVerticalBox();
        final Box mapPanel2 = Box.createHorizontalBox();
        mapPanel2.add(Box.createVerticalGlue());
        mapPanel2.add(this.gamePanel);
        mapPanel2.add(Box.createVerticalGlue());
        mapPanel1.add(Box.createHorizontalGlue());
        mapPanel1.add(mapPanel2);
        mapPanel1.add(Box.createHorizontalGlue());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        PlaceDefenseButton cannon = null;
        PlaceDefenseButton hunter = null;
        this.fireBall = null;
        this.iceSpell = null;

        try {
            cannon = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Cannon.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"cannonIcon.png"))));
            hunter = new PlaceDefenseButton(GamePanel.ViewState.TOWER_SELECTED, Hunter.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"hunterIcon.png"))));
            this.fireBall = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, FireBall.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"fireball.png"))));
            this.iceSpell = new PlaceDefenseButton(GamePanel.ViewState.SPELL_SELECTED, SnowStorm.NAME, gamePanel, 
                    new ImageIcon(ImageIO.read(new File("assets"+File.separator+"snowStorm.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fireBall.setEnabled(false);
        this.iceSpell.setEnabled(false);

		buttonPanel.add(cannon);
		buttonPanel.add(hunter);
		buttonPanel.add(this.fireBall);
		buttonPanel.add(this.iceSpell);
		this.frame.getContentPane().add(mapPanel1);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension preferredSize = new Dimension((int)this.gamePanel.getPreferredSize().getWidth()+PlaceDefenseButton.WIDTH, 
                (int)this.gamePanel.getPreferredSize().getWidth()+PlaceDefenseButton.HEIGHT);
        System.out.println(preferredSize);
        this.frame.setSize(preferredSize);
        this.frame.setMinimumSize(preferredSize);
        this.frame.setPreferredSize(preferredSize);
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
