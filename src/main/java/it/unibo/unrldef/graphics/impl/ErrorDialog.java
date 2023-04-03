package it.unibo.unrldef.graphics.impl;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.input.api.Input.HitType;

import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Optional;
import java.awt.event.ActionEvent;

/**
 * A dialog that shows an error message and exits the program.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public final class ErrorDialog extends JDialog {

    private final Input inputHandler;

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ErrorDialog.
     * 
     * @param error the error message to show
     * @param input the input handler
     */
    public ErrorDialog(final String error, final Input input) {
        super();

        final JPanel dialogPanel = new JPanel(new BorderLayout());
        final JButton exitButton = new JButton("Exit");
        this.inputHandler = Objects.requireNonNull(input);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                inputHandler.setLastHit(0, 0, HitType.EXIT_GAME, Optional.empty());
            }
        });

        this.setTitle("Error");
        this.add(dialogPanel);
        dialogPanel.add(new JLabel(error), BorderLayout.NORTH);
        dialogPanel.add(exitButton, BorderLayout.SOUTH);

    }

    /**
     * Shows the dialog.
     */
    public void showDialog() {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(this);
        this.pack();
        this.setVisible(true);
    }
}
