package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.NoRulesPlayEventHandler;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;
import static pink.digitally.games.whot.whotcore.WhotCard.whotCard;

@DisplayName("Playing Whot")
class PlayGameTest {

    private Player ngozi;
    private Player emeka;
    private WhotGamePlay whotGamePlay;
    private GameMediatorActor gameMediator;

    @BeforeEach
    void setUp() {
        gameMediator = new GameMediatorActor(new NoRulesPlayEventHandler());
    }

    @Test
    void theGameHasNotBeenStarted() {
        givenThereIsAWhotGame();

        assertAll(
                () -> assertNull(whotGamePlay.nextPlayer(), "The next player should be null"),
                () -> assertTrue(whotGamePlay.getBoard().getPlayPile().isEmpty(), "There should be no Play pile"),
                () -> assertTrue(whotGamePlay.getBoard().getDrawPile().isEmpty(), "There should be no Draw Pile"),
                () -> assertEquals(GameState.NOT_STARTED, whotGamePlay.getGameState())
        );
    }

    @Test
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
    void givenThatTheGameHasStartedAPlayerTakesACard() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesACard(ngozi);

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));
    }

    private void thenTheTopOfThePlayPileIs(WhotCard whotCard) {
        assertEquals(whotCard, whotGamePlay.getBoard().getPlayPile().getFirst());
    }

    private void whenPlayerPlays(Player player, WhotCard whotCard) {
        player.play(new PlayCardPlayerEvent(whotCard));
    }

    private void whenPlayerTakesACard(Player player) {
        player.play(new TakeCardPlayerEvent());
    }

    private void andTheTopOfPlayPileIs(WhotCard whotCard) {
        gameMediator.setTheTopOfPile(whotCard);
    }

    private void andTheGameMediatorWillDeal(List<WhotCard> list, Player player) {
        gameMediator.setPlayerCards(list, player);
    }

    private void andTheGameHasStarted() {
        whotGamePlay.startGame();
    }

    private void givenThereIsAWhotGame() {
        ngozi = player("Ngozi");
        emeka = player("Emeka");
        whotGamePlay =  new WhotGamePlay.Builder()
                .withBoard(new BoardActor())
                .withDeckOfCards()
                .withGameMediator(gameMediator)
                .withDeckOfCards()
                .withPlayers(ngozi, emeka)
                .build();
    }
}
