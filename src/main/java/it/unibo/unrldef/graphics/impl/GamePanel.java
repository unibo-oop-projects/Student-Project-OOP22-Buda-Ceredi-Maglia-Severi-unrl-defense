package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;

import java.awt.Image;

public class GamePanel extends JPanel {

    private World gameWorld;
    private ViewState viewState;
    
    private Image orcImage;
    private Image goblinImage;
    private Image fireballImage;
    private Image arrowsImage;

    private enum ViewState {
        IDLE,
        TOWER_SELECTED,
        SPELL_SELECTED
    }

    public GamePanel(World gameWorld) {
        super();

        this.viewState = ViewState.IDLE;
        //TODO: load assets
        try {
            this.fireballImage = ImageIO.read(new File("assets\\fireball.png"));
            this.arrowsImage = ImageIO.read(new File("assets\\arrows.png"));
            this.orcImage = ImageIO.read(new File("assets\\orc.png"));
            //this.goblinImage = ImageIO.read(new File("assets\\goblin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameWorld = gameWorld;
        
        this.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Position pos = new Position(e.getX(), e.getY());
                switch (viewState) {
                    case IDLE:
                        break;
                    case TOWER_SELECTED:
                        //TODO: send position to World
                        break;
                    case SPELL_SELECTED:
                        //TODO: send position to World
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                return;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                return;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                return;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                return;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                return;
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                return;
            }
            
        });
        JButton cannon = new JButton("CANNON");
        cannon.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                viewState = ViewState.TOWER_SELECTED;
            }
            
        });
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
        
        if(viewState == ViewState.TOWER_SELECTED) {
            // TODO: get available positions from world and render them as rectangles
            // graphic.setColor(java.awt.Color.GREEN);
            // for (Position p: availablePosition) {
            //     graphic.drawRect((int)p.getX(), (int)p.getY(), 50, 50);
            //     graphic.fillRect((int)p.getX(), (int)p.getY(), 50, 50);
            // }
            // graphic.setColor(java.awt.Color.BLACK);
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

        switch(enemy.getName()) {
            case "orc":
                asset = orcImage;
                break;
            case "goblin":
                asset = goblinImage;
                break;
            default:
                break;
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
