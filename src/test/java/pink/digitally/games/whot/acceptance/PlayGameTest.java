package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.acceptance.actors.GameObserverActor;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.events.handler.NoRulesPlayEventHandler;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.whotcore.WhotCard.whotCard;

@DisplayName("Whot game should")
class PlayGameTest extends AcceptanceTestBase {

    private Player ngozi;
    private Player emeka;
    private WhotGamePlay whotGamePlay;
    private GameMediatorActor gameMediator;
    private GameObserverActor gameStateObserver;

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

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.SQUARE));
    }

    @Test
    @DisplayName("allow players take cards from draw pile after the game has started")
    void givenThatTheGameHasStartedAPlayerTakesACard() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesACard(ngozi);

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));
        thenTheNumberOfCardsOfPlayer(ngozi, 2);
    }

    @Test
    @DisplayName("allow only the current player play a turn")
    void onlyTheCurrentPlayerCanPlay() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesACard(emeka);

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));
        thenTheNumberOfCardsOfPlayer(emeka, 1);
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

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FOUR, WhotShape.TRIANGLE));
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
