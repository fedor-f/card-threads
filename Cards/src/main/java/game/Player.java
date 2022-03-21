package game;

/**
 * General description of the player.
 */
public interface Player {
    /**
     * Get the player's balance
     *
     * @return player's balance
     */
    int getBalance();

    /**
     * Increase the player's balance.
     *
     * @param balance the value by which the balance will be increased
     */
    void setBalance(int balance);

    /**
     * Get the player's name.
     *
     * @return player's name
     */
    String getName();
}
