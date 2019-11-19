package pink.digitally.games.whot.whotcore.teststub;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class StubPlayer implements Player {

    private ArrayList<WhotCardWithNumberAndShape> objects = new ArrayList<>();

    @Override
    public List<WhotCardWithNumberAndShape> getCards() {
        return objects;
    }

    @Override
    public void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        objects.add(whotCardWithNumberAndShape);
    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void registerMediator(GameMediator mediator) {

    }

    @Override
    public GameMediator getMediator() {
        return null;
    }

    @Override
    public PlayerEvent playEventFunction(Board board) {
        return null;
    }

}
