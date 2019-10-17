package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.validation.Validator;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMediator {
    private Deque<Player> players;

    public void shuffle(LinkedList<WhotCardWithNumberAndShape> cards) {
        Collections.shuffle(cards);
    }

    public void registerPlayers(Player... players) {
        this.registerPlayers(asList(players));
    }

    public void registerPlayers(List<Player> players) {
        this.players = new LinkedList<>(players);
    }


    public Either<String, Void> deal(Deque<WhotCardWithNumberAndShape> cards) {
        Validator<Integer> validator = getDealValidator(players.size(), cards.size());

        if (validator.isValid()) {
            for (int i = 0; i < 6; i++) {
                players.forEach(p -> p.addCard(cards.removeFirst()));
            }
            return Either.right(null);
        } else {
            return Either.left(validator.errorMessages().orElse("An Error occurred"));
        }
    }

    public void updatePlayPile(Deque<WhotCardWithNumberAndShape> whotCards, Board board) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void updateDrawPile(Deque<WhotCardWithNumberAndShape> whotCards, Board board) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Validator<Integer> getDealValidator(int numberOfPlayers, int numberOfCards) {
        int maximumNumberOfPlayers = numberOfCards / 10;

        return new Validator.ValidatorBuilder<Integer>()
                .withFailureConditionAndMessage(number -> number < 2, "At least 2 Players is required")
                .withFailureConditionAndMessage(number -> number > maximumNumberOfPlayers,
                        String.format("No more than %d Players is allowed", maximumNumberOfPlayers))
                .build(numberOfPlayers);
    }
}
