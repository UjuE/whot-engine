package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class WhotGamePlay {
    private final LinkedList<WhotCardWithNumberAndShape> cards;
    private final List<Player> players;
    private final GameMediator gameMediator;
    private final Board board;

    private GameState gameState = GameState.NOT_STARTED;

    private WhotGamePlay(Builder builder) {
        this.players = builder.players;
        this.cards = builder.cards;
        this.gameMediator = builder.gameMediator;
        this.board = builder.board;
    }

    public void startGame() {
        gameMediator.shuffle(cards);
        gameMediator.registerPlayers(players);
        gameMediator.deal(cards);
        gameMediator.updatePlayPile(cards, board);
        gameMediator.updateDrawPile(cards, board);
        updateGameState(GameState.STARTED);
    }

    public Player nextPlayer() {
        return gameMediator.getNextPlayer();
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }


    private void updateGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public static class Builder {
        private List<Player> players;
        private LinkedList<WhotCardWithNumberAndShape> cards;
        private GameMediator gameMediator;
        private Board board;

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

        public WhotGamePlay build() {
            return new WhotGamePlay(this);
        }
    }
}
