package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * User: root
 * Date: 1/27/13
 * Time: 7:31 PM
 */
public class GenerateCardNumber {

    /**
     * Sample numbers from http://www.getcreditcardnumbers.com/
     */
    private static final String[] MASTER_CARD = {"5595891562161747", "5146243276525763",
            "5200792801359059", "5315030190249595",
            "5598484283315375", "5298893634242026"};
    /**
     * Sample numbers from http://www.getcreditcardnumbers.com/
     */
    private static final String[] VISA = {"4539157539279281", "4556714716619651",
            "4556852446891321", "4916065452238239",
            "4716080481146834", "4556429513975764"};

    private static Random random = new Random();

    /**
     * @return "random" card number
     */
    public static String nextNumber() {
        if (random.nextBoolean())
            return nextVisa();
        return nextMastercard();
    }

    /**
     * @return "random" Visa card number
     */
    public static String nextVisa() {
        return VISA[random.nextInt(VISA.length)];
    }

    /**
     * @return "random" MasterCard card number
     */
    public static String nextMastercard() {
        return MASTER_CARD[random.nextInt(MASTER_CARD.length)];
    }

    public static String nextDate() {
        DateFormat formatter = new SimpleDateFormat("yyMM");
        return formatter.format(new Date(random.nextLong()));

    }


}
