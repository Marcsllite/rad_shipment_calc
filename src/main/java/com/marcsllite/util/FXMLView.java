package com.marcsllite.util;

import com.marcsllite.App;

import javafx.scene.image.Image;

public enum FXMLView {
  MAIN {
    public String getName() { return App.getString("AppName"); }
    
    public String getTitle() { return App.getString("AppPane"); }
    
    public int getHeight() { return App.getInt("AppHeight"); }
    
    public int getWidth() { return App.getInt("AppWidth"); }
    
    public int getMaxHeight() { return App.getInt("maxAppHeight"); }
    
    public int getMaxWidth() { return App.getInt("maxAppWidth"); }
    
    public String getFxmlLoc() { return "/fxml/App.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, MENU {
    public String getName() { return App.getString("menuName"); }
    
    public String getTitle() { return App.getString("menuPane"); }
    
    public int getHeight() { return App.getInt("menuHeight"); }
    
    public int getWidth() { return App.getInt("menuWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/menuPane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, SUMMARY {
    public String getName() { return App.getString("summaryName"); }
    
    public String getTitle() { return App.getString("summaryPane"); }
    
    public int getHeight() { return App.getInt("summaryHeight"); }
    
    public int getWidth() { return App.getInt("summaryWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/summaryPane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, HOME {
    public String getName() { return App.getString("homeName"); }
    
    public String getTitle() { return App.getString("homePane"); }
    
    public int getHeight() { return App.getInt("homeHeight"); }
    
    public int getWidth() { return App.getInt("homeWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/homePane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, REFERENCE {
    public String getName() { return App.getString("referenceName"); }
    
    public String getTitle() { return App.getString("referencePane"); }
    
    public int getHeight() { return App.getInt("referenceHeight"); }
    
    public int getWidth() { return App.getInt("referenceWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/referencePane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, ADD {
    public String getName() { return App.getString("addName"); }
    
    public String getTitle() { return App.getString("addPane"); }
    
    public int getHeight() { return App.getInt("modifyHeight"); }
    
    public int getWidth() { return App.getInt("modifyWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, EDIT {
    public String getName() { return App.getString("editName"); }
    
    public String getTitle() { return App.getString("editPane"); }
    
    public int getHeight() { return App.getInt("modifyHeight"); }
    
    public int getWidth() { return App.getInt("modifyWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, SHIPMENT_DETAILS {
    public String getName() { return App.getString("shipmentName"); }
    
    public String getTitle() { return App.getString("shipmentInfoPane"); }
    
    public int getHeight() { return App.getInt("shipmentDetailsHeight"); }
    
    public int getWidth() { return App.getInt("shipmentDetailsWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/shipmentDetails.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, PRIMARY {
    public String getName() { return "Primary"; }
    
    public String getTitle() { return "Primary Page"; }
    
    public int getHeight() { return 480; }
    
    public int getWidth() { return 640; }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/primary.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, SECONDARY {
    public String getName() { return "Secondary"; }
    
    public String getTitle() { return "Secondary Page"; }
    
    public int getHeight() { return 480; }
    
    public int getWidth() { return 640; }
    
    public int getMaxHeight() { return getHeight();  }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/secondary.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, TEST {
    public String getName() { return "Test"; }
    
    public String getTitle() { return "Test Page"; }
    
    public int getHeight() { return 480; }
    
    public int getWidth() { return 640; }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/test.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  };
  
  public abstract String getName();
  
  public abstract String getTitle();
  
  public abstract int getHeight();
  
  public abstract int getWidth();
  
  public abstract int getMaxHeight();
  
  public abstract int getMaxWidth();
  
  public String getFxmlName() {
    return getFxmlLoc().replace("/fxml/", "");
  }
  
  public abstract String getFxmlLoc();
  
  public abstract Image getIconImage();
}
