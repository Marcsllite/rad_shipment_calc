package com.marcsllite.util;

import org.codehaus.plexus.util.StringUtils;

public class OSUtil {
    /**
     * @author Mkyong.com <a href="https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/">...</a>
     * Convenience function to figure out the current operating system
     *
     * @return the current operating system
     */
    public static OS getOS() {
        if (isWindows()) return OS.WINDOWS;
        else if (isMac()) return OS.MAC;
        else if (isUnix()) return OS.UNIX;
        else if (isSolaris()) return OS.SOLARIS;
        else return OS.NOT_SUPPORTED;
    }

    public static String getOperatingSystem() {
        String name = System.getProperty("os.name");
        return StringUtils.isBlank(name)? "" : name;
    }

    public static boolean isWindows() {
        return (getOperatingSystem().toLowerCase().contains("win"));
    }

    public static boolean isMac() {
        return (getOperatingSystem().toLowerCase().contains("mac"));
    }

    public static boolean isUnix() {
        return (getOperatingSystem().contains("nix") ||
            getOperatingSystem().contains("nux") ||
            getOperatingSystem().contains("aix"));
    }

    public static boolean isSolaris() {
        return (getOperatingSystem().toLowerCase().contains("sunos"));
    }

    public enum OS {
        WINDOWS("Windows"),
        MAC("Mac"),
        UNIX("Unix"),
        SOLARIS("Solaris"),
        NOT_SUPPORTED("Not Supported");

        private final String val;

        OS(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }
}

