package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Tracks;

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
        if (primaryAccountNumber != null)
            result += "PAN:  " + primaryAccountNumber + "\n";
        if (expirationDate != null)
            result += "Date: " + expirationDate + "\n";
        if (serviceCode != null)
            result += "Code: " + serviceCode + "\n";
        if (discretionalData != null)
            result += "Data: " + discretionalData + "\n";
        return result;
    }


}
