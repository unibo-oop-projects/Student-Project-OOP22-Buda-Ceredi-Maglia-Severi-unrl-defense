package it.unibo.unrldef.model.impl;

import java.util.Set;

import it.unibo.unrldef.common.Position;
import it.unibo.unrldef.model.api.DefenseEntity;
import it.unibo.unrldef.model.api.Potion;
import it.unibo.unrldef.model.api.Tower;

/**
 * 
 */
public class Hunter extends DefenseEntity implements Tower {

    public Hunter() {
        super(null, "Arcere dello sdrogo", 20, 100);
    }

    @Override
    public void updateState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    }
    
}
