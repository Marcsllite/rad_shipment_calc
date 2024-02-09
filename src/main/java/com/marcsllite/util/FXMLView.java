package com.marcsllite.util;

import javafx.scene.image.Image;

@SuppressWarnings("java:S116")
public enum FXMLView {
  MAIN {
    public String getName() { return "Main Page"; }

    @Override public String getTitle() { return "UMass Lowell - Rad Shipment Calculator"; }

    @Override public double getMaxHeight() { return 850.0; }

    @Override public double getMaxWidth() { return 950.0; }

    public String getFxmlLoc() { return "/fxml/main.fxml"; }
  }, MENU {
    public String getName() { return "Menu Page"; }

    @Override public double getWidth() { return 150.0; }

    public String getFxmlLoc() { return "/fxml/menuPane.fxml"; }
  }, PRIMARY {
    public String getName() { return "Primary Page"; }

    public String getFxmlLoc() { return "/fxml/primary.fxml"; }
  }, SECONDARY {
    public String getName() { return "Secondary Page"; }

    public String getFxmlLoc() { return "/fxml/secondary.fxml"; }
  }, TEST {
    public String getName() { return "Test"; }

    public String getFxmlLoc() { return "/test.fxml"; }
  };

  public final double SIDE_500 = 500.0;
  public final double SIDE_600 = 600.0;

  public abstract String getName();
  
  public String getTitle() { 
    return "Rad Shipment Calculator - " + getName(); 
  }
  
  public double getHeight() { return SIDE_500; }
    
  public double getWidth() { return SIDE_600; }
  
  public double getMaxHeight() { return getHeight(); }
    
  public double getMaxWidth() { return getWidth(); }
  
  public String getFxmlName() {
    return getFxmlLoc().replace("/fxml/", "");
  }
  
  public abstract String getFxmlLoc();
  
  public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
}
