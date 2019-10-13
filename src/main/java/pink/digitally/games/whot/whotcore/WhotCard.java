package pink.digitally.games.whot.whotcore;

class WhotCard {
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

    static WhotCard whotCardDetails(WhotNumber whotNumber, WhotShape whotShape){
        return new WhotCard(whotNumber, whotShape);
    }
}
