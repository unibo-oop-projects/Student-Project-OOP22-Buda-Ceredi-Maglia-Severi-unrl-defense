package it.unibo.unrldef.graphics.impl;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.model.api.World;

public class ViewImpl implements View{

    private final GamePanel gamePanel;
    private final JFrame frame;

    public ViewImpl(World world){
        this.frame = new JFrame("Unreal Defense");
		this.frame.setSize(1280,720);
		this.frame.setMinimumSize(new Dimension(1280,720));
		this.frame.setResizable(true);
		// frame.setUndecorated(true); // Remove title bar
		this.gamePanel = new GamePanel(world);
		this.frame.getContentPane().add(this.gamePanel);
		this.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
		this.frame.pack();
		this.frame.setVisible(true);
    }

    @Override
    public void render() {
        this.gamePanel.repaint();
    }
    
}
