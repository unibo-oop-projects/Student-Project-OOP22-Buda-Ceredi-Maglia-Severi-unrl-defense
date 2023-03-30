package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Bank;

/**
 * the bank for the game Unreal Defense.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public final class BankImpl implements Bank {

    private double money;

    /**
     * the constructor.
     * 
     * @param startingMoney
     */
    public BankImpl(final double startingMoney) {
        this.money = startingMoney;
    }

    @Override
    public void addMoney(final double money) {
        this.money += money;
    }

    @Override
    public Boolean trySpend(final double price) {
        if (this.money >= price) {
            this.money -= price;
            return true;
        }
        return false;
    }

    @Override
    public double getMoney() {
        return this.money;
    }

}
