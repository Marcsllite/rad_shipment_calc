package com.marcsllite.util;

import javafx.scene.image.Image;

@SuppressWarnings("java:S116")
public enum FXMLView {
  PRIMARY {
    public String getName() { return "Primary"; }

    public String getFxmlLoc() { return "/fxml/primary.fxml"; }
  }, SECONDARY {
    public String getName() { return "Secondary"; }
    
    public String getFxmlLoc() { return "/fxml/secondary.fxml"; }
  }, TEST {
    public String getName() { return "Test"; }

    public String getFxmlLoc() { return "/fxml/test.fxml"; }
  };
  
  private final double HEIGHT = 480.0;
  private final double WIDTH = 640.0;

  public abstract String getName();
  
  public String getTitle() { return getName() + " Page"; }
  
  public double getHeight() { return HEIGHT; }
    
  public double getWidth() { return WIDTH; }
  
  public double getMaxHeight() { return getHeight(); }
    
  public double getMaxWidth() { return getWidth(); }
  
  public String getFxmlName() {
    return getFxmlLoc().replace("/fxml/", "");
  }
  
  public abstract String getFxmlLoc();
  
  public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
}
