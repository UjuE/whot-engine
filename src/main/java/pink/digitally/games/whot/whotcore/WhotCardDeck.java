package pink.digitally.games.whot.whotcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.WhotNumber.TWENTY;
import static pink.digitally.games.whot.whotcore.WhotShape.CIRCLE;
import static pink.digitally.games.whot.whotcore.WhotShape.CROSS;
import static pink.digitally.games.whot.whotcore.WhotShape.SQUARE;
import static pink.digitally.games.whot.whotcore.WhotShape.STAR;
import static pink.digitally.games.whot.whotcore.WhotShape.TRIANGLE;

public enum WhotCardDeck {
    ONE(WhotNumber.ONE, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    TWO(WhotNumber.TWO, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    THREE(WhotNumber.THREE, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    FOUR(WhotNumber.FOUR, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, STAR)), 1),
    FIVE(WhotNumber.FIVE, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    SEVEN(WhotNumber.SEVEN, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    EIGHT(WhotNumber.EIGHT, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, STAR)), 1),
    TEN(WhotNumber.TEN, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    ELEVEN(WhotNumber.ELEVEN, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    TWELVE(WhotNumber.TWELVE, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE)), 1),
    THIRTEEN(WhotNumber.THIRTEEN, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    FOURTEEN(WhotNumber.FOURTEEN, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    WHOT(TWENTY, Collections.singleton(WhotShape.WHOT), 5);

    private final WhotNumber whotNumber;
    private final Set<WhotShape> possibleShapes;
    private final int numberOfOccurancesEach;

    WhotCardDeck(WhotNumber whotNumber, Set<WhotShape> possibleShapes, int numberOfOccurancesEach) {
        this.whotNumber = whotNumber;
        this.possibleShapes = possibleShapes;
        this.numberOfOccurancesEach = numberOfOccurancesEach;
    }

    static List<WhotCard> getCardsWithNumber(WhotNumber whotNumber) {
        List<WhotCard> whotCardDetails = new ArrayList<>();
        WhotCardDeck whotCardDeck = Stream.of(values())
                .filter(it -> whotNumber.equals(it.whotNumber))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("How can this happen"));

        Set<WhotShape> possibleShapes = whotCardDeck.possibleShapes;

        for (int i = 0; i < whotCardDeck.numberOfOccurancesEach; i++) {
            whotCardDetails.addAll(possibleShapes.stream()
                    .map(shape -> WhotCard.whotCard(whotNumber, shape))
                    .collect(Collectors.toList()));
        }

        return whotCardDetails;
    }

    public static Queue<WhotCardWithNumberAndShape> getCards() {
        return Stream.of(values())
                .flatMap(it -> WhotCardDeck.getCardsWithNumber(it.whotNumber).stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
