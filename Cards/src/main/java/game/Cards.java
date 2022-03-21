package game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card deck and croupier description.
 */
public class Cards {
    /**
     * A condition on which the card game continues.
     */
    private boolean stop;

    /**
     * Common lock to synchronize.
     */
    private final Object commonLock;

    /**
     * The list of players to process results.
     */
    private final List<Player> players;

    public Cards(Object lock) {
        this.commonLock = lock;
        stop = false;
        players = new ArrayList<>();
    }

    /**
     * Normal player's turn to take a card from deck.
     *
     * @param player an instance of a normal player to increase his balance
     */
    public void getCard(NormalPlayer player) {
        while (!stop) {
            synchronized (commonLock) {
                player.setBalance(ThreadLocalRandom.current().nextInt(1, 11));
            }
            goSleep(100, 201);
        }
    }

    /**
     * Sleeps a thread when some player made his turn.
     *
     * @param origin the least number of milliseconds a thread will sleep
     * @param bound  the greatest number of milliseconds a thread will sleep (exclusive)
     */
    private void goSleep(int origin, int bound) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(origin, bound));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cardsharper's turn to take or steal a card.
     *
     * @param cardsharper an instance of a cardsharper to increase his balance and make a turn.
     * @param players     cardsharper's list of players from which he will steal cards from a random player.
     */
    public void cardsharpersGetCard(Cardsharper cardsharper, List<NormalPlayer> players) {
        while (!stop) {
            // When entering the loop we check if cardsharper has a card:
            // if he does then check the probability and make next steps.
            if (!cardsharper.isTookCard()) {
                cardsharper.setPlayedFair(true);
                cardsharper.setStole(false);
                synchronized (commonLock) {
                    cardsharper.setBalance(ThreadLocalRandom.current().nextInt(1, 11));
                    cardsharper.takeCard();
                }
            } else if (ThreadLocalRandom.current().nextDouble() <= 0.4) {
                cardsharper.setStole(true);
                cardsharper.setPlayedFair(false);
                thieveryOptions(cardsharper, players);
            } else {
                cardsharper.setPlayedFair(true);
                cardsharper.setStole(false);
                synchronized (commonLock) {
                    cardsharper.setBalance(ThreadLocalRandom.current().nextInt(1, 11));
                }
            }
            sleepOptions(cardsharper);
        }
    }

    /**
     * When a cardsharper's turn was made sleep his thread.
     * The number of ms he is going to sleep depends on whether he stole a card or played fair.
     */
    private void sleepOptions(Cardsharper cardsharper) {
        if (cardsharper.isStole()) {
            cardsharper.setPlayedFair(false);
            cardsharper.setStole(false);
            goSleep(180, 301);
        } else if (cardsharper.isPlayedFair()) {
            cardsharper.setStole(false);
            cardsharper.setPlayedFair(false);
            goSleep(100, 201);
        }
    }

    /**
     * Steal a card depending on a balance of a normal player.
     *
     * @param cardsharper an instance of a cardsharper to increase his balance
     * @param players     cardsharper's list of players from which he will steal cards from a random player.
     */
    private void thieveryOptions(Cardsharper cardsharper, List<NormalPlayer> players) {
        // Get random number a cardsharper is going to steal from player's balance.
        int theft = ThreadLocalRandom.current().nextInt(0, 9);

        // Get random player from which a cardsharper is going to steal a card.
        int choice = ThreadLocalRandom.current().nextInt(0, players.size());

        synchronized (players.get(choice)) {
            if (players.get(choice).getBalance() >= theft) {
                cardsharper.setBalance(theft);
                players.get(choice).decreaseBalance(theft);
            } else {
                cardsharper.setBalance(players.get(choice).getBalance());
                players.get(choice).decreaseBalance(players.get(choice).getBalance());
            }
        }
    }

    /**
     * Stops the card game.
     */
    public void stop() {
        stop = true;
        System.out.println("\nProcessing results...");
    }

    /**
     * Shows results i.e. balances of players and the winner.
     */
    public void showResults() {
        List<Integer> winnerIndices = new ArrayList<>();

        System.out.println();
        for (Player player : players) {
            System.out.println(player);
        }

        players.sort(Comparator.comparing(Player::getBalance));

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getBalance() == players.get(players.size() - 1).getBalance()) {
                winnerIndices.add(i);
            }
        }

        for (Integer playerIndex : winnerIndices) {
            System.out.println("\nThe winner(s) is (are): " + players.get(playerIndex).getName());
        }
    }

    /**
     * Get players.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}