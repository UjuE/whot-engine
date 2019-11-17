package pink.digitally.games.whot.whotcore.card;

import java.util.Objects;

public class WhotCard implements WhotCardWithNumberAndShape {
    private final WhotShape shape;
    private final WhotNumber number;

    private WhotCard(WhotNumber number, WhotShape shape) {
        this.shape = shape;
        this.number = number;
    }

    public WhotNumber getNumber(){
        return number;
    }
    public WhotShape getShape(){
        return shape;
    }

    @Override
    public String toString() {
        return "WhotCard{" +
                "shape=" + shape +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhotCard whotCard = (WhotCard) o;
        return getShape() == whotCard.getShape() &&
                getNumber() == whotCard.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShape(), getNumber());
    }

    public static WhotCard whotCard(WhotNumber whotNumber, WhotShape whotShape){
        return new WhotCard(whotNumber, whotShape);
    }
}
