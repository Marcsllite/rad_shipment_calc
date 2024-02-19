package com.marcsllite.util;

import com.marcsllite.util.handler.ImageHandler;
import javafx.scene.image.Image;

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
  }, HOME {
    public String getName() { return "Home Page"; }

    public String getFxmlLoc() { return "/fxml/homePane.fxml"; }
  }, REFERENCE {
    public String getName() { return "Reference Page"; }

    public String getFxmlLoc() { return "/fxml/referencePane.fxml"; }
  }, SUMMARY {
    public String getName() { return "Summary Page"; }

    @Override public double getHeight() { return 400.0; }

    public String getFxmlLoc() { return "/fxml/summaryPane.fxml"; }
  }, MODIFY {
    public String getName() { return "Modify Page"; }

    @Override public double getHeight() { return 320.0; }

    @Override public double getWidth() { return 450.0; }

    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
  }, DETAILS {
    public String getName() { return "Shipment Details"; }

    @Override public double getHeight() { return 270.0; }

    @Override public double getWidth() { return 450.0; }

    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
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
