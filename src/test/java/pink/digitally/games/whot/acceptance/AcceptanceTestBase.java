package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.BeforeEach;
import pink.digitally.games.whot.acceptance.actors.BoardActor;
import pink.digitally.games.whot.acceptance.actors.GameMediatorActor;
import pink.digitally.games.whot.acceptance.actors.GameObserverActor;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.RobotPlayer;
import pink.digitally.games.whot.whotcore.WhotGamePlay;
import pink.digitally.games.whot.whotcore.card.WhotCard;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.events.ChooseShapePlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.List;

import static pink.digitally.games.whot.acceptance.actors.PlayerActor.player;

abstract class AcceptanceTestBase {
    private GameMediatorActor gameMediator;
    GameObserverActor gameStateObserver;
    Player obinna;
    Player onyinye;
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
        onyinye = player("Onyinye");
        obinna = new RobotPlayer("Obinna");
        ngozi = player("Ngozi");
        emeka = player("Emeka");
        ada = player("Ada");
        whotGamePlay =  new WhotGamePlay.Builder()
                .withBoard(new BoardActor())
                .withDeckOfCards()
                .withGameMediator(gameMediator)
                .withPlayers(ngozi, emeka, ada, onyinye, obinna)
                .withGameStateObserver(gameStateObserver)
                .build();
    }


    void whenPlayerPlays(Player player, WhotCard whotCard) {
        player.play(new PlayCardPlayerEvent(whotCard));
    }

    void whenPlayerChooses(Player player, WhotShape whotShape) {
        player.play(new ChooseShapePlayerEvent(whotShape));
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


    void whenPlayerTakesCard(Player player) {
        player.play(new TakeCardPlayerEvent());
    }

    abstract PlayEventHandler playEventHandler();
}
