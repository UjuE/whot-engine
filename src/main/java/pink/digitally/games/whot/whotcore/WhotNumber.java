package pink.digitally.games.whot.whotcore;

public enum WhotNumber {
    _1(1), _2(2), _3(3),
    _4(4), _5(5), _7(7),
    _8(8), _10(10), _11(11),
    _12(12), _13(13), _14(14),
    _20(20);

    private int numericValue;

    WhotNumber(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
