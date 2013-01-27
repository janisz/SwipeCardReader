package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.GUI;

import com.ezware.dialog.task.CommandLink;
import com.ezware.dialog.task.TaskDialogs;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Card.CardInfo;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions.CardReaderException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * User: root
 * Date: 1/27/13
 * Time: 3:41 PM
 */
public class MainWindow {

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

    public MainWindow() {

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
                                            1,
                                            new CommandLink("Exit",
                                                    "Close application."),
                                            new CommandLink("Work on fake data",
                                                    "Play with some random data to see how application is working.")
                                    );
                                    if (choice == 0) System.exit(0);
                                } finally {
                                    try {
                                        device.close();
                                    } catch (CardReaderException e) {
                                        //Ignore possible errors
                                    }
                                    progressBar.setVisible(false);
                                    buttonReadData.setEnabled(true);
                                }
                                showCardInfo(true);
                            }
                        };
                        worker.start();
                    }
                }
        );
    }

    public void showCardInfo(boolean secure) {

        DateFormat formatter = new SimpleDateFormat("MM/yy");

        textRawData.setText(new String(card.getTrackOne().getRawData()));
        textCardHolder.setText(card.getCardholderName());
        textCardNumber.setText(card.getMaskedNumber());
        textExpirationData.setText(formatter.format(card.getExpirationDate()));
        textExpirationData.setToolTipText(card.getExpirationDate().toString());
        textServiceCode.setText(card.getTrackOne().getServiceCode().toString());

        cardBrand.setText(card.getCardType().toString());

        URL url = getClass().getResource("");
        Image image = new ImageIcon(url).getImage();
        cardBrand.setIcon((Icon) image);

        if (!secure)
            textCardNumber.setText(card.getCardNumber());
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

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
}
