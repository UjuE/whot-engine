package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCard;
import pink.digitally.games.whot.whotcore.card.WhotCardDeck;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.ChooseShapePlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pink.digitally.games.whot.whotcore.card.WhotCard.whotCard;
import static pink.digitally.games.whot.whotcore.card.WhotNumber.TWENTY;
import static pink.digitally.games.whot.whotcore.card.WhotShape.WHOT;

class AdvancedRulesPlayEventHandlerTest {

    private AdvancedRulesPlayEventHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new AdvancedRulesPlayEventHandler();
    }

    @Test
    void takeCard() {
        TakeCardPlayerEvent playerEvent = new TakeCardPlayerEvent();

        Player thePlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(thePlayer, secondPlayer));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        WhotCardWithNumberAndShape card = mock(WhotCardWithNumberAndShape.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(board.getDrawPile()).thenReturn(WhotCardDeck.getCards());
        when(board.takeFromDrawPile()).thenReturn(card);

        Either<ErrorMessage, Deque<Player>> actualResult = underTest
                .handle(playerEvent, thePlayer, allPlayers, board, gameStateObserver, gameMediator);


        assertAll(
                () -> verify(board).takeFromDrawPile(),
                () -> verify(thePlayer).addCard(card),
                () -> assertTrue(actualResult.isRight(), "Is successful Play"),
                () -> assertEquals(secondPlayer, allPlayers.getFirst(), "The second player is next to play")
        );
    }

    @Test
    void standardPlay() {
        WhotCard cardToPlay = whotCard(WhotNumber.TEN, WhotShape.CIRCLE);
        WhotCard topOfPlayPile = whotCard(WhotNumber.ELEVEN, WhotShape.CIRCLE);
        PlayCardPlayerEvent playerEvent = new PlayCardPlayerEvent(cardToPlay);

        Player thePlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(thePlayer, secondPlayer));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GameMediator gameMediator = mock(GameMediator.class);

        when(thePlayer.getCards()).thenReturn(new ArrayList<>(Collections.singletonList(cardToPlay)));
        when(board.getTopOfPlayPile()).thenReturn(topOfPlayPile);

        Either<ErrorMessage, Deque<Player>> actualResult = underTest
                .handle(playerEvent, thePlayer, allPlayers, board, gameStateObserver, gameMediator);

        assertAll(
                () -> verify(board).addToPlayPile(cardToPlay),
                () -> assertTrue(thePlayer.getCards().isEmpty()),
                () -> assertTrue(actualResult.isRight(), "Is successful Play"),
                () -> assertEquals(secondPlayer, allPlayers.getFirst(), "The second player is next to play")
        );
    }

    @Test
    void continuousPickTwo() {
        WhotCard cardToPlay = whotCard(WhotNumber.TWO, WhotShape.CIRCLE);
        WhotCard anotherCard = whotCard(WhotNumber.EIGHT, WhotShape.CIRCLE);
        WhotCard topOfPlayPile = whotCard(WhotNumber.ELEVEN, WhotShape.CIRCLE);
        PlayCardPlayerEvent playerEvent = new PlayCardPlayerEvent(cardToPlay);

        GameMediator gameMediator = mock(GameMediator.class);
        Player thePlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(thePlayer, secondPlayer));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);

        when(gameMediator.getTotalTakeCount()).thenReturn(2);
        when(thePlayer.getCards()).thenReturn(new ArrayList<>(asList(cardToPlay, anotherCard)));
        when(board.getTopOfPlayPile()).thenReturn(topOfPlayPile);


        Either<ErrorMessage, Deque<Player>> actualResult = underTest
                .handle(playerEvent, thePlayer, allPlayers, board, gameStateObserver, gameMediator);

        assertAll(
                () -> verify(board).addToPlayPile(cardToPlay),
                () -> assertTrue(actualResult.isRight(), "Is successful Play"),
                () -> verify(gameMediator).addTakeCardCount(2),
                () -> verify(gameMediator).nextPlayerValidation(any()),
                () -> assertEquals(secondPlayer, allPlayers.getFirst(), "The second player is next to play")
        );
    }

    @Test
    void chooseShape(){
        WhotShape circleWhotShape = WhotShape.CIRCLE;
        PlayerEvent playerEvent = new ChooseShapePlayerEvent(circleWhotShape);
        GameMediator gameMediator = mock(GameMediator.class);
        Player thePlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(thePlayer, secondPlayer));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);

        when(board.getTopOfPlayPile()).thenReturn(whotCard(TWENTY, WHOT));

        Either<ErrorMessage, Deque<Player>> actualResult = underTest
                .handle(playerEvent, thePlayer, allPlayers, board, gameStateObserver, gameMediator);

        assertAll(
                () -> verify(board, times(0)).addToPlayPile(any()),
                () -> assertTrue(actualResult.isRight(), "Is successful Play"),
                () -> verify(gameMediator).nextPlayerValidation(any()),
                () -> assertEquals(secondPlayer, allPlayers.getFirst(), "The second player is next to play")
        );
    }
}