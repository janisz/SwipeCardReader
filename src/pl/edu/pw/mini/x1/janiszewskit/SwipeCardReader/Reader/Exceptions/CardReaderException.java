package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions;

/**
 * Exceptions trowed by Device
 *
 * @see pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device
 */
public class CardReaderException extends Exception {

    /**
     * Default constructor
     */
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
