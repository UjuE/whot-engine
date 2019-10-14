package pink.digitally.games.whot.whotcore;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<WhotCard> cards = WhotCardDeck.getCards();
        List<WhotCard> actualShuffledCards =
                underTest.shuffle(new ArrayList<>(cards));

        assertAll(
                () -> assertTrue(actualShuffledCards.containsAll(cards), "Some cards are missing"),
                () -> assertFalse(IsIterableContainingInOrder.contains(cards.toArray())
                                .matches(actualShuffledCards),
                        "The shuffled deck is in the same order as the original deck")
        );
    }
}