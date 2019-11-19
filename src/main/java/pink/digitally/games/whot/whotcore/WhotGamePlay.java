package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.card.WhotCardDeck;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.handler.StandardRulesPlayEventHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class WhotGamePlay {
    private final Deque<WhotCardWithNumberAndShape> cards;
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
                .withGameMediator(new GameMediator(new StandardRulesPlayEventHandler()));
    }

    public static class Builder {
        private List<Player> players = new ArrayList<>();
        private Deque<WhotCardWithNumberAndShape> cards;
        private GameMediator gameMediator;
        private Board board;
        private GameStateObserver gameStateObserver;

        public Builder withPlayers(Player... players) {
            return this.withPlayers(asList(players));
        }

        public Builder withPlayers(List<Player> players) {
            this.players.addAll(players);
            return this;
        }

        public Builder addPlayer(Player player){
            players.add(player);
            return this;
        }

        public Builder addEasyRobotPlayer(String name){
            return addPlayer(new RobotPlayer(name));
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
            this.gameStateObserver = new RoboGameObserverWrapper(gameStateObserver);
            return this;
        }

        public WhotGamePlay build() {
            return new WhotGamePlay(this);
        }
    }
}
