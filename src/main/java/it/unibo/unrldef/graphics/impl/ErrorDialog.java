package it.unibo.unrldef.graphics.impl;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.unrldef.input.api.InputHandler;
import it.unibo.unrldef.input.api.Input.InputType;
import it.unibo.unrldef.input.impl.InputImpl;

import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A dialog that shows an error message and exits the program.
 * 
 * @author danilo.maglia@studio.unibo.it
 */
public final class ErrorDialog extends JDialog {

    private final InputHandler inputHandler;

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ErrorDialog.
     * 
     * @param error the error message to show
     * @param input the input handler
     */
    public ErrorDialog(final String error, final InputHandler input) {
        super();

        final JPanel dialogPanel = new JPanel(new BorderLayout());
        final JButton exitButton = new JButton("Exit");
        this.inputHandler = input;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                inputHandler.addInput(new InputImpl(InputType.EXIT_GAME));
                dispose();
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
