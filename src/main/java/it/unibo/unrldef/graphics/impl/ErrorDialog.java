package it.unibo.unrldef.graphics.impl;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErrorDialog extends JDialog {
    public ErrorDialog(final String error) {
        super();
        final JPanel dialogPanel = new JPanel(new BorderLayout());
        final JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        this.setTitle("Error");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(this);
        this.add(dialogPanel);
        dialogPanel.add(new JLabel(error), BorderLayout.NORTH);
        dialogPanel.add(exitButton, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }
}
