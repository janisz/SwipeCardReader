package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

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
        firstName = getName().split("\\/")[1];
        lastName = getName().split("\\/")[0];

        setExpirationDate(fields[2]);

        serviceCode = Integer.parseInt(fields[2].substring(4, 7));
        discretionalData = fields[2].substring(7);
    }

    //Getters

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
