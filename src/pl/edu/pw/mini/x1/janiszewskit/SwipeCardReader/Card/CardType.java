package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card;

/**
 * User: root
 * Date: 1/26/13
 * Time: 11:36 PM
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

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
