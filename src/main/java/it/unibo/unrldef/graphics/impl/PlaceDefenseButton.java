package it.unibo.unrldef.graphics.impl;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceDefenseButton extends JButton{
    private final static int WIDTH = 80;
    private final static int HEIGHT = 80;
    
    public PlaceDefenseButton(GamePanel.ViewState state, String selectedEntity, GamePanel gamePanel, ImageIcon icon) {
        super(new ImageIcon(icon.getImage().getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH)));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.setState(state);
                gamePanel.setSelectedEntity(selectedEntity);
            }
        });
    }
}
