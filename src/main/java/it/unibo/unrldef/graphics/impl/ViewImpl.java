package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.IceSpell;
import it.unibo.unrldef.model.impl.SpellImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewImpl implements View{

    private final GamePanel gamePanel;
    private final JFrame frame;
    private final Player player;
    private final JButton fireBall;
    private final JButton iceSpell;

    public ViewImpl(Player player, World world, Input inputHandler){
        this.player = player;
        this.frame = new JFrame("Unreal Defense");
		//TODO: resize handler with scale on GamePanel
		this.gamePanel = new GamePanel(world, inputHandler);
        final Box mapPanel = Box.createVerticalBox();
        mapPanel.add(Box.createVerticalGlue());
        mapPanel.add(this.gamePanel);
        mapPanel.add(Box.createVerticalGlue());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton cannon = new JButton("CANNON");
        cannon.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.setState(GamePanel.ViewState.TOWER_SELECTED);
				gamePanel.setSelectedEntity("cannon");
            }
            
        });

        JButton hunter = new JButton("HUNTER");
        hunter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
				gamePanel.setState(GamePanel.ViewState.TOWER_SELECTED);
				gamePanel.setSelectedEntity("hunter");
                
            }
            
        });

        this.fireBall = new JButton("FIREBALL");
        this.fireBall.setEnabled(false);
        this.fireBall.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
				gamePanel.setState(GamePanel.ViewState.SPELL_SELECTED);
				gamePanel.setSelectedEntity("fireball");
                
            }
            
        });

        this.iceSpell = new JButton("ICE SPELL");
        this.iceSpell.setEnabled(false);
        this.iceSpell.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
				gamePanel.setState(GamePanel.ViewState.SPELL_SELECTED);
				gamePanel.setSelectedEntity("ice-spell");
            }
            
        });
		
		buttonPanel.add(cannon);
		buttonPanel.add(hunter);
		buttonPanel.add(fireBall);
		buttonPanel.add(iceSpell);
		this.frame.getContentPane().add(mapPanel);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize(this.gamePanel.getMinimumSize());
        this.frame.setMaximumSize(this.gamePanel.getMaximumSize());
        this.frame.setPreferredSize(this.gamePanel.getPreferredSize());
		this.frame.pack();
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
                case IceSpell.NAME:
                    this.iceSpell.setEnabled(((SpellImpl)spell).isReady());
                    break;
                default:
                    break;
            }
        }
    }
}
