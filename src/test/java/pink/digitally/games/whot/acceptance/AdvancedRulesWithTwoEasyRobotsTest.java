package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.GameObserverActor;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.InMemoryBoard;
import pink.digitally.games.whot.whotcore.RobotPlayer;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.events.handler.AdvancedRulesPlayEventHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdvancedRulesWithTwoEasyRobotsTest {
    @Test
    void justPlay() {
        GameObserverActor gameStateObserver = new GameObserverActor();
        WhotGamePlay whotGamePlay = new WhotGamePlay.Builder()
                .withGameMediator(new GameMediator(new AdvancedRulesPlayEventHandler()))
                .withPlayers(new RobotPlayer("Robo1"), new RobotPlayer("Robo2"))
                .withGameStateObserver(gameStateObserver)
                .withBoard(new InMemoryBoard())
                .withDeckOfCards()
                .build();

        whotGamePlay.startGame();

        assertNotNull(gameStateObserver.getWinner());
        assertEquals(GameState.ENDED, whotGamePlay.getGameState());
    }
}
