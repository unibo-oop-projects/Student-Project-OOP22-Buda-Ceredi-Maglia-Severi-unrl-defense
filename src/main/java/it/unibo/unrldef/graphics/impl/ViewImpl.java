package it.unibo.unrldef.graphics.impl;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.api.Input.HitType;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Player;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.api.World.GameState;
import it.unibo.unrldef.graphics.impl.MenuPanel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implements the view of the game.
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 */
public final class ViewImpl implements View {

    private GamePanel gamePanel;
    private DefenseButtonPanel buttonPanel;
    private final JFrame frame;
    private final Player player;
    private final World world;
    private final MenuPanel menuPanel;
    private final Input inputHandler;

    /**
     * Builds the view of the game starting with the menu.
     * @param player the player of the game
     * @param world the world of the game
     * @param inputHandler the input handler of the game
     */
    public ViewImpl(final Player player, final World world, final Input inputHandler) {
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

    @Override
    public void initGame() {
        this.frame.getContentPane().remove(this.menuPanel);
        this.gamePanel = new GamePanel(world, inputHandler);
        this.buttonPanel = new DefenseButtonPanel(this.gamePanel, this.world);
        this.buttonPanel.add(this.createExitButton());
        this.frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        this.frame.getContentPane().add(this.buttonPanel, BorderLayout.EAST);
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

    @Override
    public void updateMenu() {
        this.menuPanel.repaint();
    }

    /**
     * Creates a new button to exit the game.
     * @return the button to exit the game
     */
    private JButton createExitButton() {
        JButton exit = null;
        try {
            exit = new JButton(new ImageIcon(new ImageIcon(ImageIO.read(new File("resources/assets" + File.separator + "exit.png")))
                    .getImage()
                    .getScaledInstance(DefenseButtonPanel.WIDTH, DefenseButtonPanel.HEIGHT, java.awt.Image.SCALE_SMOOTH)));
            exit.setPreferredSize(new Dimension(DefenseButtonPanel.WIDTH, DefenseButtonPanel.HEIGHT));
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    inputHandler.setLastHit(0, 0, HitType.EXIT_GAME, Optional.empty());
                }
            });
        } catch (IOException e) {
            new ErrorDialog("Error reading the images files");
        }
        return exit;
    }

    /**
     * Updates the HUD state.
     */
    private void updateHUD() {
        final Set<Entity> buttonsEntities = new HashSet<>(this.world.getAvailableTowers());
        buttonsEntities.addAll(this.player.getSpells());
        this.buttonPanel.update(buttonsEntities);
    }

    @Override
    public void renderEndGame(final GameState state) {
        final Graphics g = this.frame.getGraphics();
        g.setFont(new Font("Verdana", Font.PLAIN, this.frame.getWidth() / 8));
        String displayState = "";
        switch (state) {
            case DEFEAT:
                g.setColor(Color.BLACK);
                displayState = "GAME OVER";
                break;
            case VICTORY:
                g.setColor(Color.RED);
                displayState = "YOU WON!";
                break;
            default:
                break;
        }
        this.buttonPanel.disableAllButtons();
        g.drawString(displayState, this.frame.getWidth() / 10, this.frame.getHeight() / 2);
        g.setColor(Color.GREEN);
    }
}
