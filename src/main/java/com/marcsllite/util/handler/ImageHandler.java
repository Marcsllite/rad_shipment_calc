package com.marcsllite.util.handler;

import javafx.scene.image.Image;

import java.io.File;

public final class ImageHandler {
  public enum Colors {
    DEFAULT_GREY("#8a8a8a"),
    DEFAULT_WHITE("#fff"),
    UML_BLUE("#0469B1");

    private final String val;

    Colors(String val) {
      this.val = val;
    }

    public String getVal() {
      return val;
    }
  }

  private ImageHandler() {}

  public static Image getErrorImage() { return new Image("/images/error.png"); }
  
  public static Image getSuccessImage() { return new Image("/images/success.png"); }
  
  public static Image getMinusImage() { return new Image("/images/minus.png"); }
  
  public static Image getColorLogoBkgImage() { return new Image("/images/color_UMass_logo_background.png"); }
  
  public static String getColorLogoBkgPath() { return  System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png"; }
  
  public static Image getShipmentImage(Colors color)  {
    switch(color){
      case DEFAULT_GREY:
        return new Image("/images/grey_single_box.png");
      case DEFAULT_WHITE:
        return new Image("/images/white_single_box.png");
      default:
        return new Image("/images/color_single_box.png");
    }
  }
  
  public static Image getReferenceImage(Colors color) {
    switch(color){
      case DEFAULT_GREY:
        return new Image("/images/grey_paper.png");
      case DEFAULT_WHITE:
        return new Image("/images/white_paper.png");
      default:
        return new Image("/images/color_paper.png");
    }
  }
}