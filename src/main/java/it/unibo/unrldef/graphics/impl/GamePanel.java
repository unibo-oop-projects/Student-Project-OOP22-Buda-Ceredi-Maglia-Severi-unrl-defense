package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Goblin;
import it.unibo.unrldef.model.impl.Orc;

import java.awt.Image;

public class GamePanel extends JPanel {

    private World gameWorld;
    private Image orcImage;
    private Image goblinImage;


    public GamePanel(World gameWorld) {
        super();
        //TODO: load assets
        this.gameWorld = gameWorld;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphic = (Graphics2D) g;

        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphic.clearRect(0,0,this.getWidth(),this.getHeight());
        // TODO: render the game world
        for (Entity entity : gameWorld.getSceneEntities()) {
            renderEntity(graphic, entity);
        }
    }

    private void renderEntity(Graphics2D graphic, Entity entity) {
        if(entity instanceof Enemy) {
            renderEnemy(graphic, entity);
        }
    }   
    
    private void renderEnemy(Graphics2D graphic, Entity enemy) {
        Image asset = null;
        
        if(enemy instanceof Orc) {
            asset = orcImage;
        } else if(enemy instanceof Goblin) {
            asset = goblinImage;
        }

        graphic.drawImage(asset, (int)enemy.getPosition().get().getX(), (int)enemy.getPosition().get().getY(), null);
    }
}
