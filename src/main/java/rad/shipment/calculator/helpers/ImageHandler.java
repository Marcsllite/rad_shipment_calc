package rad.shipment.calculator.helpers;

import javafx.scene.image.Image;
import rad.shipment.calculator.gui.Main;
import java.io.File;
import java.security.InvalidParameterException;

public class ImageHandler {
    public static Image getErrorImage(){ return new Image("/images/error.png"); }
    public static Image getSuccessImage(){ return new Image("/images/success.png"); }
    public static Image getMinusImage(){ return new Image("/images/minus.png"); }
    public static Image getColorLogoBkgImage(){ return new Image("/images/color_UMass_logo_background.png"); }
    public static String getColorLogoBkgPath() { return System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png"; }

    public static Image getShipmentImage(String color) throws RuntimeException {
        // making sure the color value is valid
        if(color == null) throw new InvalidParameterException("The color cannot be null");

        else if(color.equals(Main.getString("umlBlue"))) return new Image("/images/color_single_box.png");
        else if(color.equals(Main.getString("defaultWhite"))) return new Image("/images/white_single_box.png");
        else if(color.equals(Main.getString("defaultGrey"))) return new Image("/images/grey_single_box.png");
        else throw new RuntimeException("Invalid color");
    }
    public static Image getReferenceImage(String color) throws RuntimeException {
        // making sure the color value is valid
        if(color == null) throw new InvalidParameterException("The color cannot be null");

        else if(color.equals(Main.getString("umlBlue"))) return new Image("/images/color_paper.png");
        else if(color.equals(Main.getString("defaultWhite"))) return new Image("/images/white_paper.png");
        else if(color.equals(Main.getString("defaultGrey"))) return new Image("/images/grey_paper.png");
        else throw new RuntimeException("Invalid color");
    }

}
