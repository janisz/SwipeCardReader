package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardReaderException extends Exception {

    public CardReaderException() {
    }

    public CardReaderException(String message) {
        super(message);
    }

    public CardReaderException(Throwable cause) {
        super(cause);
    }

    public CardReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
