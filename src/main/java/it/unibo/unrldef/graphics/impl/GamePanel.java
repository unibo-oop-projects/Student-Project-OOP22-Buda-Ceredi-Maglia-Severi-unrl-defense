package it.unibo.unrldef.graphics.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.input.api.Input;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Spell;
import it.unibo.unrldef.model.api.Tower;
import it.unibo.unrldef.model.api.World;
import it.unibo.unrldef.model.impl.Cannon;
import it.unibo.unrldef.model.impl.FireBall;
import it.unibo.unrldef.model.impl.Goblin;
import it.unibo.unrldef.model.impl.Hunter;
import it.unibo.unrldef.model.impl.Orc;
import it.unibo.unrldef.model.impl.SnowStorm;

/**
 * 
 * This is the game panel where game gets rendered on the screen.
 * 
 * @author danilo.maglia@studio.unibo.it
 * @author tommaso.ceredi@studio.unibo.it
 * @author tommaso.severi2@studio.unibo.it
 * @author francesco.buda3@studio.unibo.it
 */
public final class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Each state in which the game can be.
     * 
     * @author danilo.maglia@studio.unibo.it
     */
    public enum ViewState {
        /**
         * The game is idle.
         */
        IDLE,
        /**
         * The user is placing a tower.
         */
        TOWER_SELECTED,
        /**
         * The user is placing a spell.
         */
        SPELL_SELECTED
    }

    private static final int MAP_WIDTH_IN_UNITS = 80;
    private static final int MAP_HEIGHT_IN_UNITS = 80;
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;
    private static final long TOWER_ANIMATION_LENGTH = 500;

    private static final String ASSETS_FOLDER = "assets" + File.separator;
    private String selectedEntity;
    private final Set<Position> towerAvailablePositions;
    private final World gameWorld;
    private ViewState viewState;
    private transient Sprite orc;
    private transient Sprite goblin;
    private transient Sprite fireball;
    private transient Sprite snowStorm;
    private transient Sprite map;
    private transient Sprite cannon;
    private transient Sprite hunter;
    private transient Sprite shootingCannon;
    private transient Sprite explosion;
    private transient Sprite shootingHunter;
    private transient Sprite towerPlace;
    private transient Sprite heart;
    private transient Sprite money;
    private final Set<Sprite> sprites = new HashSet<>();
    private double xScale;
    private double yScale;

    private int xMapPosition;
    private int yMapPosition;

    private transient Position mousePosition;

    private final JPanel panelRef;

    private final Map<Entity, SpriteAnimation> animationMap;

    /**
     * 
     * @param gameWorld    the game world
     * @param inputHandler the input handler
     */
    public GamePanel(final World gameWorld, final Input inputHandler) {
        this.viewState = ViewState.IDLE;
        this.panelRef = this;
        this.mousePosition = new Position(0, 0);
        try {
            this.map = new Sprite(SpriteDimensions.MAP_WIDTH, SpriteDimensions.MAP_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "firstMap.png")));
            this.fireball = new Sprite(SpriteDimensions.FIREBALL_WIDHT, SpriteDimensions.FIREBALL_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "fireball.png")));
            this.sprites.add(this.fireball);
            this.snowStorm = new Sprite(SpriteDimensions.SNOWSTORM_WIDTH, SpriteDimensions.SNOWSTORM_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "snowstorm.png")));
            this.sprites.add(this.snowStorm);
            this.orc = new Sprite(SpriteDimensions.ORC_WIDTH, SpriteDimensions.ORC_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "orc.png")));
            this.sprites.add(this.orc);
            this.goblin = new Sprite(SpriteDimensions.GOBLIN_WIDTH, SpriteDimensions.GOBLIN_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "goblin.png")));
            this.sprites.add(this.goblin);
            this.cannon = new Sprite(SpriteDimensions.CANNON_WIDTH, SpriteDimensions.CANNON_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "cannon.png")));
            this.sprites.add(this.cannon);
            this.hunter = new Sprite(SpriteDimensions.HUNTER_WIDTH, SpriteDimensions.HUNTER_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "Hunter.png")));
            this.sprites.add(this.hunter);
            this.shootingCannon = new Sprite(SpriteDimensions.CANNON_WIDTH, SpriteDimensions.CANNON_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "shootingCannon.png")));
            this.sprites.add(this.shootingCannon);
            this.explosion = new Sprite(SpriteDimensions.EXPLOSION_WIDTH, SpriteDimensions.EXPLOSION_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "explosion.png")));
            this.sprites.add(this.explosion);
            this.shootingHunter = new Sprite(SpriteDimensions.HUNTER_WIDTH, SpriteDimensions.HUNTER_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "shootingHunter.png")));
            this.sprites.add(this.shootingHunter);
            this.towerPlace = new Sprite(SpriteDimensions.TOWER_PLACE_WIDTH, SpriteDimensions.TOWER_PLACE_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "towerPlace.png")));
            this.sprites.add(this.towerPlace);
            this.heart = new Sprite(SpriteDimensions.HEART_WIDTH, SpriteDimensions.HEART_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "heart.png")));
            this.sprites.add(this.heart);
            this.money = new Sprite(SpriteDimensions.MONEY_WIDTH, SpriteDimensions.MONEY_HEIGHT,
                    ImageIO.read(new File(ASSETS_FOLDER + "money.png")));
            this.sprites.add(this.money);

        } catch (final IOException e) {
            new ErrorDialog("error loading assets");
        }

        this.animationMap = new HashMap<>();
        this.gameWorld = gameWorld;
        this.towerAvailablePositions = new HashSet<>(this.gameWorld.getAvailablePositions());
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.scaleAll(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleAll(panelRef.getWidth(), panelRef.getHeight());
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
            }

            @Override
            public void componentShown(final ComponentEvent e) {
            }

            @Override
            public void componentHidden(final ComponentEvent e) {
            }
        });

        final MouseInputListener mouse = new MouseInputListener() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                final Position pView = new Position(e.getX(), e.getY());
                final Position pModel = fromRealPositionToPosition(pView);
                switch (viewState) {
                    case TOWER_SELECTED:
                        final int towerWidth = towerPlace.getScaledDimension().getFirst();
                        final int towerHeight = towerPlace.getScaledDimension().getSecond();
                        towerAvailablePositions.stream()
                                .filter(pos -> {
                                    final Position realPos = fromPositionToRealPosition(pos);
                                    return isInSquare(pView,
                                            new Position(realPos.getX() - towerWidth / 2,
                                                    realPos.getY() - towerHeight / 2),
                                            new Position(realPos.getX() + towerWidth / 2,
                                                    realPos.getY() + towerHeight / 2));
                                })
                                .findFirst().ifPresent(modelP -> {
                                    towerAvailablePositions.remove(modelP);
                                    inputHandler.setLastHit((int) modelP.getX(), (int) modelP.getY(),
                                            Input.HitType.PLACE_TOWER, Optional.of(selectedEntity));
                                });
                        break;
                    case SPELL_SELECTED:
                        inputHandler.setLastHit((int) pModel.getX(), (int) pModel.getY(), Input.HitType.PLACE_SPELL,
                                Optional.of(selectedEntity));
                        break;
                    default:
                        break;
                }
                viewState = ViewState.IDLE; // reset the view state every time the mouse is clicked
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }

            @Override
            public void mouseDragged(final MouseEvent e) {
            }

            @Override
            public void mouseMoved(final MouseEvent e) {
                mousePosition = new Position(e.getX(), e.getY());
            }
        };

        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    /**
     * Set the state of the panel.
     * 
     * @param state the state to set
     */
    public void setState(final ViewState state) {
        this.viewState = state;
    }

    /**
     * Set the selected entity.
     * 
     * @param entity the entity to set
     */
    public void setSelectedEntity(final String entity) {
        this.selectedEntity = entity;
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        final Graphics2D graphic = (Graphics2D) g;
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphic.clearRect(0, 0, this.getWidth(), this.getHeight());

        this.renderBackground(graphic);
        this.renderMap(graphic);
        this.renderHearts(graphic);
        this.renderMoney(graphic);

        for (final Entity entity : gameWorld.getSceneEntities()) {
            this.renderEntity(graphic, entity);
        }

        switch (viewState) {
            case TOWER_SELECTED:
                this.renderTowersSquare(graphic);
                break;
            case SPELL_SELECTED:
                this.renderSpellMouseRange(graphic);
                break;
            default:
                break;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void renderBackground(final Graphics2D graphic) {
        final Color firstColor = new Color(23, 79, 120);
        final Color secondColor = new Color(21, 95, 110);
        final GradientPaint cp = new GradientPaint(0, this.getHeight(), firstColor, this.getWidth(), 0, secondColor);
        graphic.setPaint(cp);
        graphic.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphic.setColor(Color.BLACK);
    }

    private void renderTowersSquare(final Graphics2D graphic) {
        final int towerWidth = towerPlace.getScaledDimension().getFirst();
        final int towerHeight = towerPlace.getScaledDimension().getSecond();
        towerAvailablePositions.stream()
                .map(x -> this.fromPositionToRealPosition(x))
                .filter(pos -> this.isInSquare(mousePosition,
                        new Position(pos.getX() - towerPlace.getScaledDimension().getFirst() / 2,
                                pos.getY() - towerWidth / 2),
                        new Position(pos.getX() + towerPlace.getScaledDimension().getFirst() / 2,
                                pos.getY() + towerHeight / 2)))
                .findFirst()
                .ifPresent(towerSquare -> {
                    int radius = 0;
                    final Position modelP = fromRealPositionToPosition(towerSquare);
                    if (Hunter.NAME.equals(selectedEntity)) {
                        radius = (int) (Hunter.RADIOUS);
                    } else if (Cannon.NAME.equals(selectedEntity)) {
                        radius = (int) (Cannon.RADIOUS);
                    }
                    if (radius != 0) {
                        graphic.setColor(Color.GREEN);
                        final Position realPL = fromPositionToRealPosition(
                                new Position(modelP.getX() - radius, modelP.getY() - radius));
                        final Position realPR = fromPositionToRealPosition(
                                new Position(modelP.getX() + radius, modelP.getY() + radius));
                        graphic.drawOval((int) realPL.getX(), (int) realPL.getY(),
                                (int) (realPR.getX() - realPL.getX()), (int) (realPR.getY() - realPL.getY()));
                    }
                });
        for (final Position p : this.towerAvailablePositions) {
            final Position pos = this.towerPlace.getApplicationPoint(p);
            final Position realPos = fromPositionToRealPosition(pos);
            graphic.drawImage(this.towerPlace.getScaledSprite(), (int) realPos.getX(), (int) realPos.getY(), null);
        }
        graphic.setColor(Color.BLACK);
    }

    private void renderSpellMouseRange(final Graphics2D graphic) {
        if (this.mousePosition.getY() < this.getHeight() - 2 && this.mousePosition.getX() < this.getWidth() - 2
                && this.mousePosition.getY() > 0 && this.mousePosition.getX() > 0) {
            Sprite asset = new Sprite(0, 0, null);
            switch (selectedEntity) {
                case FireBall.NAME:
                    asset = this.fireball;
                    break;
                case SnowStorm.NAME:
                    asset = this.snowStorm;
                    break;
                default:
                    break;
            }
            final Position mPos = this.fromRealPositionToPosition(this.mousePosition);
            final Position realPos = this.fromPositionToRealPosition(asset.getApplicationPoint(mPos));
            graphic.drawImage(asset.getScaledSprite(), (int) realPos.getX(), (int) realPos.getY(), null);
        }
    }

    private void renderEntity(final Graphics2D graphic, final Entity entity) {
        if (entity instanceof Enemy) {
            this.renderEnemy(graphic, entity);
        } else if (entity instanceof Spell) {
            this.renderSpell(graphic, entity);
        } else if (entity instanceof Tower) {
            this.renderTower(graphic, entity);
        }
    }

    private void renderTower(final Graphics2D graphic, final Entity tower) {
        Sprite towerAsset = new Sprite(0, 0, null);
        final int electrodeHeight = 4;
        final Optional<Enemy> target = ((Tower) tower).getTarget();
        final Position rayStartPos = this.fromPositionToRealPosition(new Position(
                tower.getPosition().get().getX(), tower.getPosition().get().getY() - electrodeHeight));
        this.animationMap.putIfAbsent(tower, new SpriteAnimation(TOWER_ANIMATION_LENGTH));
        final SpriteAnimation animation = this.animationMap.get(tower);
        if (target.isPresent()) {
            animation.startAnimation(System.currentTimeMillis(), target.get());
        }
        // switch for the tower asset
        switch (tower.getName()) {
            case Cannon.NAME:
                towerAsset = animation.isAnimationRunning() ? this.shootingCannon : this.cannon;
                break;
            case Hunter.NAME:
                towerAsset = animation.isAnimationRunning() ? this.shootingHunter : this.hunter;
                break;
            default:
                break;
        }

        if (animation.isAnimationRunning() && animation.getTarget().isDead()) {
            renderEnemy(graphic, animation.getTarget());
        }
        final Position towerPos = tower.getPosition().get();
        final Position realTowerPos = fromPositionToRealPosition(towerAsset.getApplicationPoint(towerPos));
        graphic.drawImage(towerAsset.getScaledSprite(), (int) realTowerPos.getX(), (int) realTowerPos.getY(), null);
        // switch for the tower attack animation
        switch (tower.getName()) {
            case Hunter.NAME:
                final int strokeWidth = 5;
                if (animation.isAnimationRunning()) {
                    final Position realTargetPosition = this
                            .fromPositionToRealPosition(animation.getTarget().getPosition().get());
                    graphic.setColor(Color.BLUE);
                    graphic.setStroke(new BasicStroke(strokeWidth));
                    graphic.drawLine((int) rayStartPos.getX(), (int) rayStartPos.getY(),
                            (int) realTargetPosition.getX(), (int) realTargetPosition.getY());
                    graphic.setStroke(new BasicStroke(1));
                    graphic.setColor(Color.BLACK);
                    animation.updateTimePassed();
                } else {
                    animation.resetAnimation();
                }
                break;
            case Cannon.NAME:
                if (animation.isAnimationRunning()) {
                    final Position explosionPos = this.fromPositionToRealPosition(
                            this.explosion.getApplicationPoint(animation.getStartTargetPosition()));
                    graphic.drawImage(this.explosion.getScaledSprite(), (int) explosionPos.getX(),
                            (int) explosionPos.getY(), null);
                    animation.updateTimePassed();
                } else {
                    animation.resetAnimation();
                }
                break;
            default:
                break;
        }
    }

    private void renderEnemy(final Graphics2D graphic, final Entity enemy) {
        final Enemy e = (Enemy) enemy;
        double startingHealth = 0;
        Sprite asset = new Sprite(0, 0, null);
        switch (e.getName()) {
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
        final double healthPercentage = e.getHealth() / startingHealth;
        final int width = asset.getScaledDimension().getFirst();
        final Position pos = enemy.getPosition().get();
        final Position realPos = this.fromPositionToRealPosition(asset.getApplicationPoint(pos));
        final int x = (int) realPos.getX();
        final int y = (int) realPos.getY();
        final int healthBarY = (int) (y - 1 * yScale);
        final int barHeight = 5;
        graphic.drawImage(asset.getScaledSprite(), (int) x, y, null);
        graphic.setColor(Color.RED);

        graphic.fillRect(x, healthBarY, width, barHeight);
        graphic.setColor(Color.GREEN);

        graphic.fillRect(x, healthBarY, (int) (width * healthPercentage), barHeight);
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
        graphic.drawImage(asset.getScaledSprite(), (int) realPos.getX(), (int) realPos.getY(), null);
    }

    private void renderHearts(final Graphics2D graphic) {
        final int n = this.gameWorld.getCastleIntegrity().getHearts();
        final int denominator = 4;
        final int startX = this.xMapPosition + this.heart.getScaledDimension().getFirst() / denominator;
        final int startY = this.yMapPosition + this.heart.getScaledDimension().getSecond() / denominator;
        for (int i = 0; i < n; i++) {
            graphic.drawImage(this.heart.getScaledSprite(),
                    startX + i * this.heart.getScaledDimension().getFirst(), startY, null);
        }
    }

    private void renderMoney(final Graphics2D graphic) {
        final int denominator = 6;
        final int startX = this.xMapPosition + this.money.getScaledDimension().getFirst() / 4;
        final int startY = this.yMapPosition + this.money.getScaledDimension().getSecond() * 4 / 3;
        graphic.drawImage(this.money.getScaledSprite(), startX, startY, null);
        graphic.setFont(new Font("", Font.PLAIN, this.money.getScaledDimension().getSecond() / 2));
        graphic.drawString(" " + this.gameWorld.getMoney() + " ",
                startX + this.money.getScaledDimension().getFirst(),
                startY + this.money.getScaledDimension().getSecond() * 4 / denominator);
    }

    private void renderMap(final Graphics2D graphic) {
        graphic.drawImage(this.map.getScaledSprite(), this.xMapPosition, this.yMapPosition, null);
    }

    private Position fromPositionToRealPosition(final Position pos) {
        final double newX = pos.getX() * this.xScale + this.xMapPosition;
        final double newY = pos.getY() * this.yScale + this.yMapPosition;
        return new Position(newX, newY);
    }

    private Position fromRealPositionToPosition(final Position pos) {
        final double newX = ((pos.getX() - this.xMapPosition)) / xScale;
        final double newY = ((pos.getY() - this.yMapPosition)) / yScale;
        return new Position(newX, newY);
    }

    private void scaleAll(final int realWidth, final int realHeight) {
        double width;
        double height;

        width = realWidth;
        height = width * MAP_HEIGHT_IN_UNITS / MAP_WIDTH_IN_UNITS;
        xMapPosition = 0;
        yMapPosition = (int) Math.floor((realHeight - height) / 2);
        if (height > realHeight) {
            height = realHeight;
            width = height * MAP_WIDTH_IN_UNITS / MAP_HEIGHT_IN_UNITS;
            yMapPosition = 0;
            xMapPosition = (int) Math.floor((realWidth - width) / 2);
        }

        xScale = width / MAP_WIDTH_IN_UNITS;
        yScale = height / MAP_HEIGHT_IN_UNITS;

        map.scale(xScale, yScale);
        sprites.forEach(x -> x.scale(xScale, yScale));
    }

    private boolean isInSquare(final Position pos, final Position upLeft, final Position downRight) {
        return pos.getX() >= upLeft.getX()
                && pos.getX() <= downRight.getX()
                && pos.getY() <= downRight.getY()
                && pos.getY() >= upLeft.getY();
    }
}
