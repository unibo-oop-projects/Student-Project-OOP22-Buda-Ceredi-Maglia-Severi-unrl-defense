package it.unibo.unrldef.model.api;

/**
 * the bank for a game.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public interface Bank {
    /**
     * @param money the money to add to the bank
     */
    void addMoney(double money);

    /**
     * subtract the price of the item from the money in the bank.
     * @param price the price of the item
     * @return true if the player can afford the item, false otherwise
     */
    Boolean trySpend(double price);

    /**
     * 
     * @return the money in the bank
     */
    double getMoney();

    /**
     * 
     * @return a copy of the bank
     */
    Bank copy();
}
