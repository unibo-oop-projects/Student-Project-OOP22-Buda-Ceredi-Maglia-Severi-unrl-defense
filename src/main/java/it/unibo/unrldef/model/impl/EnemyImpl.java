package it.unibo.unrldef.model.impl;

import java.util.Optional;

import it.unibo.unrldef.common.Pair;
import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.Enemy;
import it.unibo.unrldef.model.api.Entity;
import it.unibo.unrldef.model.api.Path;
import it.unibo.unrldef.model.api.World;

/**
 * Implementation of an Enemy in the game Unreal Defense
 * @author danilo.maglia@studio.unibo.it
 */
public class EnemyImpl extends Entity implements Enemy {
    private int health;
    private final int speed;
    private int currentDirectionIndex;
    private World parentWorld;
    private Pair<Path.Direction, Double> currentDirection;
    
    public EnemyImpl(final Optional<Position> position, final String name, final World parentWorld, final int startingHealth, final int speed) {
        super(position, name, parentWorld);
        this.health = startingHealth;
        this.speed = speed;
        this.currentDirectionIndex = 0;
        this.currentDirection = new Pair<Path.Direction,Double>(Path.Direction.DOWN, 0.0);

    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void reduceHealth(final int amount) {
        this.health -= amount;
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
    public void updateState() {
        if (this.getTimeSinceLastAction() >= 1000) {
            Path.Direction direction = this.currentDirection.getFirst();
            Double units = this.currentDirection.getSecond();

            if(units <= 0) {
                this.currentDirectionIndex++;
                this.currentDirection = this.parentWorld.getPath().getDirection(this.currentDirectionIndex);
                direction = this.currentDirection.getFirst();
                units = this.currentDirection.getSecond();
            }
            double x = this.getPosition().get().getX();
            double y = this.getPosition().get().getY();
            switch(direction) {
                case DOWN:
                    this.getPosition().get().setY(y + speed);
                    break;
                case UP:
                    this.getPosition().get().setY(y - speed);
                    break;
                case LEFT:
                    this.getPosition().get().setX(x - speed);
                    break;
                case RIGHT:
                    this.getPosition().get().setX(x + speed);
                    break;
                case END: 
                    //TODO: Enemy will deal damage to the player
                    break;
            }
            //TODO: the enemy needs to check if he will steps out of bound if the speed is greater than the remaining units
            this.currentDirection.setSecondElement(units - speed);
        }
        
    }

    @Override
    public Enemy copy() {
        return new EnemyImpl(this.getPosition(), this.getName(), this.getParentWorld(), health, speed);
    }
                
}
