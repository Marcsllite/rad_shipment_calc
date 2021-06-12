package rad.shipment.calculator.helpers;

import rad.shipment.calculator.gui.Main;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static at.favre.lib.crypto.HKDF.fromHmacSha256;

public class Encryption {
    // Declaring Variables
    private static final Logger LOGR = Logger.getLogger(Encryption.class.getName());  // getting logger
    private final byte[] secureKey;

    /*///////////////////////////////////////////////// ENCRYPTION ///////////////////////////////////////////////////*/
    /**
     * Constructs an encryption object to encrypt and decrypt values
     */
    public Encryption(){ secureKey = generateKey(); }

    /**
     * Function to generate a secret key for encryption and decryption using this object
     *
     * @return the encryption key for this object
     */
    private byte[] generateKey(){
        try{
            SecureRandom secureRandom = new SecureRandom();
            byte[] key = new byte[Main.getInt("encKeyLength")];
            secureRandom.nextBytes(key);  // creating random 16 byte key
            return key;
        } catch (RuntimeException e) {
            LOGR.log(Level.SEVERE, "Failed to generate encryption key. Error: ", e);
            return null;
        }
    }

    /**
     * Function to encode a byte array to a string
     *
     * @param byteArray the byte array to be encoded
     * @return the encoded string representation of the byte array
     */
    public static String encodeByteArrayToString(byte[] byteArray) throws InvalidParameterException {
        if(byteArray == null) throw new InvalidParameterException("byteArray cannot be null");

        StringBuilder hexStringBuffer = new StringBuilder();
        for (byte b : byteArray) { hexStringBuffer.append(byteToHex(b)); }

        return hexStringBuffer.toString();
    }

    /**
     * Function to decode an encrypted String back to a byte array
     *
     * @param hexString the string to be decoded
     * @return the decoded byte array from hexString
     */
    public static byte[] decodeStringToByteArray(String hexString) throws InvalidParameterException {
        if(hexString == null) throw new InvalidParameterException("hexString cannot be null");

        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * Function to encrypt given plaintext with given key.
     * @author Patrick Favre-Bulle https://gist.github.com/patrickfav
     *
     * @param plainText      to encrypt
     * @param associatedData optional data added to the authentication tag
     * @return encrypted message including mac & iv or null if failed
     */
    public byte[] encrypt(byte[] plainText, byte[] associatedData) {
        try {
            if(plainText == null) throw new InvalidParameterException("plainText cannot be null");

            byte[] iv = new byte[Integer.parseInt(Main.getString("encKeyLength"))];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            byte[] encKey = fromHmacSha256().expand(secureKey, "encKey".getBytes(StandardCharsets.UTF_8), Integer.parseInt(Main.getString("encKeyLength")));
            byte[] authKey = fromHmacSha256().expand(secureKey, "authKey".getBytes(StandardCharsets.UTF_8), Integer.parseInt(Main.getString("authKeyLength"))); //HMAC-SHA256 key is 32 byte

            final Cipher cipher = Cipher.getInstance(Main.getString("cipherInstance")); //actually uses PKCS#7
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encKey, Main.getString("secretKeyVar2")), new IvParameterSpec(iv));
            byte[] cipherText = cipher.doFinal(plainText);

            byte[] mac = prepareMAC(associatedData, iv, cipherText, authKey);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1 + iv.length + 1 + mac.length + cipherText.length);
            byteBuffer.put((byte) iv.length);
            byteBuffer.put(iv);
            byteBuffer.put((byte) mac.length);
            byteBuffer.put(mac);
            byteBuffer.put(cipherText);
            byte[] cipherMessage = byteBuffer.array();

            Arrays.fill(authKey, (byte) 0);
            Arrays.fill(encKey, (byte) 0);

            return cipherMessage;
        } catch(Exception e) {
            LOGR.log(Level.SEVERE, "Failed to encrypt data. Error: ", e);
            return null;
        }
    }

    /**
     * Function to decrypt previously encrypted message with {@link #encrypt(byte[], byte[])}.
     * @author Patrick Favre-Bulle https://gist.github.com/patrickfav
     *
     * @param cipherMessage  the message returned by encrypt
     * @param associatedData optional data added to the authentication tag
     * @return the plain text of null if failed
     */
    public byte[] decrypt(byte[] cipherMessage, byte[] associatedData) {
        try {
            if(cipherMessage == null) throw new InvalidParameterException("cipherMessage cannot be null");

            ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);

            int ivLength = (byteBuffer.get());
            // check input parameter
            if (ivLength != Integer.parseInt(Main.getString("encKeyLength"))) { throw new IllegalArgumentException("invalid iv length"); }
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);

            int macLength = (byteBuffer.get());
            // check input parameter
            if (macLength != Integer.parseInt(Main.getString("authKeyLength"))) { throw new IllegalArgumentException("invalid mac length"); }
            byte[] mac = new byte[macLength];
            byteBuffer.get(mac);

            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            byte[] encKey = fromHmacSha256().expand(secureKey, "encKey".getBytes(StandardCharsets.UTF_8), Integer.parseInt(Main.getString("encKeyLength")));
            byte[] authKey = fromHmacSha256().expand(secureKey, "authKey".getBytes(StandardCharsets.UTF_8), Integer.parseInt(Main.getString("authKeyLength")));


            byte[] refMac = prepareMAC(associatedData, iv, cipherText, authKey);

            if (!MessageDigest.isEqual(refMac, mac)) throw new SecurityException("could not authenticate");

            final Cipher cipher = Cipher.getInstance(Main.getString("cipherInstance"));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encKey, Main.getString("secretKeyVar2")), new IvParameterSpec(iv));
            return cipher.doFinal(cipherText);
        } catch(Exception e) {
            LOGR.log(Level.SEVERE, "Failed to decrypt data. Error: ", e);
            return null;
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to turn a byte to a String
     *
     * @param num the byte to be turned into a string
     * @return the string representation of num
     */
    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    /**
     * Helper function to turn a string in Hex to a byte
     *
     * @param hexString the string to turn into a byte
     * @return the byte version of hexString
     */
    private static byte hexToByte(String hexString) throws InvalidParameterException {
        if(hexString == null) throw new InvalidParameterException("hexString cannot be null");

        int firstDigit = charToInt(hexString.charAt(0));
        int secondDigit = charToInt(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    /**
     * Helper function to turn a character in hex to an integer
     *
     * @param hexChar the hex character
     * @return the integer that represents hexChar
     */
    private static int charToInt(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException("Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    /**
     * Helper function to prepare the MAC layer of encryption
     * when encrypting and decrypting data
     *
     * @param associatedData optional data added to the authentication tag
     * @param iv the iv from the cipherText
     * @param cipherText the message returned by encrypt
     * @param authKey the key created from the secret key during encryption
     *
     * @return MAC to decrypt cipherText
     */
    private byte[] prepareMAC(byte[] associatedData, byte[] iv, byte[] cipherText, byte[] authKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidParameterException {
        if(iv == null) throw new InvalidParameterException("iv cannot be null");
        if(cipherText == null) throw new InvalidParameterException("cipherText cannot be null");
        if(authKey == null) throw new InvalidParameterException("authKey cannot be null");

        SecretKey macKey = new SecretKeySpec(authKey, Main.getString("hmacInstance"));
        Mac hmac = Mac.getInstance(Main.getString("hmacInstance"));
        hmac.init(macKey);
        hmac.update(iv);
        hmac.update(cipherText);

        if (associatedData != null)  hmac.update(associatedData);

        return hmac.doFinal();
    }
}
