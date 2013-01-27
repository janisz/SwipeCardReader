package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card;

/**
 * Enumeration holding popular card vendors
 */
public enum CardType {
    VISA("VISA"),
    MASTERCARD("MasterCard"),
    AMERICANEXPRESS("American Express"),
    DINERSCLUB("Diners Club"),
    DISCOVER("Discover"),
    JCB("JCB"),
    OTHER("other");

    private CardType(final String text) {
        this.text = text;
    }

    private final String text;

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
