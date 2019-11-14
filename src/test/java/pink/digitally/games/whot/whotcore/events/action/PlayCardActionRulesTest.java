package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.GamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.GamePlayRuleDeterminer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Play Action with rules should")
class PlayCardActionRulesTest {

    private PlayCardAction underTest;
    private GamePlayRuleDeterminer gamePlayRuleDeterminer;

    @BeforeEach
    void setUp() {
        gamePlayRuleDeterminer = mock(GamePlayRuleDeterminer.class);
        underTest = new PlayCardAction(gamePlayRuleDeterminer);
    }

    @Test
    void playActionDelegatesPlayToGamePlayRule() {
        WhotCard playedCard = WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.SQUARE);

        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        LinkedList<Player> allPlayers = new LinkedList<>(asList(playerOne, playerTwo));
        Board board = mock(Board.class);
        GameStateObserver gameStateObserver = mock(GameStateObserver.class);
        GamePlayRule gamePlayRule = mock(GamePlayRule.class);

        when(gamePlayRuleDeterminer.determineRuleToApply(any())).thenReturn(gamePlayRule);
        when(gamePlayRule.play(any(), any(), any(), any(), any())).thenReturn(allPlayers);

        when(board.getTopOfPlayPile()).thenReturn(WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.CIRCLE));
        Either<ErrorMessage, Deque<Player>> actualPlayers = underTest.handle(Optional.of(playedCard),
                playerOne,
                allPlayers,
                board, gameStateObserver);

        verify(gamePlayRuleDeterminer, times(1)).determineRuleToApply(playedCard);
        verify(gamePlayRule, times(1)).play(playedCard, playerOne, allPlayers, board, gameStateObserver);
        assertEquals(allPlayers, actualPlayers.get(), "Expected the list returned from gamePlayRuleDeterminer");
    }
}