package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.teststub.StubPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        underTest.registerPlayers(mock(Player.class));
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

        underTest.registerPlayers(mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class));
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

        Player firstPlayer = new StubPlayer();
        Player secondPlayer = new StubPlayer();

        underTest.registerPlayers(firstPlayer, secondPlayer);
        underTest.deal(cards);

        assertAll(
                () -> assertEquals("The dealt cards cannot still be on the deck", deckSizeBeforeDeal - 12, cards.size()),
                () -> assertTrue(Stream.of(firstPlayer, secondPlayer)
                        .allMatch(player -> player.getCards().size() == 6), "Some players did not have 6 cards"),
                () -> assertFalse(cards.containsAll(theFirstTweleveCards), "The dealt cards did not come from the top of the deck")
        );
    }

    @Test
    @DisplayName("The first player in the list of registered players is first")
    void theFirstPlayerAtStartOfGame() {
        Player firstPlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);

        underTest.registerPlayers(firstPlayer, secondPlayer);

        assertEquals(firstPlayer, underTest.getNextPlayer());
    }

    @Test
    @DisplayName("The first player in the list of registered players is first")
    void theNextPlayerAfterFirstPlayerHasPlayed() {
        Player firstPlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);

        underTest.registerPlayers(firstPlayer, secondPlayer);
        underTest.play(firstPlayer, mock(PlayerEvent.class));

        assertEquals(secondPlayer, underTest.getNextPlayer());
    }

    @Test
    void updateBoardPlayPile() {
        LinkedList<WhotCardWithNumberAndShape> whotCards = WhotCardDeck.getCards();
        WhotCardWithNumberAndShape first = whotCards.getFirst();

        Board board = mock(Board.class);

        underTest.updatePlayPile(whotCards, board);

        verify(board).addToPlayPile(ArgumentMatchers.eq(first));
        //Size of the cards is reduced by one
        assertEquals(53, whotCards.size());
    }

    @Test
    void updateBoardDrawPile() {
        LinkedList<WhotCardWithNumberAndShape> whotCards = WhotCardDeck.getCards();
        Board board = mock(Board.class);

        underTest.updateDrawPile(whotCards, board);

        verify(board).setDrawPile(any(LinkedList.class));
        //All the cards have been given to the board
        assertTrue(whotCards.isEmpty());
    }


}