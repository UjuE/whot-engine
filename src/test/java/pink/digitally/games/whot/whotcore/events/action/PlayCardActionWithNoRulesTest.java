package pink.digitally.games.whot.whotcore.events.action;

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
import pink.digitally.games.whot.whotcore.playrule.GamePlayRuleDeterminer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Play Action with no rules should")
class PlayCardActionWithNoRulesTest {

    private PlayCardAction underTest;

    @BeforeEach
    void setUp() {
        underTest = new PlayCardAction(new GamePlayRuleDeterminer());
    }

    @Test
    void theSameNumberIsPlayable() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.SQUARE);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.CIRCLE));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver, gameMediator);

        verify(board).addToPlayPile(playedCard);
        assertEquals(playerTwo, actualPlayers.get().peekFirst(), "Expected playerTwo's turn to begin");
    }

    @Test
    void theSameShapeIsPlayable() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.SQUARE);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.FOUR, WhotShape.SQUARE));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver, gameMediator);

        verify(board).addToPlayPile(playedCard);
        assertEquals(playerTwo, actualPlayers.get().peekFirst(), "Expected playerTwo's turn to begin");
    }

    @Test
    void differentShapeAndDifferentNumberIsNotPlayable() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.SQUARE);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.FOUR, WhotShape.CIRCLE));
        Either<ErrorMessage, Deque<Player>> result = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver, gameMediator);

        verify(board, times(0)).addToPlayPile(playedCard);
        assertEquals("Invalid play. Played 'WhotCard{shape=SQUARE, number=EIGHT}'" +
                " on Play Pile with 'WhotCard{shape=CIRCLE, number=FOUR}'",
                result.getLeft().getMessage(), "Expected Error message");
    }

    @Test
    void whotCanAlwaysBePlayed() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.TWENTY, WhotShape.WHOT);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.FOUR, WhotShape.CIRCLE));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver, gameMediator);

        verify(board).addToPlayPile(playedCard);
        assertEquals(playerTwo, actualPlayers.get().peekFirst(), "Expected playerTwo's turn to begin");
    }

    @Test
    void whotCardCanAlwaysBePlayedOn() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.SQUARE);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.TWENTY, WhotShape.WHOT));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver, gameMediator);


        verify(board).addToPlayPile(playedCard);
        assertEquals(playerTwo, actualPlayers.get().peekFirst(), "Expected playerTwo's turn to begin");
    }
}