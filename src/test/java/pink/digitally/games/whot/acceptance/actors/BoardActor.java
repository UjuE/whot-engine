package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

import java.util.Deque;
import java.util.LinkedList;

public class BoardActor implements Board {

    private Deque<WhotCardWithNumberAndShape> playPile = new LinkedList<>();
    private Deque<WhotCardWithNumberAndShape> drawPile = new LinkedList<>();

    @Override
    public Deque<WhotCardWithNumberAndShape> getPlayPile() {
        return playPile;
    }

    @Override
    public Deque<WhotCardWithNumberAndShape> getDrawPile() {
        return drawPile;
    }

    @Override
    public void addToPlayPile(WhotCardWithNumberAndShape whotCards) {
        this.playPile.addFirst(whotCards);
    }

    @Override
    public void setDrawPile(Deque<WhotCardWithNumberAndShape> whotCards) {
        this.drawPile = whotCards;
    }

    @Override
    public WhotCardWithNumberAndShape takeFromDrawPile() {
        return drawPile.removeFirst();
    }

    @Override
    public WhotCardWithNumberAndShape getTopOfPlayPile() {
        return playPile.peekFirst();
    }
}
