package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;
import pink.digitally.games.whot.whotcore.validation.SimpleValidator;
import pink.digitally.games.whot.whotcore.validation.Validator;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static pink.digitally.games.whot.whotcore.validation.DealValidator.dealValidator;

public class GameMediator {
    private static final int MAXIMUM_NUMBER_OF_CARDS_TO_DEAL = 6;
    private final PlayEventHandler playEventHandler;
    private Deque<Player> players;
    private Board board;
    private GameStateObserver gameStateObserver;

    public GameMediator(PlayEventHandler playEventHandler) {
        this.playEventHandler = playEventHandler;
    }

    public void shuffle(LinkedList<WhotCardWithNumberAndShape> cards) {
        Collections.shuffle(cards);
    }

    public void registerPlayers(Player... players) {
        this.registerPlayers(asList(players));
    }

    public void registerPlayers(List<Player> players) {
        this.players = new LinkedList<>(players);
        this.players.forEach(it -> it.registerMediator(this));
    }

    public void registerBoard(Board board) {
        this.board = board;
    }

    public void registerGameStateObserver(GameStateObserver gameStateObserver) {
        this.gameStateObserver = gameStateObserver;
    }

    public Either<String, Void> deal(Deque<WhotCardWithNumberAndShape> cards) {
        Validator validator = dealValidator(players.size(), cards.size());

        if (validator.isValid()) {
            return dealCardsSuccessfully(cards);
        } else {
            return Either.left(validator.errorMessages().orElse("An Error occurred"));
        }
    }

    public void updatePlayPile(Deque<WhotCardWithNumberAndShape> whotCards) {
        board.addToPlayPile(whotCards.removeFirst());
    }

    public void updateDrawPile(Deque<WhotCardWithNumberAndShape> whotCards) {
        board.setDrawPile(new LinkedList<>(whotCards));
        whotCards.clear();
    }

    public Board getBoard() {
        return board;
    }

    public Either<ErrorMessage, Void> play(Player player, PlayerEvent playerEvent) {
        SimpleValidator<Player> validator = new SimpleValidator.Builder<Player>()
                .withFailureConditionAndMessage(thePlayer -> !playersIsNotNullOrEmptyAndIsPlayerTurn(thePlayer),
                        "It is not player's turn")
                .build(player);

        if (validator.isValid()) {
            return playEventHandler
                    .handle(playerEvent, player, players, board)
                    .map(newPlayersOrdering -> applyNewState(player, newPlayersOrdering));
        }

        return Either.left(new ErrorMessage(validator.errorMessages().orElse("Error Occurred")));
    }

    public Deque<Player> getPlayers() {
        return players;
    }

    public Player getNextPlayer() {
        return Optional.ofNullable(players).map(Deque::getFirst).orElse(null);
    }

    private boolean playersIsNotNullOrEmptyAndIsPlayerTurn(Player player) {
        return Optional.ofNullable(players)
                .filter(it -> !it.isEmpty() && it.peekFirst().equals(player))
                .isPresent();
    }

    private Void applyNewState(Player player, Deque<Player> newPlayersOrdering) {
        players = newPlayersOrdering;
        notifyObserver(player);
        return null;
    }

    private Either<String, Void> dealCardsSuccessfully(Deque<WhotCardWithNumberAndShape> cards) {
        for (int i = 0; i < MAXIMUM_NUMBER_OF_CARDS_TO_DEAL; i++) {
            players.forEach(p -> p.addCard(cards.removeFirst()));
        }
        return Either.right(null);
    }

    private void notifyObserver(Player player) {
        if (player.getCards().isEmpty()) {
            Optional.ofNullable(gameStateObserver)
                    .ifPresent(theGameStateObserver -> theGameStateObserver.gameEnded(players));
        } else {
            Optional.ofNullable(gameStateObserver)
                    .ifPresent(theGameStateObserver -> theGameStateObserver.currentPlayer(players.peekFirst()));
        }
    }
}
