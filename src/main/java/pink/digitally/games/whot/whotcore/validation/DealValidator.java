package pink.digitally.games.whot.whotcore.validation;


import org.apache.commons.math3.util.Pair;

import static java.util.Arrays.asList;

public class DealValidator extends SimpleValidator<Integer> {
    private static final Integer MINIMUM_NUMBER_OF_PLAYERS = 2;
    private static final int DENOMINATOR = 10;

    private DealValidator(int numberOfPlayers, int numberOfCards) {
        super(numberOfPlayers, asList(
                new Pair<>(number -> number < MINIMUM_NUMBER_OF_PLAYERS, "At least 2 Players is required"),
                new Pair<>(number -> number > maximumNumberOfPlayers(numberOfCards), String.format("No more than %d Players is allowed", maximumNumberOfPlayers(numberOfCards))))
        );
    }

    private static int maximumNumberOfPlayers(int numberOfCards) {
        return numberOfCards / DENOMINATOR;
    }

    public static Validator dealValidator(int numberOfPlayers, int numberOfCards){
        return new DealValidator(numberOfPlayers, numberOfCards);
    }
}
