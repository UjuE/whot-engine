package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.PlayCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.TakeCardPlayerEvent;
import pink.digitally.games.whot.whotcore.events.action.AllRulesValidPlayCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RobotPlayer implements Player {
    private final String name;

    private ArrayList<WhotCardWithNumberAndShape> whotCardWithNumberAndShapes = new ArrayList<>();
    private GameMediator mediator;

    public RobotPlayer(String name) {
        this.name = name;
    }

    @Override
    public List<WhotCardWithNumberAndShape> getCards() {
        return whotCardWithNumberAndShapes;
    }

    @Override
    public void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        this.whotCardWithNumberAndShapes.add(whotCardWithNumberAndShape);
    }

    @Override
    public String getPlayerName() {
        return name;
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
    public void play(Board board) {
        List<WhotCardWithNumberAndShape> whotCard = this.whotCardWithNumberAndShapes
                .stream()
                .filter(theCard -> AllRulesValidPlayCheck.isValidPlay(theCard, board.getTopOfPlayPile())
                        && getMediator().getNextPlayEventValidator().isValid(new PlayCardPlayerEvent(theCard)))
                .collect(Collectors.toList());

        if (whotCard.isEmpty()) {
            play(new TakeCardPlayerEvent());
        } else {
            play(new PlayCardPlayerEvent(whotCard.get(0)));
        }
    }
}
