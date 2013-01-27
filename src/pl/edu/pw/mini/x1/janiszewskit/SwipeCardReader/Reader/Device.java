package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Exceptions.CardReaderException;
import pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Utils.OSChecker;

import java.io.IOException;

/**
 * This is the class that is efectivly a communication layer
 * between Java and device. Underneth it use <code>hidapi</code>
 * to create connection with card reader.
 * <p/>
 * On GNU/Linux it require special permissions to create connection
 * with HID device. Easiest way to run it is to run a program as a
 * root. Also GNU/Linux systems read data in 8 bytes chunks, so there
 * is need for special treatment those OS.
 * <p/>
 * Tested only on Mac OS X and Fedora 17
 */
public class Device {

    static private final int MAX_REPORT_SIZE = 226;
    static private final int VENDOR_ID = 0x801;
    static private final int PRODUCT_ID = 2;

    static private HIDManager manager;
    private HIDDevice device;

    private Integer id = PRODUCT_ID;
    private Integer vendor = VENDOR_ID;
    private Integer timeout = 0;

    /**
     * Method resposible for loading native code and set up devices manager
     * Should be called as a first method, before start doing anything.
     *
     * @throws CardReaderException
     */
    private void loadDriver() throws CardReaderException {
        com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();
        if (manager == null) {

            try {
                manager = HIDManager.getInstance();
            } catch (IOException e) {
                throw new CardReaderException("Cannot initialize HIDManager", e.getCause());
            }
        }
    }

    /**
     * Default constructor.
     * <p/>
     * Create new instance of Device class with default vendor and product id
     *
     * @throws CardReaderException
     */
    public Device() throws CardReaderException {

        loadDriver();
        open();
    }

    /**
     * Constructor that create instance connected vith desired device
     * specified by parameters
     *
     * @param id     device's product id
     * @param vendor device's vendor id
     * @throws CardReaderException
     */
    public Device(Integer id, Integer vendor) throws CardReaderException {

        loadDriver();

        this.id = id;
        this.vendor = id;

        open();
    }

    /**
     * Constructor that create instance connected vith desired device
     * specified by parameters.
     *
     * @param id      device's product id
     * @param vendor  device's vendor id
     * @param timeout reading timeout in milliseconds
     * @throws CardReaderException
     */
    public Device(Integer id, Integer vendor, Integer timeout) throws CardReaderException {

        this(id, vendor);
        this.timeout = timeout;
    }

    /**
     * Open connection with device. Connection is opened automatically
     * when object is created but can be closed by user
     *
     * @throws CardReaderException
     * @see pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device#close()
     */
    public void open() throws CardReaderException {

        try {
            device = manager.openById(vendor, id, null);
        } catch (IOException e) {
            throw new CardReaderException("Cannot open device", e.getCause());
        }

    }

    /**
     * Close connection with device. Before next read attempt connection should
     * be resotred using <code>open</code>
     *
     * @throws CardReaderException
     * @see pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Reader.Device#open()
     */
    public void close() throws CardReaderException {

        try {
            device.close();
        } catch (IOException e) {
            throw new CardReaderException("Cannot close device", e.getCause());
        }
    }

    /**
     * Reads data form card. Methods waits until user swipe his card
     *
     * @return array of read bytes
     * @throws CardReaderException
     */
    public byte[] read() throws CardReaderException {

        if (!(timeout.equals(0))) return readTimeout();

        byte[] buffer = new byte[MAX_REPORT_SIZE];
        int size = 0;

        try {
            if (OSChecker.isUnix()) {
                size = _readOnLinux(buffer);
            } else {
                size = device.read(buffer);
            }

        } catch (IOException e) {
            throw new CardReaderException("Cannot read", e.getCause());
        }

        byte[] result = new byte[size];
        System.arraycopy(buffer, 0, result, 0, size);

        return result;
    }

    /**
     * Handle GNU/Linux specify HID input
     *
     * @param buffer byte array for read data
     * @return count of read bytes
     * @throws IOException
     */
    private int _readOnLinux(byte[] buffer) throws IOException {

        int s = 2;
        int size = 0;

        while (s != 1) {

            byte[] buff = new byte[8];
            s = device.read(buff);

            for (int i = 0; i < s; i++) {

                buffer[size + i] = buff[i];
                size -= (buff[i] != 0) ? 0 : 1;
            }

            size += s;
        }

        return size;
    }

    /**
     * Read card untill timesout
     * Not tested! Be careful.
     *
     * @return
     * @throws CardReaderException
     */
    private byte[] readTimeout() throws CardReaderException {

        byte[] buffer = new byte[MAX_REPORT_SIZE];
        int size = 0;

        size = device.readTimeout(buffer, timeout);

        byte[] result = new byte[size];
        System.arraycopy(buffer, 0, result, 0, size);

        return result;
    }

    /**
     * Close connection before utilize object.
     * It is important to do it on device if user forgot to do so,
     * since it frees pointer to internal data structure.
     *
     * @throws CardReaderException
     */
    protected void finalize() throws Throwable {
        //
        close();
        super.finalize();
    }

    /**
     * Determine if two instance are pointing on same device
     *
     * @param object
     * @return True if instance point on same device,
     *         otherwise return false
     */
    public boolean equals(Object object) {

        if (!(object instanceof Device))
            return false;
        return ((Device) object).device.equals(this.device);
    }

    /**
     * Compute object hash
     *
     * @return computed hash code
     */
    public int hashCode() {
        return device.hashCode();
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public Integer getVendor() {
        return vendor;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
