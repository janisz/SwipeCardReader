package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parent class for tracks. Contain all
 * standard fields.
 */
public abstract class Track {

    protected byte[] rawData;

    protected String name;
    protected String firstName;
    protected String lastName;
    protected String primaryAccountNumber;
    protected Date expirationDate;
    protected Integer serviceCode;
    protected String discretionalData;


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
        if (name != null)
            result += "Name: " + name + "\n";
        if (getPrimaryAccountNumber() != null)
            result += "PAN:  " + getPrimaryAccountNumber() + "\n";
        if (getExpirationDate() != null) {
            DateFormat formatter = new SimpleDateFormat("MM/yy");
            result += "Date: " + formatter.format(expirationDate) + "\n";
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
            expirationDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            //ignore error, just create default date instead
            expirationDate = new Date();
        }
    }

    //Getters

    public byte[] getRawData() {
        return rawData;
    }

    public String getPrimaryAccountNumber() {
        return primaryAccountNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }

    public String getDiscretionalData() {
        return discretionalData;
    }
}
