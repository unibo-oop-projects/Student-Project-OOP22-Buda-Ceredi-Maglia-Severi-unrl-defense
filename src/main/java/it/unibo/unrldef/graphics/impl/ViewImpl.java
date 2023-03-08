package it.unibo.unrldef.graphics.impl;

import it.unibo.unrldef.graphics.api.View;
import it.unibo.unrldef.model.api.World;

public class ViewImpl implements View{

    private final GamePanel gamePanel;

    public ViewImpl(World world){
        this.gamePanel = new GamePanel(world);
    }

    @Override
    public void render() {
        this.gamePanel.repaint();
    }
    
}
