package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Goblin;
import it.unibo.unrldef.model.impl.Orc;

import java.awt.Image;

public class GamePanel extends JPanel {

    private World gameWorld;
    private Image orcImage;
    private Image goblinImage;
    private Image fireballImage;
    private Image arrowsImage;


    public GamePanel(World gameWorld) {
        super();
        //TODO: load assets
        try {
            this.fireballImage = ImageIO.read(new File("assets\\fireball.png"));
            this.arrowsImage = ImageIO.read(new File("assets\\arrows.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (entity instanceof Enemy) {
            renderEnemy(graphic, entity);
        } else if (entity instanceof Spell) {
            this.renderSpell(graphic, entity);
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

    private void renderSpell(final Graphics2D graphic, final Entity spell) {
        Image asset = null;
        if(spell.getName().equals("fireball")) {
            asset = this.fireballImage;
        } else if(spell.getName().equals("arrows")) {
            asset = this.arrowsImage;
        }
        graphic.drawImage(asset, (int)spell.getPosition().get().getX(), (int)spell.getPosition().get().getY(), null);
    }
}
