package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.BeforeEach;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.acceptance.actors.GameObserverActor;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;

abstract class AcceptanceTestBase {
    private GameMediatorActor gameMediator;
    private GameObserverActor gameStateObserver;
    Player ngozi;
    Player emeka;
    Player ada;
    WhotGamePlay whotGamePlay;

    @BeforeEach
    void setUp() {
        gameMediator = new GameMediatorActor(playEventHandler());
        gameStateObserver = new GameObserverActor();
    }


    void givenThereIsAWhotGame() {
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


    void whenPlayerPlays(Player player, WhotCard whotCard) {
        player.play(new PlayCardPlayerEvent(whotCard));
    }

    void andTheTopOfPlayPileIs(WhotCard whotCard) {
        gameMediator.setTheTopOfPile(whotCard);
    }

    void andTheGameMediatorWillDeal(List<WhotCard> list, Player player) {
        gameMediator.setPlayerCards(list, player);
    }

    void andTheGameHasStarted() {
        whotGamePlay.startGame();
    }


    void thenTheNumberOfCardsOfPlayer(Player player, int expectedNumberOfCards) {
        assertEquals(expectedNumberOfCards, player.getCards().size());
    }

    void thenTheTopOfThePlayPileIs(WhotCard whotCard) {
        assertEquals(whotCard, whotGamePlay.getBoard().getPlayPile().getFirst());
    }

    void whenPlayerTakesACard(Player player) {
        player.play(new TakeCardPlayerEvent());
    }

    abstract PlayEventHandler playEventHandler();
}
