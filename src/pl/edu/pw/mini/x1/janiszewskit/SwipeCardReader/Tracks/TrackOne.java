package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Tracks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: root
 * Date: 1/26/13
 * Time: 7:58 PM
 */
public class TrackOne extends Track {

    private final Integer TRACK_LENGTH = 76;

    public TrackOne(byte[] rawTrack) {
        super(rawTrack);

        String track = "";
        for (byte b : rawTrack) {
            track += (char) b;
        }

        String[] tracks = track.split("\\?");
        String[] fields = tracks[0].split("\\^");

        primaryAccountNumber = fields[0].split("B")[1];

        name = fields[1];
        firstName = name.split("\\/")[1];
        lastName = name.split("\\/")[0];

        try {
            DateFormat formatter = new SimpleDateFormat("yyMM");
            String date = fields[2].substring(0, 4);
            expirationDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            expirationDate = new Date();
            //ignore error, just create default date instead
        }

        serviceCode = Integer.parseInt(fields[2].substring(4, 7));
        discretionalData = fields[2].substring(7);
    }

}
