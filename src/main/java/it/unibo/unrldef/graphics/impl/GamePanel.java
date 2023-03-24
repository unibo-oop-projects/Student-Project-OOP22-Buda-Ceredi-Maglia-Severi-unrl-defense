package it.unibo.unrldef.graphics.impl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.lang.Math;

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

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.GradientPaint;

public class GamePanel extends JPanel {
    private final int MAP_WIDTH_IN_UNITS = 80;
    private final int MAP_HEIGHT_IN_UNITS = 80;

    private String selectedEntity;

    private List<Position> towerAvailablePositions;

    private World gameWorld;
    private ViewState viewState;
    
    private Sprite orc;
    private Sprite goblin;
    private Sprite fireball;
    private Sprite snowStorm;
    private Sprite map;
    private Sprite cannon;
    private Sprite hunter;
    private Sprite shootingCannon;
    private Sprite explosion;
    private Sprite shootingHunter;
    private Set<Sprite> sprites = new HashSet<>();
    private double xScale;
    private double yScale;
    private int xMapPosition = 0;
    private int yMapPosition = 0;
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
        this.mousePosition = new Position(0, 0);
        try {
            this.map = new Sprite(80, 80, ImageIO.read(new File("assets"+File.separator+"firstMap.png")));
            this.fireball = new Sprite(8, 8, ImageIO.read(new File("assets"+File.separator+"fireball.png")));
            this.sprites.add(this.fireball);
            this.snowStorm = new Sprite( 14, 14, ImageIO.read(new File("assets"+File.separator+"snowStorm.png")));
            this.sprites.add(this.snowStorm);
            this.orc = new Sprite(6, 6, ImageIO.read(new File("assets"+File.separator+"orc.png")));
            this.sprites.add(this.orc);
            this.goblin = new Sprite(4, 5, ImageIO.read(new File("assets"+File.separator+"goblin.png")));
            this.sprites.add(this.goblin);
            this.hunter = new Sprite(9, 12, ImageIO.read(new File("assets"+File.separator+"Hunter.png")));
            this.sprites.add(this.hunter);
            this.cannon = new Sprite(14, 14, ImageIO.read(new File("assets"+File.separator+"cannon.png")));
            this.sprites.add(this.cannon);
            this.shootingCannon = new Sprite(14, 14, ImageIO.read(new File("assets"+File.separator+"shootingCannon.png")));
            this.sprites.add(this.shootingCannon);
            this.shootingHunter = new Sprite(9, 12, ImageIO.read(new File("assets"+File.separator+"shootingHunter.png")));
            this.sprites.add(this.shootingHunter);
            this.explosion = new Sprite(8, 4, ImageIO.read(new File("assets"+File.separator+"explosion.png")));
            this.sprites.add(this.explosion);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.towerAvailablePositions = new ArrayList<>();
        this.gameWorld = gameWorld;
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.scaleAll(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleAll(panelRef.getWidth(), panelRef.getHeight());
                //scaleAll(panelRef.getSize().width, panelRef.getSize().height);
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
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphic.clearRect(0,0,this.getWidth(),this.getHeight());
        graphic.setPaint(cp);
        graphic.fillRect(0, 0, this.getWidth(), this.getHeight());
        renderMap(graphic);

        for (Entity entity : gameWorld.getSceneEntities()) {
            renderEntity(graphic, entity);
        }
        
        switch(viewState) {
            case TOWER_SELECTED:
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
                break;
            case SPELL_SELECTED:
                if (this.mousePosition.getY() < this.getHeight()-2 && this.mousePosition.getX() < this.getWidth()-2
                        && this.mousePosition.getY() > 0 && this.mousePosition.getX() > 0) {
                    System.out.println(this.mousePosition.getX()+" "+ this.mousePosition.getY());
                    Sprite asset = new Sprite(0, 0, null);
                    switch (selectedEntity) {
                        case FireBall.NAME:
                            asset = this.fireball;
                            break;
                        case SnowStorm.NAME:
                            asset = this.snowStorm;
                            break;
                    }
                    final Position mPos = this.fromRealPositionToPosition(this.mousePosition);
                    final Position realPos = this.fromPositionToRealPosition(asset.getApplicationPoint(mPos));
                    graphic.drawImage(asset.getScaledSprite(), (int)realPos.getX(), (int)realPos.getY() , null);
                }
                break;
            case IDLE:
                break;
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
        Sprite towerAsset = new Sprite(0, 0, null);
        final int electrodeHeight = 4;
        Optional<Enemy> target = ((Tower)tower).getTarget();
        final Position rayStartPos = this.fromPositionToRealPosition(new Position(tower.getPosition().get().getX(), tower.getPosition().get().getY()-electrodeHeight));
        Position realTargetPosition = new Position(0, 0);
        
        switch(tower.getName()) {
            case Cannon.NAME:
                if (target.isPresent()) {
                    towerAsset = shootingCannon;
                } else {
                    towerAsset = cannon;
                }
                break;
            case Hunter.NAME:
                if (target.isPresent()) {
                    realTargetPosition = this.fromPositionToRealPosition(target.get().getPosition().get());
                    towerAsset = shootingHunter;
                    graphic.setColor(Color.BLUE);
                    graphic.setStroke(new BasicStroke(5));
                    graphic.drawLine((int)rayStartPos.getX(), (int)rayStartPos.getY(), 
                            (int)realTargetPosition.getX(), (int)realTargetPosition.getY());
                    graphic.setStroke(new BasicStroke(1));
                    graphic.setColor(Color.BLACK);
                } else {
                    towerAsset = hunter;
                }
                break;
            default:
                break;
        }
        Position pos = tower.getPosition().get();
        Position realPos = fromPositionToRealPosition(towerAsset.getApplicationPoint(pos));
        graphic.drawImage(towerAsset.getScaledSprite(), (int) realPos.getX(), (int) realPos.getY(), null);
    }

    private void renderEnemy(Graphics2D graphic, Entity enemy) {
        Enemy e = (Enemy)enemy;
        double startingHealth = 0;
        Sprite asset = new Sprite(0, 0, null);
        switch(e.getName()) {
            case Orc.NAME:
                asset = orc;
                startingHealth = Orc.HEALTH;
                break;
            case Goblin.NAME:
                asset = goblin;
                startingHealth = Goblin.HEALTH;
                break;
            default:
                break;
        }
        double healthPercentage = e.getHealth() / startingHealth;
        int width = asset.getScaledDimension().getFirst();
        Position pos = enemy.getPosition().get();
        Position realPos = this.fromPositionToRealPosition(asset.getApplicationPoint(pos));
        int x = (int)realPos.getX();
        int y = (int)realPos.getY();
        int healthBarY = (int)(y - 1*yScale);
        graphic.drawImage(asset.getScaledSprite(),(int) x, y, null);
        graphic.setColor(Color.RED);
        
        graphic.fillRect(x, healthBarY, width, 5);
        graphic.setColor(Color.GREEN);
        
        graphic.fillRect(x, healthBarY, (int)(width*healthPercentage), 5);
    }

    private void renderSpell(final Graphics2D graphic, final Entity spell) {
        Sprite asset = new Sprite(0, 0, null);
        switch (spell.getName()) {
            case FireBall.NAME:
                asset = this.fireball;
                break;
            case SnowStorm.NAME:
                asset = this.snowStorm;
                break;
            default:
                break;
        }
        final Position pos = spell.getPosition().get();
        final Position realPos = this.fromPositionToRealPosition(asset.getApplicationPoint(pos));
        graphic.drawImage(asset.getScaledSprite(), (int)realPos.getX(), (int)realPos.getY() , null);
    }

    private void renderMap(final Graphics2D graphic) {
        this.towerAvailablePositions.clear();
        this.towerAvailablePositions = this.gameWorld.getAvailablePositions().stream().map((p) -> this.fromPositionToRealPosition(p)).collect(Collectors.toList());
        graphic.drawImage(this.map.getScaledSprite(), this.xMapPosition, this.yMapPosition, null);
    }

    private Position fromPositionToRealPosition(Position pos) {
        double newX = (pos.getX() * this.xScale) + this.xMapPosition;
        double newY = (pos.getY() * this.yScale) + this.yMapPosition;
        Position panelPosition = new Position(newX, newY);
        return panelPosition;
    }

    private Position fromRealPositionToPosition(Position pos) {
        double newX = ((pos.getX() - this.xMapPosition))/xScale;
        double newY = ((pos.getY() - this.yMapPosition))/yScale;
        Position panelPosition = new Position(newX, newY);
        return panelPosition;
    }

    private void scaleAll(int realWidth, int realHeight) {
        double width;
        double height;

        width = realWidth;
        height = (width * MAP_HEIGHT_IN_UNITS / MAP_WIDTH_IN_UNITS);
        xMapPosition = 0;
        yMapPosition = (int) Math.floor((realHeight - height)/2);
        if (height > realHeight) {
            height = realHeight;
            width = (height * MAP_WIDTH_IN_UNITS / MAP_HEIGHT_IN_UNITS);
            yMapPosition = 0;
            xMapPosition = (int) Math.floor((realWidth - width)/2);
        }
        
        xScale = (width / MAP_WIDTH_IN_UNITS);
        yScale = (height / MAP_HEIGHT_IN_UNITS);


        map.scale(xScale, yScale);
        sprites.forEach(x -> x.scale(xScale, yScale));
    }
}
