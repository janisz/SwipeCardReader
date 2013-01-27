package pl.edu.pw.mini.x1.janiszewskit.SwipeCardReader.Utils;

/**
 * Class that determine host operating system
 */
public class OSChecker {

    /**
     * Contains operating system name
     */
    private final static String OS = System.getProperty("os.name").toLowerCase();

    /**
     * @return if host is running Windows
     */
    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    /**
     * @return if host is running Mac OS
     */
    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    /**
     * @return if host is running GNU/Linux
     */
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    /**
     * @return if host is running Solaris
     */
    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

}