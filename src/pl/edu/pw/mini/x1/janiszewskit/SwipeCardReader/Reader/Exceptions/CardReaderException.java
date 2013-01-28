package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions;

/**
 * Exceptions trowed by Device
 *
 * @see pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device
 */
public class CardReaderException extends Exception {

    /**
     * @see Exception#Exception()
     */
    public CardReaderException() {
    }

    /**
     * @see Exception#Exception(String)
     */
    public CardReaderException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(Throwable)
     */
    public CardReaderException(Throwable cause) {
        super(cause);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public CardReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
