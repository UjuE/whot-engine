package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;
import pink.digitally.games.whot.whotcore.utils.QueueShuffler;
import pink.digitally.games.whot.whotcore.validation.SimpleValidator;
import pink.digitally.games.whot.whotcore.validation.Validator;

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
    private Validator<PlayerEvent> nextPlayEventValidator = new SimpleValidator.Builder<PlayerEvent>().build();
    private Board board;
    private GameStateObserver gameStateObserver;
    private boolean inSpecialPlay = false;
    private int totalTakeCount;

    public GameMediator(PlayEventHandler playEventHandler) {
        this.playEventHandler = playEventHandler;
    }

    public void shuffle(Deque<WhotCardWithNumberAndShape> cards) {
        QueueShuffler.shuffle(cards);
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
        int numberOfPlayers = players.size();
        Validator<Integer> validator = dealValidator(cards.size());

        if (validator.isValid(numberOfPlayers)) {
            return dealCardsSuccessfully(cards);
        } else {
            return Either.left(validator.errorMessages(numberOfPlayers).orElse("An Error occurred"));
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

    public void play(Player player, PlayerEvent playerEvent) {
        SimpleValidator<Player> validator = new SimpleValidator.Builder<Player>()
                .withFailureConditionAndMessage(thePlayer -> !playersIsNotNullOrEmptyAndIsPlayerTurn(thePlayer),
                        "It is not player's turn")
                .build();

        if (validator.isValid(player)) {
            if (nextPlayEventValidator.isValid(playerEvent)) {
                Either<ErrorMessage, Void> playResult = playEventHandler
                        .handle(playerEvent, player, players, board, gameStateObserver, this)
                        .map(newPlayersOrdering -> applyNewState(player, newPlayersOrdering));

                handlePossibleError(player, playResult);
            } else {
                gameStateObserver.onInvalidPlay(player, board, new ErrorMessage(nextPlayEventValidator.errorMessages(playerEvent).orElse("")));
            }

        }
    }

    public Deque<Player> getPlayers() {
        return players;
    }

    public Player getNextPlayer() {
        if (!isGameEnded()) {
            return Optional.ofNullable(players).map(Deque::getFirst).orElse(null);
        }

        return null;
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
                    .ifPresent(theGameStateObserver -> theGameStateObserver.onGameEnded(player, players));
        } else {
            Optional.ofNullable(gameStateObserver)
                    .ifPresent(theGameStateObserver -> theGameStateObserver.onPlayerTurn(players.peekFirst(), board));
        }
    }

    private boolean isGameEnded() {
        return Optional.ofNullable(gameStateObserver)
                .filter(it -> GameState.ENDED.equals(it.getCurrentGameState()))
                .isPresent();
    }

    private void handlePossibleError(Player player, Either<ErrorMessage, Void> playResult) {
        if (playResult.isLeft()) {
            gameStateObserver.onInvalidPlay(player, board, playResult.getLeft());
        }
    }

    public void addTakeCardCount(int takeCardCount) {
        totalTakeCount += takeCardCount;
    }

    public void nextPlayerValidation(Validator<PlayerEvent> validator) {
        this.inSpecialPlay = true;
        this.nextPlayEventValidator = validator;
    }

    public boolean isInSpecialPlay() {
        return inSpecialPlay;
    }

    public Integer getTotalTakeCount() {
        return totalTakeCount;
    }

    public void resetTakeCount() {
        totalTakeCount = 0;
    }

    public void resetNextPlayEventValidation() {
        this.inSpecialPlay = false;
        this.nextPlayEventValidator = new SimpleValidator.Builder<PlayerEvent>().build();
    }
}
