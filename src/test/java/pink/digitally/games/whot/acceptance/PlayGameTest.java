package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.NoRulesPlayEventHandler;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;
import static pink.digitally.games.whot.whotcore.WhotCard.whotCard;

@DisplayName("Whot game should")
class PlayGameTest {

    private Player ngozi;
    private Player emeka;
    private WhotGamePlay whotGamePlay;
    private GameMediatorActor gameMediator;

    @BeforeEach
    void setUp() {
        gameMediator = new GameMediatorActor(new NoRulesPlayEventHandler());
    }

    @Test
    @DisplayName("not be set up before the game has started")
    void theGameHasNotBeenStarted() {
        givenThereIsAWhotGame();

        assertAll(
                () -> assertNull(whotGamePlay.nextPlayer(), "The next player should be null"),
                () -> assertTrue(whotGamePlay.getBoard().getPlayPile().isEmpty(), "There should be no Play pile"),
                () -> assertTrue(whotGamePlay.getBoard().getDrawPile().isEmpty(), "There should be no Draw Pile"),
                () -> assertEquals(GameState.NOT_STARTED, whotGamePlay.getGameState())
        );
    }

    @Test
    @DisplayName("be in the correct state when the game has started")
    void theGameIsInTheExpectedState() {
        givenThereIsAWhotGame();
        andTheGameHasStarted();

        assertAll(
                () -> assertEquals(ngozi, whotGamePlay.nextPlayer()),
                () -> assertFalse(whotGamePlay.getBoard().getPlayPile().isEmpty()),
                () -> assertFalse(whotGamePlay.getBoard().getDrawPile().isEmpty()),
                () -> assertEquals(GameState.STARTED, whotGamePlay.getGameState())
        );
    }

    @Test
    @DisplayName("allow players play card on play pile after the game has started")
    void givenThatTheGameHasStartedAPlayerPlays() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.SQUARE));
    }

    @Test
    @DisplayName("allow players take cards from draw pile after the game has started")
    void givenThatTheGameHasStartedAPlayerTakesACard() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesACard(ngozi);

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));
        thenTheNumberOfCardsOfPlayer(ngozi, 2);
    }

    @Test
    @DisplayName("allow only the current player play a turn")
    void onlyTheCurrentPlayerCanPlay() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerTakesACard(emeka);

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.TRIANGLE));
        thenTheNumberOfCardsOfPlayer(emeka, 1);
    }

    @Test
    @DisplayName("allow only valid cards to be played on the play pile")
    void onlyValidCardsCanBePlayed() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FOUR, WhotShape.TRIANGLE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        thenTheTopOfThePlayPileIs(whotCard(WhotNumber.FOUR, WhotShape.TRIANGLE));
    }

    private void thenTheNumberOfCardsOfPlayer(Player player, int expectedNumberOfCards) {
        assertEquals(expectedNumberOfCards, player.getCards().size());
    }

    private void thenTheTopOfThePlayPileIs(WhotCard whotCard) {
        assertEquals(whotCard, whotGamePlay.getBoard().getPlayPile().getFirst());
    }

    private void whenPlayerPlays(Player player, WhotCard whotCard) {
        player.play(new PlayCardPlayerEvent(whotCard));
    }

    private void whenPlayerTakesACard(Player player) {
        player.play(new TakeCardPlayerEvent());
    }

    private void andTheTopOfPlayPileIs(WhotCard whotCard) {
        gameMediator.setTheTopOfPile(whotCard);
    }

    private void andTheGameMediatorWillDeal(List<WhotCard> list, Player player) {
        gameMediator.setPlayerCards(list, player);
    }

    private void andTheGameHasStarted() {
        whotGamePlay.startGame();
    }

    private void givenThereIsAWhotGame() {
        ngozi = player("Ngozi");
        emeka = player("Emeka");
        whotGamePlay =  new WhotGamePlay.Builder()
                .withBoard(new BoardActor())
                .withDeckOfCards()
                .withGameMediator(gameMediator)
                .withDeckOfCards()
                .withPlayers(ngozi, emeka)
                .build();
    }
}
