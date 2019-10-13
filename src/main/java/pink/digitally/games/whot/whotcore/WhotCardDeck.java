package pink.digitally.games.whot.whotcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.WhotNumber._1;
import static pink.digitally.games.whot.whotcore.WhotNumber._10;
import static pink.digitally.games.whot.whotcore.WhotNumber._11;
import static pink.digitally.games.whot.whotcore.WhotNumber._12;
import static pink.digitally.games.whot.whotcore.WhotNumber._13;
import static pink.digitally.games.whot.whotcore.WhotNumber._14;
import static pink.digitally.games.whot.whotcore.WhotNumber._2;
import static pink.digitally.games.whot.whotcore.WhotNumber._20;
import static pink.digitally.games.whot.whotcore.WhotNumber._3;
import static pink.digitally.games.whot.whotcore.WhotNumber._4;
import static pink.digitally.games.whot.whotcore.WhotNumber._5;
import static pink.digitally.games.whot.whotcore.WhotNumber._7;
import static pink.digitally.games.whot.whotcore.WhotNumber._8;
import static pink.digitally.games.whot.whotcore.WhotShape.CIRCLE;
import static pink.digitally.games.whot.whotcore.WhotShape.CROSS;
import static pink.digitally.games.whot.whotcore.WhotShape.SQUARE;
import static pink.digitally.games.whot.whotcore.WhotShape.STAR;
import static pink.digitally.games.whot.whotcore.WhotShape.TRIANGLE;

public enum WhotCardDeck {
    ONE(_1, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    TWO(_2, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    THREE(_3, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    FOUR(_4, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, STAR)), 1),
    FIVE(_5, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    SEVEN(_7, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)), 1),
    EIGHT(_8, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, STAR)), 1),
    TEN(_10, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    ELEVEN(_11, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    TWELVE(_12, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE)), 1),
    THIRTEEN(_13, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    FOURTEEN(_14, new HashSet<>(Arrays.asList(CIRCLE, TRIANGLE, CROSS, SQUARE)), 1),
    WHOT(_20, Collections.singleton(WhotShape.WHOT), 5);

    private final WhotNumber whotNumber;
    private final Set<WhotShape> possibleShapes;
    private final int numberOfOccurancesEach;

    WhotCardDeck(WhotNumber whotNumber, Set<WhotShape> possibleShapes, int numberOfOccurancesEach) {
        this.whotNumber = whotNumber;
        this.possibleShapes = possibleShapes;
        this.numberOfOccurancesEach = numberOfOccurancesEach;
    }

    public static List<WhotCard> getCardsWithNumber(WhotNumber whotNumber){
        List<WhotCard> whotCardDetails = new ArrayList<>();
        WhotCardDeck whotCardDeck = Stream.of(values())
                .filter(it -> whotNumber.equals(it.whotNumber))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("How can this happen"));

        Set<WhotShape> possibleShapes = whotCardDeck.possibleShapes;

        for (int i = 0; i < whotCardDeck.numberOfOccurancesEach; i++) {
            whotCardDetails.addAll(possibleShapes.stream()
                    .map(shape -> WhotCard.whotCardDetails(whotNumber, shape))
                    .collect(Collectors.toList()));
        }

        return whotCardDetails;
    }

    public static List<WhotCard> getCards(){
        return Stream.of(values())
                .flatMap(it -> WhotCardDeck.getCardsWithNumber(it.whotNumber).stream())
                .collect(Collectors.toList());
    }
}
