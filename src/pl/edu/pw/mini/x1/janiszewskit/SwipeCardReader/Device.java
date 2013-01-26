package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/13
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Device {

    //"The maximum size of a HID Feature report is 64 bytes"
    //http://msdn.microsoft.com/en-us/library/windows/hardware/hh406599(v=vs.85).aspx
    static private final int MAX_REPORT_SIZE = 64;

    static private HIDManager manager;
    private HIDDevice device;

    private Integer id = 0;
    private Integer vendor = 0;
    private Integer timeout = 0;


    public Device(Integer id, Integer vendor) throws CardReaderException {

        com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();

        if (manager == null) {

            try {
                manager = HIDManager.getInstance();
            } catch (IOException e) {
                throw new CardReaderException("Cannot initialize HIDManager", e.getCause());
            }
        }

        this.id = id;
        this.vendor = id;

        open();
    }

    public Device(Integer id, Integer vendor, Integer timeout) throws CardReaderException {

        this(id, vendor);
        this.timeout = timeout;
    }

    public void open() throws CardReaderException {

        try {
            device = manager.openById(vendor, id, null);
        } catch (IOException e) {
            throw new CardReaderException("Cannot open device", e.getCause());
        }

    }

    public void close() throws CardReaderException {

        try {
            device.close();
        } catch (IOException e) {
            throw new CardReaderException("Cannot close device", e.getCause());
        }
    }

    public byte[] read() throws CardReaderException {

        if (!(timeout.equals(0))) return readTimeout();

        byte[] buffer = new byte[MAX_REPORT_SIZE];
        int size = 0;

        try {
            size = device.read(buffer);
        } catch (IOException e) {
            throw new CardReaderException("Cannot read", e.getCause());
        }

        byte[] result = new byte[size];
        System.arraycopy(buffer, 0, result, 0, size);

        return result;
    }

    private byte[] readTimeout() throws CardReaderException {

        byte[] buffer = new byte[MAX_REPORT_SIZE];
        int size = 0;

        size = device.readTimeout(buffer, timeout);

        byte[] result = new byte[size];
        System.arraycopy(buffer, 0, result, 0, size);

        return result;
    }


    protected void finalize() throws CardReaderException {
        // It is important to call close() on device if user forgot to do so,
        // since it frees pointer to internal data structure.
        close();
    }

    public boolean equals(Object object) {

        if (!(object instanceof Device))
            return false;
        return ((Device) object).device.equals(this.device);
    }

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
