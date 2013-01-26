package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

/**
 * User: root
 * Date: 1/26/13
 * Time: 7:58 PM
 */
public class TrackTwo extends Track {

    private final Integer TRACK_LENGTH = 37;

    public TrackTwo(byte[] rawTrack) {
        super(rawTrack);

        String track = "";
        for (byte b : rawTrack) {
            track += (char) b;
        }

        String[] tracks = track.split("\\?");
        String[] fields = tracks[1].split("=");

        primaryAccountNumber = fields[0].split(";")[1];

        setExpirationDate(fields[1]);

        serviceCode = Integer.parseInt(fields[1].substring(4, 7));
        discretionalData = fields[1].substring(7);
    }

}
