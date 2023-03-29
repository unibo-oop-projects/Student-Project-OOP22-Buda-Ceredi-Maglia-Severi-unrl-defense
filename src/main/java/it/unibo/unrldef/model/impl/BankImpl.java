package it.unibo.unrldef.model.impl;

import it.unibo.unrldef.model.api.Bank;

/**
 * the bank for the game Unreal Defense.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public class BankImpl implements Bank {

    private double money;

    /**
     * the constructor.
     * 
     * @param startingMoney
     */
    public BankImpl(final double startingMoney) {
        this.money = startingMoney;
    }
    /**
     * @param money the money to add to the bank
     */
    public void addMoney(final double money) {
        this.money += money;
    }

    /**
     * subtract the price of the item from the money in the bank.
     * @param price the price of the item
     * @return true if the player can afford the item, false otherwise
     */
    public Boolean trySpend(final double price) {
        if (this.money >= price) {
            this.money -= price;
            return true;
        }
        return false;
    }

    /**
     * @return the money in the bank
     */
    public double getMoney() {
        return this.money;
    }

}
