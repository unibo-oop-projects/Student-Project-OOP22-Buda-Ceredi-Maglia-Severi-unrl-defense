package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import java.util.HashSet;
import java.util.Set;

public class ViewImpl implements View{

    private GamePanel gamePanel;
    private DefenseButtonPanel buttonPanel;
    private final JFrame frame;
    private final Player player;
    private final World world;
    private JLabel bank;
    private JLabel hearts;
    private final MenuPanel menuPanel;
    private final Input inputHandler;

    public ViewImpl(Player player, World world, Input inputHandler){
        this.player = player;
        this.world = world;
        this.inputHandler = inputHandler;
        this.frame = new JFrame("Unreal Defense");
        this.menuPanel = new MenuPanel(inputHandler);
        this.frame.getContentPane().add(this.menuPanel);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.menuPanel.getPreferredSize());
        this.frame.setMinimumSize(this.menuPanel.getPreferredSize());
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
		this.frame.setVisible(true);
    }

    public void initGame() {
        this.frame.getContentPane().remove(this.menuPanel);
        this.gamePanel = new GamePanel(world, inputHandler);
		this.buttonPanel = new DefenseButtonPanel(this.gamePanel, this.world);
        this.bank = new JLabel();
        this.hearts = new JLabel();
        this.buttonPanel.add(this.bank);
        this.buttonPanel.add(this.hearts);
		this.frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
		this.frame.getContentPane().add(this.buttonPanel, BorderLayout.EAST);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.frame.getPreferredSize());
        this.frame.setMinimumSize(this.frame.getPreferredSize());
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
		this.frame.setVisible(true);
    }

    public void updateMenu() {
        this.menuPanel.repaint();
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
        this.buttonPanel.update(buttonsEntities);
        this.bank.setText("€ "+this.world.getMoney());
        this.hearts.setText("<3 "+this.world.getCastleIntegrity().getHearts());
    }
}
