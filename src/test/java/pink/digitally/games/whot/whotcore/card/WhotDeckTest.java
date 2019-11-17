package pink.digitally.games.whot.whotcore.card;

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
import static pink.digitally.games.whot.whotcore.card.WhotShape.CIRCLE;
import static pink.digitally.games.whot.whotcore.card.WhotShape.CROSS;
import static pink.digitally.games.whot.whotcore.card.WhotShape.SQUARE;
import static pink.digitally.games.whot.whotcore.card.WhotShape.STAR;
import static pink.digitally.games.whot.whotcore.card.WhotShape.TRIANGLE;

@DisplayName("Whot Deck of Cards")
class WhotDeckTest {

    @ParameterizedTest(name = "has 3 number {0} cards with shapes: circle, triangle and star")
    @ValueSource(strings = {"FOUR", "EIGHT"})
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
    @ValueSource(strings = {"ONE", "TWO", "THREE", "FIVE", "SEVEN"})
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
    @ValueSource(strings = {"TEN", "ELEVEN", "THIRTEEN", "FOURTEEN"})
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
                () -> assertEquals(5, WhotCardDeck.getCardsWithNumber(WhotNumber.TWENTY).size()),
                () -> assertTrue(WhotCardDeck.getCardsWithNumber(WhotNumber.TWENTY).stream()
                        .allMatch(it -> it.getShape().equals(WhotShape.WHOT)))
        );
    }

    @Test
    @DisplayName("has 2 number 12 cards all with shapes: circle and triangle")
    void hasTwoNumberTweleveCards(){
        WhotNumber whotNumber = WhotNumber.TWELVE;
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