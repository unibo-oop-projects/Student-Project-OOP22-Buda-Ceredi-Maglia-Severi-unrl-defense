package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Path;

/**
 * Implementation of an Enemy in the game Unreal Defense
 * @author danilo.maglia@studio.unibo.it
 */
public class EnemyImpl extends EntityImpl implements Enemy {
    private double health;
    private double speed;
    private double dropAmount;
    private final double DEFAULT_SPEED;
    private int currentDirectionIndex;
    private Pair<Path.Direction, Double> currentDirection;
    
    public EnemyImpl(final Optional<Position> position, final String name, final double startingHealth, final double speed, final double dropAmount) {
        super(position, name);
        this.health = startingHealth;
        this.speed = speed;
        this.dropAmount = dropAmount;
        this.DEFAULT_SPEED = speed;
        this.currentDirectionIndex = 0;
        this.currentDirection = new Pair<Path.Direction,Double>(Path.Direction.DOWN, 0.0);

    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getDropAmount() {
        return this.dropAmount;
    }

    @Override
    public void reduceHealth(final double amount) {
        this.health -= amount;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void resetSpeed() {
        this.speed = DEFAULT_SPEED;
    }

    @Override
    public boolean isDead() {
        return this.getHealth() <= 0;
    }

    @Override
    public boolean hasReachedEndOfPath() {
        return this.currentDirection.getFirst() == Path.Direction.END;
    }

    @Override
    public void updateState(long time) {
        if (!this.hasReachedEndOfPath()) {
            if (this.currentDirection.getSecond() <= 0) {
                this.currentDirection = this.getParentWorld().getPath().getDirection(this.currentDirectionIndex);
                this.currentDirectionIndex++;
            }
            this.move(time);
        }
        
    }

    @Override
    public void move(long time) {
        Path.Direction direction = this.currentDirection.getFirst();
        Double units = this.currentDirection.getSecond();
        double x = this.getPosition().get().getX();
        double y = this.getPosition().get().getY();
        double actualSpeed = this.speed * (time/1000.0);
        double stepSize = (units - actualSpeed) < 0 ? units : actualSpeed; // This prevents the enemy from stepping out of bounds
        switch(direction) {
            case DOWN:
                y += stepSize;
                break;
            case UP:
                y -= stepSize;
                break;
            case LEFT:
                x -= stepSize;
                break;
            case RIGHT:
                x += stepSize;
                break;
            default:
                break;
        }
        this.setPosition(x, y);
        this.currentDirection.setSecondElement(units - stepSize);
    }

    @Override
    public Enemy copy() {
        EnemyImpl enemy = new EnemyImpl(Optional.of(this.getPosition().get().copy()), this.getName(), this.health, this.speed, this.dropAmount);
        enemy.setParentWorld(this.getParentWorld());
        return enemy;
    }


                
}
