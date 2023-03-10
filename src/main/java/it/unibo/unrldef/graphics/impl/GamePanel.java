package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Orc;
import it.unibo.unrldef.model.impl.Arrows;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Goblin;

import java.awt.Image;

public class GamePanel extends JPanel {
    private String selectedEntity;

    private World gameWorld;
    private ViewState viewState;
    
    private Image orcImage;
    private Image goblinImage;
    private Image fireballImage;
    private Image arrowsImage;
    private Image map;

    private enum ViewState {
        IDLE,
        TOWER_SELECTED,
        SPELL_SELECTED
    }

    public GamePanel(World gameWorld, Input inputHandler) {
        super();

        this.viewState = ViewState.IDLE;
        //TODO: load assets
        try {
            this.fireballImage = ImageIO.read(new File("assets\\fireball.png"));
            this.arrowsImage = ImageIO.read(new File("assets\\arrows.png"));
            this.orcImage = ImageIO.read(new File("assets\\orc.png"));
            //this.goblinImage = ImageIO.read(new File("assets\\goblin.png"));
            this.map = ImageIO.read(new File("assets\\debugMap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameWorld = gameWorld;
        
        this.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                switch (viewState) {
                    case IDLE:
                        break;
                    case TOWER_SELECTED:
                        inputHandler.setLastHit(x, y, Input.HitType.PLACE_TOWER, Optional.of(selectedEntity));
                        break;
                    case SPELL_SELECTED:
                        inputHandler.setLastHit(x, y, Input.HitType.PLACE_SPELL, Optional.of(selectedEntity));
                        break;
                }
                viewState = ViewState.IDLE; // reset the view state every time the mouse is clicked
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

        // TODO: Create a class to avoid code repetition

        JButton cannon = new JButton("CANNON");
        cannon.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                viewState = ViewState.TOWER_SELECTED;
                selectedEntity = "cannon";
            }
            
        });

        JButton hunter = new JButton("HUNTER");
        hunter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                viewState = ViewState.TOWER_SELECTED;
                selectedEntity = "hunter";
            }
            
        });

        JButton fireBall = new JButton("FIREBALL");
        fireBall.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                viewState = ViewState.SPELL_SELECTED;
                selectedEntity = "fireball";
            }
            
        });

        JButton arrows = new JButton("ARROWS");
        arrows.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                viewState = ViewState.SPELL_SELECTED;
                selectedEntity = "arrows";
            }
            
        });

        this.add(cannon);
        this.add(hunter);
        this.add(fireBall);
        this.add(arrows);
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
            // List<Position> availablePosition = this.gameWorld.getAvailablePosition();
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
            case Orc.NAME:
                asset = orcImage;
                break;
            case Goblin.NAME:
                asset = goblinImage;
                break;
            default:
                break;
        }

        graphic.drawImage(asset, (int)enemy.getPosition().get().getX(), (int)enemy.getPosition().get().getY(), null);
    }

    private void renderSpell(final Graphics2D graphic, final Entity spell) {
        Image asset = null;
        switch (spell.getName()) {
            case FireBall.NAME:
                asset = this.fireballImage;
                break;
            case Arrows.NAME:
                asset = this.arrowsImage;
                break;
            default:
                break;
        }
        graphic.drawImage(asset, (int)spell.getPosition().get().getX(), (int)spell.getPosition().get().getY(), null);
    }

    private void renderMap(final Graphics2D graphic) {
        graphic.drawImage(this.map, 0, 0, null);
    }
}
