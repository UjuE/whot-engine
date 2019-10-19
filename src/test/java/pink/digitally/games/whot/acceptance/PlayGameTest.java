package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotGamePlay;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;

@DisplayName("Playing Whot")
class PlayGameTest {

    private Player ngozi;
    private Player emeka;
    private WhotGamePlay whotGamePlay;

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

    private void andTheGameHasStarted() {
        whotGamePlay.startGame();
    }

    private void givenThereIsAWhotGame() {
        ngozi = player("Ngozi");
        emeka = player("Emeka");
        whotGamePlay =  new WhotGamePlay.Builder()
                .withBoard(new BoardActor())
                .withDeckOfCards()
                .withGameMediator(new GameMediator())
                .withDeckOfCards()
                .withPlayers(ngozi, emeka)
                .build();
    }
}
