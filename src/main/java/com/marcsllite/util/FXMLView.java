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
  
  private final int HEIGHT = 480;
  private final int WIDTH = 640;

  public abstract String getName();
  
  public String getTitle() { return getName() + " Page"; }
  
  public int getHeight() { return HEIGHT; }
    
  public int getWidth() { return WIDTH; }
  
  public int getMaxHeight() { return getHeight(); }
    
  public int getMaxWidth() { return getWidth(); }
  
  public String getFxmlName() {
    return getFxmlLoc().replace("/fxml/", "");
  }
  
  public abstract String getFxmlLoc();
  
  public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
}
