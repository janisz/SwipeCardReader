package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks.TrackData.*;

/**
 * Parent class for tracks. Contain all
 * standard fields.
 */
public abstract class Track {

    /**
     * raw card data passed in constructor
     */
    protected byte[] rawData;

    /**
     * Set holding card track data
     */
    protected HashMap<TrackData, Object> cardData = new HashMap<TrackData, Object>();


    protected Track(byte[] rawData) {
        this.rawData = rawData;
    }

    /**
     * @return string containing all
     *         not null properties with
     *         labels. Each property is
     *         in own line.
     *         <p/>
     *         Date is formatted as on real card
     */
    public String toString() {

        String result = "";
        if (cardData.containsKey(NAME))
            result += "Name: " + cardData.get(NAME) + "\n";
        if (getPrimaryAccountNumber() != null)
            result += "PAN:  " + getPrimaryAccountNumber() + "\n";
        if (getExpirationDate() != null) {
            DateFormat formatter = new SimpleDateFormat("MM/yy");
            result += "Date: " + formatter.format(getExpirationDate()) + "\n";
        }
        if (getServiceCode() != null)
            result += "Code: " + getServiceCode() + "\n";
        if (getDiscretionalData() != null)
            result += "Data: " + getDiscretionalData() + "\n";
        return result;
    }

    /**
     * Convert string started with date to
     * <code>expirationDate</code>
     *
     * @param string expiration date
     */
    protected void setExpirationDate(String string) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyMM");
            String date = string.substring(0, 4);
            cardData.put(EXPIRATION_DATE, (Date) formatter.parse(date));
        } catch (ParseException e) {
            //ignore error, just create default date instead
            cardData.put(EXPIRATION_DATE, new Date());
        }
    }

    //Getters

    public byte[] getRawData() {
        return rawData;
    }

    public String getPrimaryAccountNumber() {
        return (String) cardData.get(PRIMARY_ACCOUNT_NUMBER);
    }

    public Date getExpirationDate() {
        return (Date) cardData.get(EXPIRATION_DATE);
    }

    public Integer getServiceCode() {
        return (Integer) cardData.get(SERVICE_CODE);
    }

    public String getDiscretionalData() {
        return (String) cardData.get(DISCRETIONAL_DATA);
    }
}
