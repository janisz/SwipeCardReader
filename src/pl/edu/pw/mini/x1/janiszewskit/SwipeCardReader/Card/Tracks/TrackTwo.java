package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

/**
 * Hold information about track2 on
 * magnetic card
 */
public class TrackTwo extends Track {

    /**
     * Maximum length of track according to ISO/IEC 7813
     */
    private final Integer TRACK_LENGTH = 37;

    /**
     * Initialize all fields with data from parameter
     *
     * @param rawTrack raw credit card data
     */
    public TrackTwo(byte[] rawTrack) {
        super(rawTrack);

        String track = "";
        for (byte b : rawTrack) {
            track += (char) b;
        }

        try {

            String[] tracks = track.split("\\?");
            String[] fields = tracks[1].split("=");

            primaryAccountNumber = fields[0].split(";")[1];

            setExpirationDate(fields[1]);

            serviceCode = Integer.parseInt(fields[1].substring(4, 7));
            discretionalData = fields[1].substring(7);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Cannot create Track2 from given data", e.getCause());
        }
    }

}
