package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Game Moderator")
class GameMediatorTest {

    private GameMediator underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameMediator();
    }

    @Test
    @DisplayName("can shuffle decks of cards")
    void canShuffleADeckOfCards() {
        LinkedList<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        LinkedList<WhotCardWithNumberAndShape> unTouchedCopy = new LinkedList<>(cards);
        underTest.shuffle(cards);

        assertAll(
                () -> assertTrue(cards.containsAll(unTouchedCopy), "Some cards are missing"),
                () -> assertFalse(IsIterableContainingInOrder.contains(cards.toArray())
                                .matches(unTouchedCopy),
                        "The shuffled deck is in the same order as the original deck")
        );
    }

    @Test
    @DisplayName("will not allow less than 2 players")
    void errorMessageWhenNumberOfPlayersIsLessThanTwo() {
        LinkedList<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        underTest.registerPlayers(new Player("someId"));
        Either<String, Void> dealResult = underTest.deal(cards);
        assertAll(
                () -> assertTrue(dealResult.isLeft()),
                () -> assertEquals("At least 2 Players is required", dealResult.getLeft())
        );
    }

    @Test
    @DisplayName("will not allow players greater than numberOfCards/10")
    void maximumNumberOfPlayersIsTheNumberOfCardsDividedByTen() {
        LinkedList<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        int maximumNumberOfPlayers = cards.size() / 10;

        underTest.registerPlayers(new Player("someId"),
                new Player("2"),
                new Player("4"),
                new Player("3"),
                new Player("6"),
                new Player("7"));
        Either<String, Void> dealResult = underTest.deal(cards);

        assertAll(
                () -> assertTrue(dealResult.isLeft()),
                () -> assertEquals(String.format("No more than %d Players is allowed", maximumNumberOfPlayers), dealResult.getLeft())
        );
    }

    @Test
    @DisplayName("deals 6 cards to each player")
    void dealsSixCardsToEachPlayer() {
        LinkedList<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        int deckSizeBeforeDeal = cards.size();

        WhotCardWithNumberAndShape[] ts = cards.toArray(new WhotCardWithNumberAndShape[]{});
        List<WhotCardWithNumberAndShape> theFirstTweleveCards = asList(ts).subList(0, 11);

        Player firstPlayer = new Player("someId");
        Player secondPlayer = new Player("noo");

        underTest.registerPlayers(firstPlayer, secondPlayer);
        underTest.deal(cards);

        assertAll(
                () -> assertEquals("The dealt cards cannot still be on the deck", deckSizeBeforeDeal - 12, cards.size()),
                () -> assertTrue(Stream.of(firstPlayer, secondPlayer)
                        .allMatch(player -> player.getCards().size() == 6), "Some players did not have 6 cards"),
                () -> assertFalse(cards.containsAll(theFirstTweleveCards), "The dealt cards did not come from the top of the deck")
        );
    }
}