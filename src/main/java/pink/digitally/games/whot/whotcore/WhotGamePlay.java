package pink.digitally.games.whot.whotcore;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class WhotGamePlay {
    private final LinkedList<WhotCardWithNumberAndShape> cards;
    private final List<Player> players;
    private final GameMediator gameMediator;
    private final Board board;

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
