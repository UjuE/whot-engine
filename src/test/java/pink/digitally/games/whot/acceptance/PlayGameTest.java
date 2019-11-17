package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.events.handler.NoRulesPlayEventHandler;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.whotcore.card.WhotCard.whotCard;

@DisplayName("Whot game should")
class PlayGameTest extends AcceptanceTestBase {

    @Test
    @DisplayName("not be set up before the game has started")
    void theGameHasNotBeenStarted() {
        givenThereIsAWhotGame();

        assertAll(
                () -> assertNull(whotGamePlay.nextPlayer(), "The next player should be null"),
                () -> assertTrue(whotGamePlay.getBoard().getPlayPile().isEmpty(), "There should be no Play pile"),
                () -> assertTrue(whotGamePlay.getBoard().getDrawPile().isEmpty(), "There should be no Draw Pile"),
                () -> assertEquals(GameState.NOT_STARTED, whotGamePlay.getGameState(), "the game state should be 'NOT_STARTED'")
        );
    }

    @Test
    @DisplayName("be in the correct state when the game has started")
    void theGameIsInTheExpectedState() {
        givenThereIsAWhotGame();
        andTheGameHasStarted();

        assertAll(
                () -> assertEquals(ngozi, whotGamePlay.nextPlayer()),
                () -> assertFalse(whotGamePlay.getBoard().getPlayPile().isEmpty()),
                () -> assertFalse(whotGamePlay.getBoard().getDrawPile().isEmpty()),
                () -> assertEquals(GameState.STARTED, whotGamePlay.getGameState())
        );
    }

    @Test
    @DisplayName("allow players play card on play pile after the game has started")
    void givenThatTheGameHasStartedAPlayerPlays() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        assertEquals(whotCard(WhotNumber.FIVE, WhotShape.SQUARE), whotGamePlay.getBoard().getPlayPile().getFirst());
    }

    @Test
    @DisplayName("allow players take cards from draw pile after the game has started")
    void givenThatTheGameHasStartedAPlayerTakesACard() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesCard(ngozi);

        assertEquals(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE), whotGamePlay.getBoard().getPlayPile().getFirst());
        assertEquals(2, ngozi.getCards().size());
    }

    @Test
    @DisplayName("allow only the current player play a turn")
    void onlyTheCurrentPlayerCanPlay() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesCard(emeka);

        assertEquals(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE), whotGamePlay.getBoard().getPlayPile().getFirst());
        assertEquals(1, emeka.getCards().size());
    }

    @Test
    @DisplayName("allow only valid cards to be played on the play pile")
    void onlyValidCardsCanBePlayed() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FOUR, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        assertEquals(whotCard(WhotNumber.FOUR, WhotShape.TRIANGLE), whotGamePlay.getBoard().getPlayPile().getFirst());
    }

    @Test
    @DisplayName("end game when a player has no more cards")
    void theGameIsEndedWhenAPlayerHasNoMoreCards() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        assertEquals(GameState.ENDED, whotGamePlay.getGameState());
        assertEquals(ngozi, gameStateObserver.getWinner());
        assertNull(whotGamePlay.nextPlayer());
    }

    @Override
    PlayEventHandler playEventHandler() {
        return new NoRulesPlayEventHandler();
    }
}
