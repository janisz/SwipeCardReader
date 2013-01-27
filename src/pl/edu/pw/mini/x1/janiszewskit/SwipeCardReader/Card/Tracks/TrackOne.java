package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

/**
 * Hold information about track1 on
 * magnetic card
 */
public class TrackOne extends Track {

    /**
     * Maximum length of track according to ISO/IEC 7813
     */
    private final Integer TRACK_LENGTH = 76;

    /**
     * Initialize all fields with data from parameter
     *
     * @param rawTrack raw credit card data
     */
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
