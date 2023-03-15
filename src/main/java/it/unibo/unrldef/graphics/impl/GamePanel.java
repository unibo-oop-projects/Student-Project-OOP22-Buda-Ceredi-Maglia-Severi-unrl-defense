package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Toolkit;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Orc;
import it.unibo.unrldef.model.impl.Arrows;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Goblin;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.Hunter;

import java.awt.Image;

public class GamePanel extends JPanel {
    private String selectedEntity;

    private World gameWorld;
    private ViewState viewState;
    
    private Image orcImage;
    private Image goblinImage;
    //private Image fireballFalling;
    private Image fireballOnGround;
    private Image arrowsImage;
    private Image map;
    private Image cannonImage;
    private Image hunterImage;
    private Image shootingCannon;
    private Image shootingHunter;
    private double xScale = 1;
    private double yScale = 1;
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 600;

    public enum ViewState {
        IDLE,
        TOWER_SELECTED,
        SPELL_SELECTED
    }

    public GamePanel(World gameWorld, Input inputHandler) {
        super(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        this.viewState = ViewState.IDLE;
        //TODO: load assets
        try {
            // this.fireballFalling = ImageIO.read(new File("assets"+File.separator+"fireball.png"));
            this.fireballOnGround = ImageIO.read(new File("assets"+File.separator+"fireball_ground.png"));
            this.arrowsImage = ImageIO.read(new File("assets"+File.separator+"arrows.png"));
            this.orcImage = ImageIO.read(new File("assets"+File.separator+"orc.png"));
            this.goblinImage = ImageIO.read(new File("assets"+File.separator+"goblin.png"));
            this.map = ImageIO.read(new File("assets"+File.separator+"debugMap.png")).getScaledInstance(DEFAULT_WIDTH, DEFAULT_HEIGHT, java.awt.Image.SCALE_SMOOTH);
            this.hunterImage = ImageIO.read(new File("assets"+File.separator+"Hunter.png"));
            this.cannonImage = ImageIO.read(new File("assets"+File.separator+"Cannon.png"));
            this.shootingCannon = ImageIO.read(new File("assets"+File.separator+"shootingCannon.png"));
            this.shootingHunter = ImageIO.read(new File("assets"+File.separator+"shootingHunter.png"));
            this.ballEsplosion = ImageIO.read(new File("assets"+File.separator+"esplosione.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameWorld = gameWorld;

        this.add(new JLabel(new ImageIcon(map)));
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        
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
    }

    public void setState(ViewState state) {
        this.viewState = state;
    }

    public void setSelectedEntity(String entity) {
        this.selectedEntity = entity;
    }

    public void setScale(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH*(int)xScale,(int)DEFAULT_HEIGHT*(int)yScale));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphic = (Graphics2D) g;
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphic.clearRect(0,0,this.getWidth(),this.getHeight());
        // TODO: render the game world
        renderMap(graphic);
        for (Entity entity : gameWorld.getSceneEntities()) {
            renderEntity(graphic, entity);
        }
        
        if(viewState == ViewState.TOWER_SELECTED) {
            // TODO: get available positions from world and render them as rectangles
            Set<Position> availablePosition = this.gameWorld.getAvailablePositions();
            graphic.setColor(java.awt.Color.GREEN);
            for (Position p: availablePosition) {
                graphic.drawRect((int)p.getX(), (int)p.getY(), 1, 1);
                graphic.fillRect((int)p.getX(), (int)p.getY(), 1, 1);
            }
            graphic.setColor(java.awt.Color.BLACK);
        }
    }

    private void renderEntity(Graphics2D graphic, Entity entity) {
        if (entity instanceof Enemy) {
            renderEnemy(graphic, entity);
        } else if (entity instanceof Spell) {
            this.renderSpell(graphic, entity);
        } else if (entity instanceof Tower) {
            this.renderTower(graphic, entity);
        }
    }   
    
    private void renderTower(Graphics2D graphic, Entity tower) {
        Image towerAsset = null;
        Optional<Enemy> target = ((Tower)tower).getTarget();
        switch(tower.getName()) {
            case Cannon.NAME:
                if (target.isPresent()) {
                    towerAsset = shootingCannon;

                    graphic.drawImage(ballEsplosion, (int)target.get().getPosition().get().getX(), (int)target.get().getPosition().get().getY(), 100, 100, null);
                } else {
                    towerAsset = cannonImage;
                }
                break;
            case Hunter.NAME:
                if (target.isPresent()) {
                    towerAsset = shootingHunter;

                    graphic.setColor(Color.RED);
                    graphic.setStroke(new BasicStroke(20));
                    System.out.println("Drawing line from " + tower.getPosition().get() + " to " + target.get().getPosition().get());
                    graphic.drawLine((int)tower.getPosition().get().getX(), (int)tower.getPosition().get().getY(), (int)target.get().getPosition().get().getX(), (int)target.get().getPosition().get().getY());
                } else {
                    towerAsset = hunterImage;
                }
                break;
            default:
                System.out.println("Drawing default");
                break;
        }
        int width = (int) Math.round(100 * xScale);
        int height = (int) Math.round(100 * yScale);
        graphic.drawImage(towerAsset, (int)tower.getPosition().get().getX(), (int)tower.getPosition().get().getY(), width, height, null);
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
        int width = (int) Math.round(40 * xScale);
        int height = (int) Math.round(40 * yScale);
        int x = (int)Math.round(enemy.getPosition().get().getX() * xScale );
        int y = (int)Math.round(enemy.getPosition().get().getY() * yScale );
        graphic.drawImage(asset, x, y, width, height, null);
    }

    private void renderSpell(final Graphics2D graphic, final Entity spell) {
        Image asset = null;
        final int h = 40;
        final int w = 40;
        final int x = (int)spell.getPosition().get().getX();
        final int y = (int)spell.getPosition().get().getY();
        switch (spell.getName()) {
            case FireBall.NAME:
                asset = this.fireballOnGround;
                break;
            case Arrows.NAME:
                asset = this.arrowsImage;
                break;
            default:
                break;
        }
        final int imageX = x-h/2;
        final int imageY = y-w/2;
        int width = (int) Math.round(h * xScale);
        int height = (int) Math.round(w * yScale);
        graphic.drawImage(asset, imageX, imageY, width, height,  null);
    }

    private void renderMap(final Graphics2D graphic) {
        graphic.drawImage(this.map, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
