package game;

/**
 * An essence of a normal player.
 */
public class NormalPlayer implements Runnable, Player {
    /**
     * The balance of a normal player.
     */
    private int balance;

    /**
     * Card deck.
     */
    private final Cards cards;

    /**
     * The name of a normal player.
     */
    private final String name;

    public NormalPlayer(Cards cards, String name) {
        this.balance = 0;
        this.cards = cards;
        cards.getPlayers().add(this);
        this.name = name;
    }

    /**
     * Decreases the balance of a player.
     *
     * @param theft the number by which the balance will be decreased
     */
    public void decreaseBalance(int theft) {
        balance -= theft;
    }

    @Override
    public void run() {
        cards.getCard(this);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void setBalance(int balance) {
        this.balance += balance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " balance: " + getBalance();
    }
}
