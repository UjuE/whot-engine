package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerActor implements Player {
    private GameMediator mediator;
    private final String playerName;
    private final ArrayList<WhotCardWithNumberAndShape> whotCardWithNumberAndShapes = new ArrayList<>();

    private PlayerActor(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public List<WhotCardWithNumberAndShape> getCards() {
        return whotCardWithNumberAndShapes;
    }

    @Override
    public void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        whotCardWithNumberAndShapes.add(whotCardWithNumberAndShape);
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void registerMediator(GameMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public GameMediator getMediator() {
        return mediator;
    }

    @Override
    public PlayerEvent playEventFunction(Board board) {
        return null;
    }

    @Override
    public void play(Board board) {
        //Do nothing
    }

    public static Player player(String playerName){
        return new PlayerActor(playerName);
    }
}
