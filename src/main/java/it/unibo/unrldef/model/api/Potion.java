package it.unibo.unrldef.model.api;

public interface Potion {
    
    public String getName();

    public Double getDamagePerTick();

    public Integer getRadius();

    public Double getRechargeState();

    public boolean isReady();

    public void updateState();
}
