package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Tower;

public class CannonImpl implements Tower {
    
    final private int cost = 100;
    final private int attackSpeed = 3;
    final private String name = "Cannone dello sdrogo";
    final private Set<Position> cannonPosition;

    public int getCost() {
        return this.cost;
    }

    public int getAttackSpeed(){
        return this.attackSpeed;
    }

    public String getName(){
        return this.name;
    }

}
