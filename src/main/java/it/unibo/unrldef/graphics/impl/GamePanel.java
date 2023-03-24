package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BasicStroke;

import it.unibo.unrldef.common.Pair;
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

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.GradientPaint;
import java.awt.Image;

public class GamePanel extends JPanel {
    private final int MAP_SIZE_IN_UNITS = 80;

    private String selectedEntity;

    private List<Position> towerAvailablePositions;

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
    private int xMapPosition = 0;
    private int yMapPosition = 0;
    private int mapSize = 0;
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 600;
    private Position mousePosition;

    private int towerSquareWidth = 50;
    private int towerSquareHeight = 50;

    private final JPanel panelRef;

    public enum ViewState {
        IDLE,
        TOWER_SELECTED,
        SPELL_SELECTED
    }

    public GamePanel(World gameWorld, Input inputHandler) {
        this.viewState = ViewState.IDLE;
        this.panelRef = this;


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
        this.towerAvailablePositions = new ArrayList<>();
        this.gameWorld = gameWorld;
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int tmp = Math.min(panelRef.getWidth(), panelRef.getHeight());
                xScale = (double)tmp / DEFAULT_WIDTH;
                yScale = (double)tmp / DEFAULT_HEIGHT;
                towerSquareWidth = (int)(30 * xScale);
                towerSquareHeight = (int)(30 * yScale);
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });


        MouseInputListener mouse = new MouseInputListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Position p = fromRealPositionToPosition(new Position(e.getX(),e.getY()));
                switch (viewState) {
                    case IDLE:
                        break;
                    case TOWER_SELECTED: 
                        towerAvailablePositions.stream()
                        .filter(towerSquare -> towerSquare.getX() - towerSquareWidth/2 < e.getX() && towerSquare.getX() + towerSquareWidth/2 > e.getX() && towerSquare.getY() - towerSquareHeight/2 < e.getY() && towerSquare.getY() + towerSquareHeight/2 > e.getY())
                        .findFirst()
                        .map(towerSquare -> fromRealPositionToPosition(towerSquare))
                        .ifPresent(modelP -> inputHandler.setLastHit((int)modelP.getX(), (int)modelP.getY(), Input.HitType.PLACE_TOWER, Optional.of(selectedEntity)));
                        break;
                    case SPELL_SELECTED:
                        inputHandler.setLastHit((int)p.getX(), (int)p.getY(), Input.HitType.PLACE_SPELL, Optional.of(selectedEntity));
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
            public void mouseMoved(MouseEvent e) { 
                mousePosition = new Position(e.getX(), e.getY());
                //System.out.println("New mouse position: " + mousePosition);
            }  
        };

        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    public void setState(ViewState state) {
        this.viewState = state;
    }

    public void setSelectedEntity(String entity) {
        this.selectedEntity = entity;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphic = (Graphics2D) g;
        Color a = new Color(23, 79, 120);
        Color b = new Color(21, 95,110);
        GradientPaint cp = new GradientPaint(0, this.getHeight(), a, this.getWidth(), 0, b);

       // graphic.setBackground(new java.awt.Color(22, 89, 114));
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphic.clearRect(0,0,this.getWidth(),this.getHeight());
        graphic.setPaint(cp);
        graphic.fillRect(0, 0, this.getWidth(), this.getHeight());
        // TODO: render the game world
        renderMap(graphic);

        // System.out.println("Entities:" + gameWorld.getSceneEntities());
        for (Entity entity : gameWorld.getSceneEntities()) {
            renderEntity(graphic, entity);
            // System.out.println("Entity: " + entity.getName() + " Pos X: " + entity.getPosition().get().getX() + " Pos Y: " + entity.getPosition().get().getY());
        }
        
        if(viewState == ViewState.TOWER_SELECTED) {
            // TODO: get available positions from world and render them as rectangles

            towerAvailablePositions.stream()
            .filter(towerSquare -> towerSquare.getX() - towerSquareWidth/2 < mousePosition.getX() && towerSquare.getX() + towerSquareWidth/2 > mousePosition.getX() && towerSquare.getY() - towerSquareHeight/2 < mousePosition.getY() && towerSquare.getY() + towerSquareHeight/2 > mousePosition.getY())
            .findFirst()
            .ifPresent(towerSquare -> {
                int radius = 0;
                final Position modelP = fromRealPositionToPosition(towerSquare);
                if (selectedEntity.equals(Hunter.NAME)) {
                    radius = (int)(Hunter.RADIOUS);
                } else if (selectedEntity.equals(Cannon.NAME)) {
                    radius = (int)(Cannon.RADIOUS);
                }
                if (radius != 0) {
                    graphic.setColor(java.awt.Color.GREEN);
                    final Position realPL = fromPositionToRealPosition(new Position(modelP.getX()-radius, modelP.getY()-radius));
                    final Position realPR = fromPositionToRealPosition(new Position(modelP.getX()+radius, modelP.getY()+radius));
                    graphic.drawOval((int)realPL.getX(), (int)realPL.getY(), (int)(realPR.getX()-realPL.getX()), (int)(realPR.getY()-realPL.getY()));
                }          
            });
                
            

            graphic.setColor(java.awt.Color.GREEN);
            for (Position p: this.towerAvailablePositions) {
                graphic.fillRect((int)p.getX()-this.towerSquareWidth/2, (int)p.getY()-this.towerSquareHeight/2, this.towerSquareWidth, this.towerSquareHeight);
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
        int h = 0;
        int w = 0;
        int width = 0;
        int height = 0;
        Position pos;
        final int hunterGap = 7;
        Optional<Enemy> target = ((Tower)tower).getTarget();
        final Position realTowerPosition = this.fromPositionToRealPosition(new Position(tower.getPosition().get().getX(), tower.getPosition().get().getY()-hunterGap));
        final Position realTargetPosition = target.isPresent() ? this.fromPositionToRealPosition(target.get().getPosition().get()) : null;
        switch(tower.getName()) {
            case Cannon.NAME:
                w=100;
                h=71;
                width = (int)(w * yScale);
                height = (int)(h * xScale);
                if (target.isPresent()) {
                    towerAsset = shootingCannon;
                } else {
                    towerAsset = cannonImage;
                }
                pos = this.fromPositionToRealPosition(tower.getPosition().get());
                graphic.drawImage(towerAsset, (int)pos.getX()-width/2, (int)pos.getY()-height/2, width, height, null);
                break;
            case Hunter.NAME:
                h=100;
                w=75;
                width = (int)(w * yScale);
                height = (int)(h * xScale);
                if (target.isPresent()) {
                    towerAsset = hunterImage;
                    graphic.setColor(Color.BLUE);
                    graphic.setStroke(new BasicStroke(5));
                    //System.out.println("Drawing line from " + realTowerPosition + " to " + realTargetPosition);
                    graphic.drawLine((int)realTowerPosition.getX(), (int)realTowerPosition.getY(), 
                            (int)realTargetPosition.getX(), (int)realTargetPosition.getY());
                    graphic.setStroke(new BasicStroke(1));
                    graphic.setColor(Color.BLACK);
                } else {
                    towerAsset = hunterImage;
                }
                pos = this.fromPositionToRealPosition(tower.getPosition().get());
                graphic.drawImage(towerAsset, (int)pos.getX()-width/2, (int)(pos.getY()-height/2)-20, width, height, null);
                break;
            default:
                break;
        }

    }

    private void renderEnemy(Graphics2D graphic, Entity enemy) {
        Image asset = null;
        final int h = 40;
        final int w = 30;

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

        int width = (int)(w* yScale);
        int height = (int)(h * xScale);
        Position pos = this.fromPositionToRealPosition(enemy.getPosition().get());
        int x = ((int)pos.getX()) - height/2;
        int y = ((int)pos.getY()) - width/2;
        graphic.drawImage(asset,(int) x, y, width, height, null);
    }

    private void renderSpell(final Graphics2D graphic, final Entity spell) {
        Image asset = null;
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
        final Position pos = spell.getPosition().get();
        final double radius = ((Spell)spell).getRadius();
        final Position realPos1 = this.fromPositionToRealPosition(new Position(pos.getX()-radius, pos.getY()-radius));
        final Position realPos2 = this.fromPositionToRealPosition(new Position(pos.getX()+radius, pos.getY()+radius));
        graphic.drawImage(asset, (int)realPos1.getX(), (int)realPos1.getY(), (int)(realPos2.getX()-realPos1.getX()), 
                (int)(realPos2.getY()-realPos1.getY()) , null);
    }

    private void renderMap(final Graphics2D graphic) {
        this.mapSize = Math.min(getWidth(), getHeight());
        this.xMapPosition = (getWidth() - this.mapSize) / 2;
        this.yMapPosition = (getHeight() - this.mapSize) / 2;
        this.towerAvailablePositions.clear();
        this.towerAvailablePositions = this.gameWorld.getAvailablePositions().stream().map((p) -> this.fromPositionToRealPosition(p)).collect(Collectors.toList());
        graphic.drawImage(this.map, this.xMapPosition, this.yMapPosition, this.mapSize, this.mapSize, null);
    }

    private Position fromPositionToRealPosition(Position pos) {
        double newX = (pos.getX() * this.mapSize) / this.MAP_SIZE_IN_UNITS + this.xMapPosition;
        double newY = (pos.getY() * this.mapSize) / this.MAP_SIZE_IN_UNITS + this.yMapPosition;
        Position panelPosition = new Position(newX, newY);
        return panelPosition;
    }

    private Position fromRealPositionToPosition(Position pos) {
        double newX = (this.MAP_SIZE_IN_UNITS*(pos.getX() - this.xMapPosition))/this.mapSize;
        double newY = (this.MAP_SIZE_IN_UNITS*(pos.getY() - this.yMapPosition))/this.mapSize;
        Position panelPosition = new Position(newX, newY);
        return panelPosition;
    }
}
