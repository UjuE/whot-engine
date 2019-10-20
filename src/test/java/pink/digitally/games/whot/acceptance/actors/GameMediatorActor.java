package pink.digitally.games.whot.acceptance.actors;

import io.vavr.control.Either;
import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.PlayEventHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class GameMediatorActor extends GameMediator {
    private List<Pair<Player, List<WhotCard>>> injectedPlayersCards = new ArrayList<>();
    private WhotCard topOfPile;

    public GameMediatorActor(PlayEventHandler playEventHandler) {
        super(playEventHandler);
    }

    public void setPlayerCards(List<WhotCard> whotCards, Player player) {
        injectedPlayersCards.add(Pair.create(player, whotCards));
    }

    public void setTheTopOfPile(WhotCard whotCard) {
        this.topOfPile = whotCard;
    }

    @Override
    public Either<String, Void> deal(Deque<WhotCardWithNumberAndShape> cards) {
        if(!injectedPlayersCards.isEmpty()){
            injectedPlayersCards
                    .forEach(it -> it.getSecond().forEach(card -> it.getFirst().addCard(card)));

            injectedPlayersCards
                    .forEach(it -> cards.removeAll(it.getSecond()));
            return Either.right(null);
        }
        return super.deal(cards);
    }

    @Override
    public void updatePlayPile(Deque<WhotCardWithNumberAndShape> whotCards) {
        if(Objects.nonNull(topOfPile)){
            getBoard().addToPlayPile(topOfPile);
        } else {
            super.updatePlayPile(whotCards);
        }
    }
}
