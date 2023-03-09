package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Integrity;


public class IntegrityImpl implements Integrity{

    private int hearts;

    public IntegrityImpl(int value) {
        this.hearts = value;
    }

    @Override
    public int getHearts() {
        return this.hearts;
    }

    @Override
    public void damage(int val) {
        int tmp = this.hearts-val;
        this.hearts = tmp > 0 ? tmp : 0;        
    }

    @Override
    public Boolean isCompromised() {
        return this.hearts == 0;
    }
    
}
