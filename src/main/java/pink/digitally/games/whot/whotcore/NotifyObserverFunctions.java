package pink.digitally.games.whot.whotcore;

import java.util.Deque;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.scorer.WhotCardScoreHelper.lowestScore;

public enum NotifyObserverFunctions {
    PLAYER_FINISHES_CARDS((player, board) -> player.getCards().isEmpty(),
            ((gameStateObserver, player, players, board) -> gameStateObserver.onGameEnded(player, players))),

    BOARD_DRAW_PILE_FINISHES((player, board) -> board.getDrawPile().isEmpty(),
            ((gameStateObserver, player, players, board) -> gameStateObserver.onGameEnded(lowestScore(players), players)));

    private GameStateCondition gameStateCondition;
    private GameObserverAction gameObserverAction;

    NotifyObserverFunctions(GameStateCondition gameStateCondition,
                            GameObserverAction gameObserverAction) {

        this.gameStateCondition = gameStateCondition;
        this.gameObserverAction = gameObserverAction;
    }

    private static GameObserverAction defaultGameObserverAction(){
        return ((gameStateObserver, player, players, board) -> gameStateObserver
                .onPlayerTurn(players.peekFirst(), board));
    }

    public static void observe(Player currentPlayer,
                               Deque<Player> players,
                               Board board,
                               GameStateObserver gameStateObserver){
        Stream.of(values())
                .filter(it -> it.gameStateCondition.condition(currentPlayer, board))
                .map(it -> it.gameObserverAction)
                .findFirst()
                .orElse(defaultGameObserverAction())
                .action(gameStateObserver, currentPlayer, players, board);
    }

    public interface GameStateCondition {
        boolean condition(Player player, Board board);
    }

    public interface GameObserverAction {
        void action(GameStateObserver gameStateObserver,
                    Player player,
                    Deque<Player> players,
                    Board board);
    }
}
