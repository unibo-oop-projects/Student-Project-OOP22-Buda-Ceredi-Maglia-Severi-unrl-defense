package it.unibo.unrldef.graphics.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.api.Input.HitType;


public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton exitButton, startButton;
    private final JTextField nameField;
    private final Input inputHandler;
    private MenuPanel panelRef;
    private double xScale = 1;
    private double yScale = 1;
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 400;

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public MenuPanel(Input inputHandler) {
        super();
        this.panelRef = this;
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int currWidth = panelRef.getWidth();
                int currHeight = panelRef.getHeight();
                xScale = (double)currWidth / DEFAULT_WIDTH;
                yScale = (double)currHeight / DEFAULT_HEIGHT;
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        this.inputHandler = inputHandler;
        this.setLayout(null);
        this.setBackground(new java.awt.Color(194, 148, 103));
        this.nameField = new JTextField();
        this.nameField.setBounds(DEFAULT_WIDTH / 2 - 70, DEFAULT_HEIGHT / 2, 140, 20);
        this.startButton = new JButton("Start");
        this.startButton.setBounds(DEFAULT_WIDTH / 2 - 70, DEFAULT_HEIGHT / 2 + 30, 140, 20);
        this.startButton.addActionListener(e -> {
                if (this.nameField.getText().length() > 0) {
                    this.inputHandler.setLastHit(0, 0, HitType.START_GAME, Optional.of(this.nameField.getText()));
                }
            });
        exitButton = new JButton("Exit");
        exitButton.setBounds(DEFAULT_WIDTH / 2 - 70, DEFAULT_HEIGHT / 2 + 60, 140, 20);
        exitButton.addActionListener(e -> {
                this.inputHandler.setLastHit(0, 0, HitType.EXIT_GAME, Optional.empty());
            });
        this.add(nameField);
        this.add(startButton);
        this.add(exitButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = (panelRef.getWidth()/2) - 85;
        int height = (panelRef.getHeight()/2) - 100;
        int fontName = Math.min((int)xScale, (int)yScale) == 0 ? 20 : (int)((20) * Math.min((int)xScale, (int)yScale));
        int fontSubtitle = Math.min((int)xScale, (int)yScale) == 0 ? 15 : (int)((15) * Math.min((int)xScale, (int)yScale));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, fontName));
        g.drawString("UNREAL DEFENSE", width, height);
        g.setFont(new Font("Arial", Font.BOLD, fontSubtitle));
        g.drawString("Inserisci il tuo nome:", width+15, height + 80);
        this.nameField.setBounds((int)((DEFAULT_WIDTH / 2 - 70) * xScale), (int)((DEFAULT_HEIGHT / 2) * yScale), (int)(140 * xScale), (int)(20 * yScale));
        this.startButton.setBounds((int)((DEFAULT_WIDTH / 2 - 70) * xScale), (int)((DEFAULT_HEIGHT / 2 + 30) * yScale), (int)(140 * xScale), (int)(20 * yScale));
        this.exitButton.setBounds((int)((DEFAULT_WIDTH / 2 - 70) * xScale), (int)((DEFAULT_HEIGHT / 2 + 60) * yScale), (int)(140 * xScale), (int)(20 * yScale));
        if (this.nameField.getText().length() > 0) {
            this.startButton.setEnabled(true);
        } else {
            this.startButton.setEnabled(false);
        }
    }
    
}
