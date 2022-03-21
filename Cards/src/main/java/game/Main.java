package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * Showing that the app is running.
     * It is used to make the game repetitive.
     */
    static boolean running = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        play(scanner);
    }

    /**
     * Starts the whole simulation.
     *
     * @param scanner object to enter something
     */
    private static void play(Scanner scanner) {
        System.out.println("Welcome to the card game!");

        while (running) {
            welcomeMessage();
            chooseGameMode(input(scanner, 1, 4, "\nPlease enter the right number:"),
                    scanner);
        }
    }

    /**
     * Welcoming message.
     */
    private static void welcomeMessage() {
        System.out.println("""          
                \n_________________________
                Choose a game mode:\s
                1. Play with 3 normal players
                2. Play with sharpers
                3. Play a game with variable number of players
                4. Quit.""");
    }

    /**
     * Starts selected game mode.
     *
     * @param value   depending on this parameter the selected game mode activates.
     * @param scanner object to enter something
     */
    private static void chooseGameMode(int value, Scanner scanner) {
        switch (value) {
            case 1 -> threePlayersMode();
            case 2 -> sharperMode();
            case 3 -> variableMode(scanner);
            case 4 -> endgame();
        }
    }

    /**
     * Asks a user to input a number and checks whether it is correct.
     *
     * @param scanner    scanner object to input something
     * @param lowerBound the lowest number that user can enter
     * @param upperBound the greatest number that user can enter
     * @param message    a message that shows when user is asked to input something
     * @return correct entered number
     */
    private static int input(Scanner scanner, int lowerBound, int upperBound, String message) {
        int value;
        // Asking a user to enter a number until it is correct.
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Incorrect input. Try again.");
                scanner.next();
            }
            value = scanner.nextInt();
        } while (value < lowerBound || value > upperBound);
        return value;
    }

    /**
     * The simulation of a game mode with three fair players.
     */
    private static void threePlayersMode() {
        // Initializing a common lock for threads.
        final Object lock = new Object();

        Cards cards = new Cards(lock);

        List<NormalPlayer> players = new ArrayList<>();

        List<Thread> playerThreads = new ArrayList<>();
        List<Thread> cardsharperThreads = new ArrayList<>();

        initializePlayers(cards, players, playerThreads, cardsharperThreads, 3, 0);

        System.out.println("\nStarting the game... Please stand by");

        startPlayers(playerThreads, cardsharperThreads, 0);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cards.stop();

        joinThreads(playerThreads, cardsharperThreads, 0);

        cards.showResults();
    }

    /**
     * The simulation of a sharper game mode.
     */
    private static void sharperMode() {
        // Initializing a common lock for threads.
        final Object lock = new Object();

        Cards cards = new Cards(lock);

        List<NormalPlayer> players = new ArrayList<>();

        List<Thread> playerThreads = new ArrayList<>();
        List<Thread> cardsharperThreads = new ArrayList<>();

        initializePlayers(cards, players, playerThreads, cardsharperThreads, 1, 2);

        System.out.println("\nStarting the game... Please stand by");

        startPlayers(playerThreads, cardsharperThreads, 2);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cards.stop();

        joinThreads(playerThreads, cardsharperThreads, 2);

        cards.showResults();
    }

    /**
     * The simulation of a game mode with variable number of players.
     *
     * @param scanner an object to enter something
     */
    private static void variableMode(Scanner scanner) {
        // Initializing a common lock for threads.
        final Object lock = new Object();

        Cards cards = new Cards(lock);

        List<NormalPlayer> players = new ArrayList<>();

        List<Thread> playerThreads = new ArrayList<>();
        List<Thread> cardsharperThreads = new ArrayList<>();

        int playersNumber = input(scanner, 1, 100,
                "Enter the number of normal players (maximum number is 100): ");
        int cardsharpersNumber = input(scanner, 0, 100,
                "Enter the number of cardsharpers (maximum number is 100): ");

        System.out.println("\nThe game has started... Please stand by");

        initializePlayers(cards, players, playerThreads, cardsharperThreads, playersNumber, cardsharpersNumber);

        startPlayers(playerThreads, cardsharperThreads, cardsharpersNumber);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cards.stop();

        joinThreads(playerThreads, cardsharperThreads, cardsharpersNumber);

        cards.showResults();
    }

    /**
     * Stopping the game and displaying a message.
     */
    private static void endgame() {
        running = false;
        System.out.println("Bye!");
    }

    /**
     * Initializing players and cardsharpers and their threads.
     *
     * @param cards              card deck
     * @param players            list of normal players
     * @param playerThreads      list of players' threads
     * @param cardsharperThreads list of cardsharpers' threads
     * @param playersNumber      the number of players in a game
     * @param cardsharpersNumber the number of cardsharpers in a game
     */
    private static void initializePlayers(Cards cards, List<NormalPlayer> players,
                                          List<Thread> playerThreads,
                                          List<Thread> cardsharperThreads,
                                          int playersNumber, int cardsharpersNumber) {
        for (int i = 0; i < playersNumber; i++) {
            NormalPlayer player = new NormalPlayer(cards, String.format("normal player no. %d", i + 1));
            players.add(player);
            Thread normalPlayer = new Thread(player);
            playerThreads.add(normalPlayer);
        }

        if (cardsharpersNumber != 0) {
            for (int i = 0; i < cardsharpersNumber; i++) {
                Cardsharper cardsharper = new Cardsharper(cards, String.format("cardsharper no. %d", i + 1), players);
                Thread thread = new Thread(cardsharper);
                cardsharperThreads.add(thread);
            }
        }
    }

    /**
     * Starts threads.
     *
     * @param playerThreads      list of players' threads
     * @param cardsharperThreads list of cardsharpers' threads
     * @param cardsharpersNumber the number of cardsharpers in the game
     */
    private static void startPlayers(List<Thread> playerThreads, List<Thread> cardsharperThreads,
                                     int cardsharpersNumber) {
        for (Thread fairPlayer : playerThreads) {
            fairPlayer.start();
        }

        if (cardsharpersNumber != 0) {
            for (Thread cardsharper : cardsharperThreads) {
                cardsharper.start();
            }
        }
    }

    /**
     * Joins all threads.
     *
     * @param players  the list of threads with runnable of player
     * @param sharpers the list of threads with runnable of cardsharper
     */
    private static void joinThreads(List<Thread> players, List<Thread> sharpers, int sharpersNumber) {
        for (Thread player : players) {
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (sharpersNumber != 0) {
            for (Thread sharper : sharpers) {
                try {
                    sharper.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


