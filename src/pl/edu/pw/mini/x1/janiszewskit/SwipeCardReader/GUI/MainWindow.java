package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.GUI;

import com.ezware.dialog.task.CommandLink;
import com.ezware.dialog.task.TaskDialogs;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.CardInfo;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.CardType;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions.CardReaderException;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Utils.GenerateCardNumber;
import uk.co.halfninja.randomnames.Gender;
import uk.co.halfninja.randomnames.NameGenerator;
import uk.co.halfninja.randomnames.NameGenerators;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Basic demonstration for code
 */
public class MainWindow {

    private final static int EXIT_PROGRAM = 0;
    private final static int WORK_WITH_FAKE_DATA = 1;

    private JTextField textRawData;
    private JTextField textCardNumber;
    private JTextField textCardHolder;
    private JTextField textExpirationData;
    private JButton buttonReadData;
    private JCheckBox checkBoxSanitizeData;
    private JTextField textServiceCode;
    private JPanel panel;
    private JProgressBar progressBar;
    private JLabel cardBrand;
    private Device device;
    private CardInfo card;

    private ImageIcon iconVisa;
    private ImageIcon iconMastercard;

    /**
     * Initialize ActionListeners
     */
    public MainWindow() {

        checkBoxSanitizeData.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        showCardInfo(checkBoxSanitizeData.isSelected());
                    }
                }
        );

        buttonReadData.addActionListener(

                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        progressBar.setVisible(true);
                        buttonReadData.setEnabled(false);

                        Thread worker = new Thread() {

                            public void run() {

                                try {

                                    device = new Device();
                                    byte[] data = device.read();
                                    card = new CardInfo(data);
                                } catch (CardReaderException e) {

                                    TaskDialogs.showException(e);

                                    int choice = TaskDialogs.choice(

                                            findActiveFrame(),
                                            "What do you want to do ?",
                                            "It looks like there were some errors.",
                                            WORK_WITH_FAKE_DATA,
                                            new CommandLink("Exit",
                                                    "Close application."),
                                            new CommandLink("Work on fake data",
                                                    "Play with some random data to see how application is working.")
                                    );

                                    if (choice == EXIT_PROGRAM)
                                        System.exit(0);
                                    else if (choice == WORK_WITH_FAKE_DATA)
                                        card = generateFakeCard();
                                } finally {

                                    try {
                                        device.close();
                                    } catch (CardReaderException e) {
                                        //Ignore possible errors
                                    } catch (NullPointerException e) {
                                        //Ignore possible errors
                                    }

                                    progressBar.setVisible(false);
                                    buttonReadData.setEnabled(true);
                                }

                                showCardInfo(checkBoxSanitizeData.isSelected());
                            }
                        };

                        worker.start();
                    }
                }
        );
    }

    /**
     * Display information about card
     *
     * @param secure decide if card number can be showed
     */
    public void showCardInfo(boolean secure) {

        DateFormat formatter = new SimpleDateFormat("MM/yy");

        textRawData.setText(new String(card.getTrackOne().getRawData()));
        textCardHolder.setText(card.getCardholderName());
        textCardNumber.setText(card.getMaskedNumber());
        textExpirationData.setText(formatter.format(card.getExpirationDate()));
        textExpirationData.setToolTipText(card.getExpirationDate().toString());
        textServiceCode.setText(card.getTrackOne().getServiceCode().toString());

        cardBrand.setText(card.getCardType().toString());

        java.net.URL where = null;

        try {

            if (card.getCardType().equals(CardType.VISA)) {

                where = new URL("http://www.rowery-rybczynski.pl/themes/sklep/img/logo_paiement_visa.jpg");
                iconVisa = iconVisa == null ? new ImageIcon(where) : iconVisa;
                cardBrand.setIcon(iconVisa);
            } else if (card.getCardType().equals(CardType.MASTERCARD)) {

                where = new URL("http://www.rowery-rybczynski.pl/themes/sklep/img/logo_paiement_mastercard.jpg");
                iconMastercard = iconMastercard == null ? new ImageIcon(where) : iconMastercard;
                cardBrand.setIcon(iconMastercard);
            }

            cardBrand.setText("");
        } catch (MalformedURLException e) {
            //ignore
        }


        if (secure)
            textCardNumber.setText(card.getCardNumber());
    }

    /**
     * Main method Launch window
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @return active window
     */
    private static Frame findActiveFrame() {
        Frame[] frames = JFrame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            Frame frame = frames[i];
            if (frame.isVisible()) {
                return frame;
            }
        }
        return null;
    }

    /**
     * Prepare random card data
     *
     * @return random CardInfo
     */
    private CardInfo generateFakeCard() {

        CardInfo c = null;
        NameGenerator generator = NameGenerators.standardGenerator();
        String s = "N'%B" +
                GenerateCardNumber.nextNumber() +
                "^" + generator.generate(Gender.male).getFamilyName().toUpperCase() + "/" + generator.generate(Gender.male).getGivenName().toUpperCase() +
                "^" + GenerateCardNumber.nextDate() +
                "226000000000000000000000000?;" +
                GenerateCardNumber.nextNumber() +
                "=" + GenerateCardNumber.nextDate() +
                "2260000000000000?";
        try {
            c = new CardInfo(s.getBytes());
        } catch (CardReaderException e1) {
            //Fake data are always valid
        }
        return c;
    }
}
