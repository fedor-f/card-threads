/*
 * NormalPlayerTest
 *
 * Filippov Fedor
 *
 * Software design. Homework 3.
 */
package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalPlayerTest {

    @Test
    void decreaseBalance() {
        Cards cards = new Cards(new Object());
        NormalPlayer normalPlayer = new NormalPlayer(cards, "player");

        normalPlayer.setBalance(5);
        normalPlayer.decreaseBalance(5);

        int expected = 0;

        assertEquals(expected, normalPlayer.getBalance());
    }

    @Test
    void getNameFirst() {
        Cards cards = new Cards(new Object());
        NormalPlayer normalPlayer = new NormalPlayer(cards, "player");

        String expected = "player";

        assertEquals(expected, normalPlayer.getName());
    }

    @Test
    void getNameSecond() {
        Cards cards = new Cards(new Object());
        NormalPlayer normalPlayer = new NormalPlayer(cards, "player");

        String expected = "play";

        assertNotEquals(expected, normalPlayer.getName());
    }

    @Test
    void testToString() {
        Cards cards = new Cards(new Object());
        NormalPlayer normalPlayer = new NormalPlayer(cards, "player");

        normalPlayer.setBalance(5);

        String expected = "player balance: " + normalPlayer.getBalance();

        assertEquals(expected, normalPlayer.toString());
    }
}