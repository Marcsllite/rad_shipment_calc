package rad.shipment.calculator.helpers;

import rad.shipment.calculator.gui.Main;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network {

    // Declaring Variables
    private static final Logger LOGR = Logger.getLogger(Network.class.getName());  // getting logger

    /**
     * Function to ping given hostname
     *
     * @param hostname the hostname of the device to ping
     * @return true if ping was successful, else false
     */
    public static boolean ping(String hostname) {
        try {
            if(InetAddress.getByName(hostname).isReachable(4000)) {
                LOGR.info("Successfully pinged " + hostname);
                return true;
            } else {
                LOGR.info("Ping to " + hostname + " failed.");
                return false;
            }
        } catch (IOException e) {
            // getByName(String host) throws UnknownHostException
            // isReachable(int timeout) throws IOException
            LOGR.info("Ping to " + hostname + " failed.");
            return false;
        }
    }

//    /**
//     * Function to ping given DeviceInfo object
//     * Also sets the status image and status text for the device
//     *
//     * @param device the device to ping
//     * @return true if ping was successful, else false
//     */
//    public static boolean ping(DeviceInfo device) {
//        try {
//            // getByName(String host) throws UnknownHostException
//            // isReachable(int timeout) throws IOException
//            // UnknownHostException is a type of IOException
//            if(InetAddress.getByName(device.getHostname()).isReachable(4000)) {
//                LOGR.info("Successfully pinged " + device.getHostname());
//                // setting status image and status text for device based on ping results
//                device.setImgViewStatus(new ImageView(ImageHandler.getSuccessImage()));
//                device.setTooltipStatus(Main.getBundle().getString("connectSuccess"));
//                return true;
//            } else {
//                LOGR.info("Ping to " + device.getHostname() + " failed.");
//                // setting status image and status text for device based on ping results
//                device.setImgViewStatus(new ImageView(ImageHandler.getErrorImage()));
//                device.setTooltipStatus(Main.getBundle().getString("connectFail"));
//                device.setLastReboot(null);
//                return false;
//            }
//        } catch (IOException e) {
//            LOGR.info("Ping to " + device.getHostname() + " failed.");
//            // setting status image and status text for device based on ping results
//            device.setImgViewStatus(new ImageView(ImageHandler.getErrorImage()));
//            device.setTooltipStatus(Main.getBundle().getString("connectFail"));
//            device.setLastReboot(null);
//            return false;
//        }
//    }

    /**
     * Function to get the hostname of a given ip address
     *
     * @param ip the ip to find the hostname of
     * @param logging boolean value that states whether logging will be done
     * @return the hostname of the give ip address or the DeviceInfo's default string if failed
     */
    public static String lookUpHostname(String ip, boolean logging){
        try { return InetAddress.getByName(ip).getHostName(); }
        catch (UnknownHostException e) {
            if(logging) LOGR.log(Level.WARNING, "Could not look up the ip address " + ip + ". Error: ", e);
            return Main.getString("defaultStr");
        }
    }

    /**
     * Function to get the hostname of a given ip address with logging
     *
     * @param ip the ip to find the hostname of
     * @return the hostname of the give ip address or the DeviceInfo's default string if failed
     */
    public static String lookUpHostname(String ip){ return lookUpHostname(ip, true); }

    /**
     * Function to get the ip of a given hostname
     *
     * @param hostname the hostname to find the ip address of
     * @param logging boolean value that states whether logging will be done
     * @return the ip address of the given hostname or the DeviceInfo's default string if failed
     */
    public static String lookUpIP(String hostname, boolean logging){
        InetAddress thisComputer;
        byte[] address;
        StringBuilder ret = new StringBuilder();

        // get the bytes of the IP address
        try {
            thisComputer = InetAddress.getByName(hostname);
            address = thisComputer.getAddress();
        } catch (UnknownHostException e) {
            if (logging) LOGR.log(Level.WARNING, "Cannot find host " + hostname + ". Error: ", e);
            return Main.getString("defaultStr");
        }

        // Find the IP address
        for (byte b : address) {
            int unsignedByte = b < 0 ? b + 256 : b;
            ret.append(unsignedByte).append(".");
        }
        return ret.substring(0, ret.length() - 1);
    }

    /**
     * Function to get the ip of a given hostname with logging
     *
     * @param hostname the hostname to find the ip address of
     * @return the ip address of the given hostname or the DeviceInfo's default string if failed
     */
    public static String lookUpIP(String hostname){ return lookUpIP(hostname, true); }
}
