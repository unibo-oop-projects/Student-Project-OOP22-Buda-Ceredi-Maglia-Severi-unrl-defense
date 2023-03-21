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
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BasicStroke;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Orc;
import it.unibo.unrldef.model.impl.SnowStorm;
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
    private Image fireball;
    private Image iceSpell;
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
        this.viewState = ViewState.IDLE;
        //TODO: load assets
        try {
            this.fireball = ImageIO.read(new File("assets"+File.separator+"fireball.png"));
            this.iceSpell = ImageIO.read(new File("assets"+File.separator+"snowStorm.png"));
            this.orcImage = ImageIO.read(new File("assets"+File.separator+"orc.png"));
            this.goblinImage = ImageIO.read(new File("assets"+File.separator+"goblin.png"));
            this.map = ImageIO.read(new File("assets"+File.separator+"debugMap.png")).getScaledInstance(DEFAULT_WIDTH, DEFAULT_HEIGHT, java.awt.Image.SCALE_SMOOTH);
            this.hunterImage = ImageIO.read(new File("assets"+File.separator+"Hunter.png"));
            this.cannonImage = ImageIO.read(new File("assets"+File.separator+"cannon.png"));
            this.shootingCannon = ImageIO.read(new File("assets"+File.separator+"shootingCannon.png"));
            this.shootingHunter = ImageIO.read(new File("assets"+File.separator+"shootingHunter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameWorld = gameWorld;

        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
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
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseDragged(MouseEvent e) { }

            @Override
            public void mouseMoved(MouseEvent e) { }
            
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
                    // render
                    graphic.setColor(Color.RED);
                    graphic.setStroke(new BasicStroke(20));
                    graphic.drawLine((int)tower.getPosition().get().getX(), (int)tower.getPosition().get().getY(), (int)target.get().getPosition().get().getX(), (int)target.get().getPosition().get().getY());
                } else {
                    towerAsset = cannonImage;
                }
                break;
            case Hunter.NAME:
                if (target.isPresent()) {
                    towerAsset = shootingHunter;
                } else {
                    towerAsset = hunterImage;
                }
                break;
            default:
                break;
        }
        int width = (int) Math.round(100 * xScale);
        int height = (int) Math.round(100 * yScale);
        graphic.drawImage(towerAsset, (int)tower.getPosition().get().getX(), (int)tower.getPosition().get().getY(), width, height, null);
    }

    private void renderEnemy(Graphics2D graphic, Entity enemy) {
        Image asset = null;
        final int h = 40;
        final int w = 40;

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
        int width = (int) Math.round(h * xScale);
        int height = (int) Math.round(w * yScale);
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
                asset = this.fireball;
                break;
            case SnowStorm.NAME:
                asset = this.iceSpell;
                break;
            default:
                break;
        }
        final int imageX = x-w/2;
        final int imageY = y-h/2;
        int width = (int) Math.round(w * xScale);
        int height = (int) Math.round(h * yScale);
        graphic.drawImage(asset, imageX, imageY, width, height,  null);
    }

    private void renderMap(final Graphics2D graphic) {
        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        graphic.drawImage(this.map, x, y, size, size, null);
    }
}
