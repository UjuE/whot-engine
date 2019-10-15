package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class GameModerator {
    public List<WhotCardWithNumberAndShape> shuffle(List<WhotCardWithNumberAndShape> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    public Either<String, Void> deal(List<WhotCardWithNumberAndShape> cards, Player... player) {
        Validator<Integer> validator = getDealValidator(cards);

        if (validator.isValid(player.length)) {
            for (int i = 0; i < 6; i++) {
                Stream.of(player)
                        .forEach(p -> p.addCard(cards.remove(0)));
            }
            return Either.right(null);
        } else {
            return Either.left(validator.errorMessages(player.length).orElse("An Error occurred"));
        }
    }

    private Validator<Integer> getDealValidator(List<WhotCardWithNumberAndShape> cards) {
        int maximumNumberOfPlayers = cards.size() / 10;
        return new Validator.ValidatorBuilder<Integer>()
                .withFailureConditionAndMessage(number -> number < 2, "At least 2 Players is required")
                .withFailureConditionAndMessage(number -> number > maximumNumberOfPlayers,
                        String.format("No more than %d Players is allowed", maximumNumberOfPlayers))
                .build();
    }
}
