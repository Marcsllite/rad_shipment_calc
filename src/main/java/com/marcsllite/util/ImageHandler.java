package com.marcsllite.util;

import javafx.scene.image.Image;

import java.io.File;
import java.security.InvalidParameterException;

public final class ImageHandler {

  public enum Colors {
    DEFAULT_GREY("#8a8a8a"),
    DEFAULT_WHITE("#fff"),
    UML_BLUE("#0469B1");

    public final String val;

    private Colors(String val) {
      this.val = val;
    }
  }

  private ImageHandler() {}

  public static Image getErrorImage() { return new Image("/images/error.png"); }
  
  public static Image getSuccessImage() { return new Image("/images/success.png"); }
  
  public static Image getMinusImage() { return new Image("/images/minus.png"); }
  
  public static Image getColorLogoBkgImage() { return new Image("/images/color_UMass_logo_background.png"); }
  
  public static String getColorLogoBkgPath() { return  System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png"; }
  
  public static Image getShipmentImage(Colors color) throws InvalidParameterException {
    switch(color){
      case DEFAULT_GREY:
        return new Image("/images/grey_single_box.png");
      case DEFAULT_WHITE:
        return new Image("/images/white_single_box.png");
      case UML_BLUE:
        return new Image("/images/color_single_box.png");
      default:
        throw new InvalidParameterException("Invalid color");
    }
  }
  
  public static Image getReferenceImage(Colors color) throws InvalidParameterException {
    switch(color){
      case DEFAULT_GREY:
        return new Image("/images/grey_paper.png");
      case DEFAULT_WHITE:
        return new Image("/images/white_paper.png");
      case UML_BLUE:
        return new Image("/images/color_paper.png");
      default:
        throw new InvalidParameterException("Invalid color");
    }
  }
}