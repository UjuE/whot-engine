package pink.digitally.games.whot.whotcore;

public enum WhotNumber {
    ONE(1), TWO(2), THREE(3),
    FOUR(4), FIVE(5), SEVEN(7),
    EIGHT(8), TEN(10), ELEVEN(11),
    TWELVE(12), THIRTEEN(13), FOURTEEN(14),
    TWENTY(20);

    private int numericValue;

    WhotNumber(int numericValue) {
        this.numericValue = numericValue;
    }

    @SuppressWarnings("unused")
    public int getNumericValue() {
        return numericValue;
    }
}
