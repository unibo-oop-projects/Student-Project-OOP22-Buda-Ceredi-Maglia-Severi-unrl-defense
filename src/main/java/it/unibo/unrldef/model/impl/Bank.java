package it.unibo.unrldef.model.impl;

/**
 * a simple bank for a game
 * @author francesco.buda3@studio.unibo.it
 */
public class Bank {

    private double money;

    public Bank(double startingMoney) {
        this.money = startingMoney;
    }


    public void addMoney(double money) {
        this.money += money;
    }

    public Boolean trySpend(double price) {
        if ( this.money >= price ) {
            this.money -= price;
            return true;
        }
        return false;
    }

    public double getMoney() {
        return this.money;
    }


}
