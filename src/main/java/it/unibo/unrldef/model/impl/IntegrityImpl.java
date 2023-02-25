package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Integrity;


public class IntegrityImpl implements Integrity{

    private int value;

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void damage(int val) {
        int tmp = this.value-val;
        this.value = tmp > 0 ? tmp : 0;        
    }
    
}
