package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.acceptance.actors.GameObserverActor;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.StandardRulesPlayEventHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;
import static pink.digitally.games.whot.whotcore.WhotCard.whotCard;

@DisplayName("Whot game with standard rules should")
class StandardRulesPlayGameTest {
    private Player ngozi;
    private Player emeka;
    private Player ada;
    private WhotGamePlay whotGamePlay;
    private GameMediatorActor gameMediator;
    private GameObserverActor gameStateObserver;

    @BeforeEach
    void setUp() {
        gameMediator = new GameMediatorActor(new StandardRulesPlayEventHandler());
        gameStateObserver = new GameObserverActor();
    }

    @Test
    @DisplayName("allow the current player another turn when 1 is played")
    void holdOn() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.ONE, WhotShape.SQUARE),
                whotCard(WhotNumber.TWO, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT)), ada);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.ONE, WhotShape.SQUARE));

        assertEquals(ngozi, whotGamePlay.nextPlayer());
    }

    @Test
    @DisplayName("make the next player pick 2 and skip a turn when the current player plays 2")
    void pickTwo() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.TWO, WhotShape.SQUARE),
                whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT)), ada);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.TWO, WhotShape.SQUARE));

        assertEquals(3, emeka.getCards().size());
        assertEquals(ada.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }

    @Test
    @DisplayName("Skip the next player when 8 is played")
    void suspension() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.TWO, WhotShape.SQUARE),
                whotCard(WhotNumber.EIGHT, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT)), ada);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        assertEquals(1, emeka.getCards().size());
        assertEquals(ada.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }

    @Test
    @DisplayName("Make all players but the current player take 1 card from the draw pile")
    void generalMarket() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.FOURTEEN, WhotShape.SQUARE),
                whotCard(WhotNumber.EIGHT, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT)), ada);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.FIVE, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FOURTEEN, WhotShape.SQUARE));

        assertEquals(2, emeka.getCards().size());
        assertEquals(2, ada.getCards().size());
        assertEquals(ngozi.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }

    @Test
    @DisplayName("give the current player another turn when 20 is played")
    void whotCardIsPlayed() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT),
                whotCard(WhotNumber.TWO, WhotShape.SQUARE)), ngozi);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE)), emeka);
        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TWENTY, WhotShape.WHOT)), ada);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.TWENTY, WhotShape.WHOT));

        assertEquals(ngozi.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }


    private void givenThereIsAWhotGame() {
        ngozi = player("Ngozi");
        emeka = player("Emeka");
        ada = player("Ada");
        whotGamePlay =  new WhotGamePlay.Builder()
                .withBoard(new BoardActor())
                .withDeckOfCards()
                .withGameMediator(gameMediator)
                .withPlayers(ngozi, emeka, ada)
                .withGameStateObserver(gameStateObserver)
                .build();
    }

    private void whenPlayerPlays(Player player, WhotCard whotCard) {
        player.play(new PlayCardPlayerEvent(whotCard));
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
}
