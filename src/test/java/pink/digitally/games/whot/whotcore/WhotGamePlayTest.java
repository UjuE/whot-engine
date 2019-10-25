package pink.digitally.games.whot.whotcore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Deque;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Whot Game Play")
class WhotGamePlayTest {

    @Test
    @DisplayName("sets up when game has started")
    void startGame() {
        GameMediator gameMediator = mock(GameMediator.class);
        Board board = mock(Board.class);
        Player james = mock(Player.class);
        Player john = mock(Player.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        List<Player> players = asList(james, john);

        when(gameMediator.getNextPlayer()).thenReturn(james);

        WhotGamePlay whotGamePlay = new WhotGamePlay.Builder()
                .withPlayers(james, john)
                .withDeckOfCards()
                .withGameMediator(gameMediator)
                .withBoard(board)
                .withGameStateObserver(gameStateObserver)
                .build();

        whotGamePlay.startGame();

        InOrder inOrder = Mockito.inOrder(gameMediator, board, gameStateObserver);
        inOrder.verify(gameMediator).registerGameStateObserver(gameStateObserver);
        inOrder.verify(gameMediator).shuffle(any());
        inOrder.verify(gameMediator).registerPlayers(eq(players));
        inOrder.verify(gameMediator).deal(any(Deque.class));
        inOrder.verify(gameMediator).updatePlayPile(any(Deque.class));
        inOrder.verify(gameMediator).updateDrawPile(any(Deque.class));
        inOrder.verify(gameStateObserver).onGameStarted(eq(players), eq(board));
        inOrder.verify(gameStateObserver).onPlayerTurn(eq(james), eq(board));
    }
}