package pink.digitally.games.whot.whotcore;

import java.util.Deque;
import java.util.LinkedList;

public class InMemoryBoard implements Board {

    private Deque<WhotCardWithNumberAndShape> playPile;
    private Deque<WhotCardWithNumberAndShape> drawPile;

    public InMemoryBoard() {
        playPile = new LinkedList<>();
    }

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
        playPile.addFirst(whotCards);
    }

    @Override
    public void setDrawPile(Deque<WhotCardWithNumberAndShape> drawPile) {
        this.drawPile = drawPile;
    }

    @Override
    public WhotCardWithNumberAndShape takeFromDrawPile() {
        return this.drawPile.removeFirst();
    }

    @Override
    public WhotCardWithNumberAndShape getTopOfPlayPile() {
        return this.playPile.peekFirst();
    }
}
