package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Game Moderator")
class GameModeratorTest {

    private GameModerator underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameModerator();
    }

    @Test
    @DisplayName("can shuffle decks of cards")
    void canShuffleADeckOfCards() {
        List<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        List<WhotCardWithNumberAndShape> actualShuffledCards =
                underTest.shuffle(new ArrayList<>(cards));

        assertAll(
                () -> assertTrue(actualShuffledCards.containsAll(cards), "Some cards are missing"),
                () -> assertFalse(IsIterableContainingInOrder.contains(cards.toArray())
                                .matches(actualShuffledCards),
                        "The shuffled deck is in the same order as the original deck")
        );
    }

    @Test
    @DisplayName("will not allow less than 2 players")
    void errorMessageWhenNumberOfPlayersIsLessThanTwo() {
        List<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        Either<String, Void> dealResult = underTest.deal(cards, new Player("someId"));
        assertAll(
                () -> assertTrue(dealResult.isLeft()),
                () -> assertEquals("At least 2 Players is required", dealResult.getLeft())
        );
    }

    @Test
    @DisplayName("will not allow players greater than numberOfCards/10")
    void maximumNumberOfPlayersIsTheNumberOfCardsDividedByTen() {
        List<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        int maximumNumberOfPlayers = cards.size() / 10;

        Either<String, Void> dealResult = underTest.deal(cards, new Player("someId"),
                new Player("2"),
                new Player("4"),
                new Player("3"),
                new Player("6"),
                new Player("7"));

        assertAll(
                () -> assertTrue(dealResult.isLeft()),
                () -> assertEquals(String.format("No more than %d Players is allowed", maximumNumberOfPlayers), dealResult.getLeft())
        );
    }

    @Test
    @DisplayName("deals 6 cards to each player")
    void dealsSixCardsToEachPlayer() {
        List<WhotCardWithNumberAndShape> cards = WhotCardDeck.getCards();
        int deckSizeBeforeDeal = cards.size();

        List<WhotCardWithNumberAndShape> theFirstTweleveCards = new ArrayList<>(cards.subList(0, 11));

        Player firstPlayer = new Player("someId");
        Player secondPlayer = new Player("noo");

        underTest.deal(cards, firstPlayer, secondPlayer);

        assertAll(
                () -> assertEquals("The dealt cards cannot still be on the deck", deckSizeBeforeDeal - 12, cards.size()),
                () -> assertTrue(Stream.of(firstPlayer, secondPlayer)
                        .allMatch(player -> player.getCards().size() == 6), "Some players did not have 6 cards"),
                () -> assertFalse(cards.containsAll(theFirstTweleveCards), "The dealt cards did not come from the top of the deck")
        );
    }
}