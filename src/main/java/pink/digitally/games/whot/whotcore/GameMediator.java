package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;
import pink.digitally.games.whot.whotcore.validation.SimpleValidator;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class GameMediator {
    private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
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
        SimpleValidator<Integer> validator = getDealValidator(players.size(), cards.size());

        if (validator.isValid()) {
            for (int i = 0; i < 6; i++) {
                players.forEach(p -> p.addCard(cards.removeFirst()));
            }
            return Either.right(null);
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
        SimpleValidator<Player> validator = new SimpleValidator.ValidatorBuilder<Player>()
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

    private Void applyNewState(Player player, Deque<Player> newPlayersOrdering) {
        players = newPlayersOrdering;
        if (player.getCards().isEmpty()) {
            Optional.ofNullable(gameStateObserver)
                    .ifPresent(theGameStateObserver -> theGameStateObserver.updateState(GameState.ENDED));
        }
        return null;
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

    private SimpleValidator<Integer> getDealValidator(int numberOfPlayers, int numberOfCards) {
        int maximumNumberOfPlayers = numberOfCards / 10;

        return new SimpleValidator.ValidatorBuilder<Integer>()
                .withFailureConditionAndMessage(number -> number < MINIMUM_NUMBER_OF_PLAYERS, "At least 2 Players is required")
                .withFailureConditionAndMessage(number -> number > maximumNumberOfPlayers,
                        String.format("No more than %d Players is allowed", maximumNumberOfPlayers))
                .build(numberOfPlayers);
    }
}
