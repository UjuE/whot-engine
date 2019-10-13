package pink.digitally.games.whot.whotcore;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.whotcore.WhotShape.CIRCLE;
import static pink.digitally.games.whot.whotcore.WhotShape.CROSS;
import static pink.digitally.games.whot.whotcore.WhotShape.SQUARE;
import static pink.digitally.games.whot.whotcore.WhotShape.STAR;
import static pink.digitally.games.whot.whotcore.WhotShape.TRIANGLE;

@DisplayName("Whot Deck of Cards")
class WhotDeckTest {

    @ParameterizedTest(name = "has 3 number {0} cards with shapes: circle, triangle and star")
    @ValueSource(strings = {"_4", "_8"})
    void hasThreeCards(String whotCardNumber){
        WhotNumber whotNumber = WhotNumber.valueOf(whotCardNumber);
        assertAll(
                () -> assertEquals(3, WhotCardDeck.getCardsWithNumber(whotNumber).size()),
                () -> assertTrue(asList(CIRCLE, TRIANGLE, STAR)
                        .containsAll(WhotCardDeck.getCardsWithNumber(whotNumber).stream()
                        .map(WhotCard::getShape).collect(Collectors.toList())))

        );
    }

    @ParameterizedTest(name = "has 5 number {0} cards with shapes: circle, triangle, cross, square and star")
    @ValueSource(strings = {"_1", "_2", "_3", "_5", "_7"})
    void hasFiveCards(String whotCardNumber){
        WhotNumber whotNumber = WhotNumber.valueOf(whotCardNumber);
        assertAll(
                () -> assertEquals(5, WhotCardDeck.getCardsWithNumber(whotNumber).size()),
                () -> assertTrue(Matchers.containsInAnyOrder(CIRCLE, TRIANGLE, CROSS, SQUARE, STAR)
                        .matches(WhotCardDeck.getCardsWithNumber(whotNumber).stream()
                        .map(WhotCard::getShape).collect(Collectors.toList())))

        );
    }

    @ParameterizedTest(name = "has 4 number {0} cards with shapes: circle, triangle, cross, square and star")
    @ValueSource(strings = {"_10", "_11", "_13", "_14"})
    void hasFourCards(String whotCardNumber){
        WhotNumber whotNumber = WhotNumber.valueOf(whotCardNumber);
        assertAll(
                () -> assertEquals(4, WhotCardDeck.getCardsWithNumber(whotNumber).size()),
                () -> assertTrue(Matchers.containsInAnyOrder(CIRCLE, TRIANGLE, CROSS, SQUARE)
                        .matches(WhotCardDeck.getCardsWithNumber(whotNumber).stream()
                        .map(WhotCard::getShape).collect(Collectors.toList())))

        );
    }

    @Test
    @DisplayName("has 5 number 20 cards all with the same whot shape")
    void hasFiveWhotCardsWithShapeWhot(){
        assertAll(
                () -> assertEquals(5, WhotCardDeck.getCardsWithNumber(WhotNumber._20).size()),
                () -> assertTrue(WhotCardDeck.getCardsWithNumber(WhotNumber._20).stream()
                        .allMatch(it -> it.getShape().equals(WhotShape.WHOT)))
        );
    }

    @Test
    @DisplayName("has 2 number 12 cards all with shapes: circle and triangle")
    void hasTwoNumberTweleveCards(){
        WhotNumber whotNumber = WhotNumber._12;
        assertAll(
                () -> assertEquals(2, WhotCardDeck.getCardsWithNumber(whotNumber).size()),
                () -> assertTrue(Matchers.containsInAnyOrder(CIRCLE, TRIANGLE)
                        .matches(WhotCardDeck.getCardsWithNumber(whotNumber).stream()
                                .map(WhotCard::getShape).collect(Collectors.toList())))

        );
    }

    @Test
    @DisplayName("has 54 cards")
    void hasFiftyThreeCards(){
        assertEquals(54, WhotCardDeck.getCards().size());
    }
}