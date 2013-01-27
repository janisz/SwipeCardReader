package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card;

import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks.TrackOne;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks.TrackTwo;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions.CardReaderException;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class containing information about card.
 * Could be imagined as a implementation of
 * real plastic card.
 * You can think about CardInfo as a Data
 * Transfer Object
 * <p/>
 * It doesn't contain information about track3
 * since it is not standardized and in most
 * cases empty (filled with 0).
 *
 * @see TrackOne
 * @see TrackTwo
 */
public class CardInfo {

    private TrackOne trackOne;
    private TrackTwo trackTwo;

    /**
     * Create new card from given raw data.
     *
     * @param rawCardData raw card data
     */
    public CardInfo(byte[] rawCardData) throws CardReaderException {

        try {
            trackOne = new TrackOne(rawCardData);
            trackTwo = new TrackTwo(rawCardData);
        } catch (IllegalArgumentException e) {
            throw new CardReaderException("Cannot create CardInfo from given data", e.getCause());
        }
    }

    //Getters

    public TrackOne getTrackOne() {
        return trackOne;
    }

    public TrackTwo getTrackTwo() {
        return trackTwo;
    }

    public Date getExpirationDate() {
        return trackOne.getExpirationDate();
    }

    public String getCardholderName() {
        return MessageFormat.format("{0} {1}", trackOne.getFirstName(), trackOne.getLastName());
    }

    public String getCardNumber() {
        return trackOne.getPrimaryAccountNumber();
    }

    /**
     * @return Card number with replaced the middle digitts
     *         with *, it is also formatted in 4 chars chunks
     *         separated with space.
     */
    public String getMaskedNumber() {
        return getCardNumber().substring(0, 4) + " **** **** " + getCardNumber().substring(12);
    }

    /**
     * @see CardType
     */
    public CardType getCardType() {
        //from http://stackoverflow.com/a/72801/1387612
        String number = trackOne.getPrimaryAccountNumber();

        if (number.matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
            return CardType.VISA;
        } else if (number.matches("^5[1-5][0-9]{14}$")) {
            return CardType.MASTERCARD;
        } else if (number.matches("^3[47][0-9]{13}$")) {
            return CardType.AMERICANEXPRESS;
        } else if (number.matches("^3(?:0[0-5]|[68][0-9])[0-9]{11}$")) {
            return CardType.DINERSCLUB;
        } else if (number.matches("^6(?:011|5[0-9]{2})[0-9]{12}$")) {
            return CardType.DISCOVER;
        } else if (number.matches("^(?:2131|1800|35\\d{3})\\d{11}$")) {
            return CardType.DISCOVER;
        }

        return CardType.OTHER;
    }

    public String toString() {
        DateFormat formatter = new SimpleDateFormat("MM/yy");
        return String.format("Name:  %s\nType:  %s\nDate:  %s\nNumber %s",
                getCardholderName(), getCardType(), formatter.format(getExpirationDate()), getMaskedNumber());
    }

}
