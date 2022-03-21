/*
 * CardsharperTest
 *
 * Filippov Fedor
 *
 * Software design. Homework 3.
 */
package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardsharperTest {

    @Test
    void getName() {
        Cards cards = new Cards(new Object());
        List<NormalPlayer> players = new ArrayList<>();

        Cardsharper cardsharper = new Cardsharper(cards, "cardsharper", players);

        String expected = "cardsharper";

        assertEquals(expected, cardsharper.getName());
    }

    @Test
    void testToString() {
        Cards cards = new Cards(new Object());
        List<NormalPlayer> players = new ArrayList<>();

        Cardsharper cardsharper = new Cardsharper(cards, "cardsharper", players);
        cardsharper.setBalance(5);

        String expected = "cardsharper balance: " + cardsharper.getBalance();

        assertEquals(expected, cardsharper.toString());
    }
}