package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.events.handler.NoRulesPlayEventHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class WhotGamePlay {
    private final LinkedList<WhotCardWithNumberAndShape> cards;
    private final List<Player> players;
    private final GameMediator gameMediator;
    private final Board board;
    private final GameStateObserver gameStateObserver;

    private WhotGamePlay(Builder builder) {
        this.players = builder.players;
        this.cards = builder.cards;
        this.gameMediator = builder.gameMediator;
        this.board = builder.board;
        this.gameStateObserver = builder.gameStateObserver;
    }

    public void startGame() {
        gameMediator.registerGameStateObserver(gameStateObserver);
        gameMediator.shuffle(cards);
        gameMediator.registerPlayers(players);
        gameMediator.registerBoard(board);
        gameMediator.deal(cards);
        gameMediator.updatePlayPile(cards);
        gameMediator.updateDrawPile(cards);

        Optional.ofNullable(gameStateObserver)
                .ifPresent(it -> {
                    gameStateObserver.onGameStarted(players, board);
                    gameStateObserver.onPlayerTurn(nextPlayer(), board);
                });
    }

    public Player nextPlayer() {
        return gameMediator.getNextPlayer();
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameStateObserver.getCurrentGameState();
    }

    @SuppressWarnings("unused")
    public static Builder withDefaults() {
        return new Builder()
                .withBoard(new InMemoryBoard())
                .withDeckOfCards()
                .withGameMediator(new GameMediator(new NoRulesPlayEventHandler()));
    }

    public static class Builder {
        private List<Player> players;
        private LinkedList<WhotCardWithNumberAndShape> cards;
        private GameMediator gameMediator;
        private Board board;
        private GameStateObserver gameStateObserver;

        public Builder withPlayers(Player... players) {
            this.players = asList(players);
            return this;
        }

        public Builder withDeckOfCards() {
            this.cards = WhotCardDeck.getCards();
            return this;
        }

        public Builder withGameMediator(GameMediator gameMediator) {
            this.gameMediator = gameMediator;
            return this;
        }

        public Builder withBoard(Board board) {
            this.board = board;
            return this;
        }

        public Builder withGameStateObserver(GameStateObserver gameStateObserver) {
            this.gameStateObserver = gameStateObserver;
            return this;
        }

        public WhotGamePlay build() {
            return new WhotGamePlay(this);
        }
    }
}
