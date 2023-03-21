package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import java.util.HashSet;
import java.util.Set;

public class ViewImpl implements View{

    private final GamePanel gamePanel;
    private final JFrame frame;
    private final Player player;
    private final World world;
    private final DefenseButtonsUpdater buttonsUpdater;
    private final JLabel bank;

    public ViewImpl(Player player, World world, Input inputHandler){
        this.player = player;
        this.world = world;
        this.frame = new JFrame("Unreal Defense");
        this.gamePanel = new GamePanel(world, inputHandler);
		ButtonPanel buttonPanel = new ButtonPanel(this.gamePanel, this.world);
        this.buttonsUpdater = buttonPanel.getUpdater();
        this.bank = new JLabel("$ "+this.world.getMoney());
        buttonPanel.add(this.bank);
		this.frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.frame.getPreferredSize());
        this.frame.setMinimumSize(this.frame.getPreferredSize());
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
		this.frame.setVisible(true);
    }

    @Override
    public void render() {
        this.updateHUD();
        this.gamePanel.repaint();
    }

    /**
     * Updates the HUD state
     */
    private void updateHUD() {
        final Set<Entity> buttonsEntities = new HashSet<Entity>(this.world.getAvailableTowers());
        buttonsEntities.addAll(this.player.getSpells());
        this.buttonsUpdater.update(buttonsEntities);
        this.bank.setText("$ "+this.world.getMoney());
    }
}
