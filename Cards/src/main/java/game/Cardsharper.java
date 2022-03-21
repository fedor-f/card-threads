package game;

import java.util.List;

/**
 * An essence of a cardsharper.
 */
public class Cardsharper implements Runnable, Player {
    /**
     * The balance of a cardsharper.
     */
    private int balance;

    /**
     * The name of a cardsharper.
     */
    private final String name;

    /**
     * Card deck.
     */
    private final Cards cards;

    /**
     * Shows if the cardsharper took the card in the beginning of a game.
     */
    private boolean tookCard;

    private boolean playedFair;

    private boolean stole;

    /**
     * Cardsharper's list of players from which he will steal some cards.
     */
    private final List<NormalPlayer> players;

    public Cardsharper(Cards cards, String name, List<NormalPlayer> player) {
        this.balance = 0;
        this.cards = cards;
        this.name = name;
        tookCard = false;
        this.players = player;
        playedFair = false;
        stole = false;
        cards.getPlayers().add(this);
    }

    /**
     * Taking a card in the beginning of the game.
     */
    public void takeCard() {
        tookCard = true;
    }

    /**
     * Checks if a cardsharper took a card in the beginning of the game.
     *
     * @return true if he took a card and false if not
     */
    public boolean isTookCard() {
        return tookCard;
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
    public void run() {
        cards.cardsharpersGetCard(this, players);
    }

    @Override
    public String toString() {
        return getName() + " balance: " + getBalance();
    }

    /**
     * Checks if cardsharper played fair.
     *
     * @return true if he played fair or else false
     */
    public boolean isPlayedFair() {
        return playedFair;
    }

    /**
     * Sets cardsharper's decision in playing fair.
     *
     * @param statement true if played fair or else false
     */
    public void setPlayedFair(boolean statement) {
        this.playedFair = statement;
    }

    /**
     * Checks if cardsharper decided to steal.
     *
     * @return true if he stole or else false
     */
    public boolean isStole() {
        return stole;
    }

    /**
     * Sets cardsharper's decision in stealing a card.
     *
     * @param statement true if stole or else false
     */
    public void setStole(boolean statement) {
        this.stole = statement;
    }
}
