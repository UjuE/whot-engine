package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCard;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;

import java.util.Deque;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("No Rules Play Event Handler")
class NoRulesPlayEventHandlerTest {

    private NoRulesPlayEventHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new NoRulesPlayEventHandler();
    }

    @Test
    @DisplayName("Play a card event")
    void playACardEvent() {
        Player firstPlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        WhotCard cardPlayed = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.CIRCLE);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.THIRTEEN, WhotShape.CIRCLE));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(new PlayCardPlayerEvent(cardPlayed),
                firstPlayer, new LinkedList<>(asList(firstPlayer, secondPlayer)), board, gameStateObserver, mock(GameMediator.class));

        assertAll(
                () -> assertTrue(actualPlayers.isRight()),
                () -> assertEquals(2, actualPlayers.get().size()),
                () -> assertEquals(secondPlayer, actualPlayers.get().peekFirst())
        );

        verify(board).addToPlayPile(cardPlayed);
    }

    @Test
    @DisplayName("Take a card event")
    void takeACardEvent() {
        Player firstPlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        Board board = mock(Board.class);
        WhotCard cardTaken = mock(WhotCard.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);

        when(board.takeFromDrawPile()).thenReturn(cardTaken);

        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(new TakeCardPlayerEvent(),
                firstPlayer, new LinkedList<>(asList(firstPlayer, secondPlayer)), board, gameStateObserver,
                mock(GameMediator.class));

        assertAll(
                () -> assertTrue(actualPlayers.isRight()),
                () -> assertEquals(2, actualPlayers.get().size()),
                () -> assertEquals(secondPlayer, actualPlayers.get().peekFirst())
        );

        verify(firstPlayer).addCard(cardTaken);
    }
}