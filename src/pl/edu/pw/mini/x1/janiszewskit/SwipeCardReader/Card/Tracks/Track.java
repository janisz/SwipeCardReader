package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: root
 * Date: 1/26/13
 * Time: 9:39 PM
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
