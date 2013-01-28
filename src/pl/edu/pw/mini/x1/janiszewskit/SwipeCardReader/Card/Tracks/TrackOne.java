package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks;

import static pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.Tracks.TrackData.*;

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

        try {

            String[] tracks = track.split("\\?");
            String[] fields = tracks[0].split("\\^");

            cardData.put(PRIMARY_ACCOUNT_NUMBER, fields[0].split("B")[1]);

            cardData.put(NAME, fields[1]);
            cardData.put(FIRST_NAME, getName().split("\\/")[1]);
            cardData.put(LAST_NAME, getName().split("\\/")[0]);

            setExpirationDate(fields[2]);

            cardData.put(SERVICE_CODE, Integer.parseInt(fields[2].substring(4, 7)));
            cardData.put(DISCRETIONAL_DATA, fields[2].substring(7));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Cannot create Track1 from given data", e.getCause());
        }
    }

    //Getters

    public String getName() {
        return (String) cardData.get(NAME);
    }

    public String getFirstName() {
        return (String) cardData.get(FIRST_NAME);
    }

    public String getLastName() {
        return (String) cardData.get(LAST_NAME);
    }

}
