package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.ArrayList;
import java.util.List;

public class PlayerActor implements Player {
    private GameMediator mediator;
    private final String playerName;
    private final ArrayList<WhotCardWithNumberAndShape> whotCardWithNumberAndShapes = new ArrayList<>();

    public PlayerActor(String playerName) {
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

    public static Player player(String playerName){
        return new PlayerActor(playerName);
    }
}
