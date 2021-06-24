package com.marcsllite.util;

import java.io.File;
import java.security.InvalidParameterException;

import javafx.scene.image.Image;

public class ImageHandler {
  public static Image getErrorImage() { return new Image("/images/error.png"); }
  public static Image getSuccessImage() { return new Image("/images/success.png"); }
  public static Image getMinusImage() { return new Image("/images/minus.png"); }
  public static Image getColorLogoBkgImage() { return new Image("/images/color_UMass_logo_background.png"); }
  public static String getColorLogoBkgPath() { return  System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png"; }
  
  private ImageHandler() {}
  
  public static Image getShipmentImage(String color) throws InvalidParameterException {
    if (color == null) throw new InvalidParameterException("The color cannot be null"); 

    if (color.equals(Util.getString("umlBlue"))) return new Image("/images/color_single_box.png"); 
    if (color.equals(Util.getString("defaultWhite"))) return new Image("/images/white_single_box.png"); 
    if (color.equals(Util.getString("defaultGrey"))) return new Image("/images/grey_single_box.png"); 

    throw new InvalidParameterException("Invalid color");
  }
  
  public static Image getReferenceImage(String color) throws InvalidParameterException {
    if (color == null) throw new InvalidParameterException("The color cannot be null");

    if (color.equals(Util.getString("umlBlue"))) return new Image("/images/color_paper.png"); 
    if (color.equals(Util.getString("defaultWhite"))) return new Image("/images/white_paper.png"); 
    if (color.equals(Util.getString("defaultGrey"))) return new Image("/images/grey_paper.png");

    throw new InvalidParameterException("Invalid color");
  }
}