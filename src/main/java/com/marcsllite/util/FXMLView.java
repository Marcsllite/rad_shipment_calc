package com.marcsllite.util;

import javafx.scene.image.Image;

public enum FXMLView {
  MAIN {
    public String getName() { return Util.getString("AppName"); }
    
    public String getTitle() { return Util.getString("AppPane"); }
    
    public int getHeight() { return Util.getInt("AppHeight"); }
    
    public int getWidth() { return Util.getInt("AppWidth"); }
    
    public int getMaxHeight() { return Util.getInt("maxAppHeight"); }
    
    public int getMaxWidth() { return Util.getInt("maxAppWidth"); }
    
    public String getFxmlLoc() { return "/fxml/App.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, MENU {
    public String getName() { return Util.getString("menuName"); }
    
    public String getTitle() { return Util.getString("menuPane"); }
    
    public int getHeight() { return Util.getInt("menuHeight"); }
    
    public int getWidth() { return Util.getInt("menuWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/menuPane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, SUMMARY {
    public String getName() { return Util.getString("summaryName"); }
    
    public String getTitle() { return Util.getString("summaryPane"); }
    
    public int getHeight() { return Util.getInt("summaryHeight"); }
    
    public int getWidth() { return Util.getInt("summaryWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/summaryPane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, HOME {
    public String getName() { return Util.getString("homeName"); }
    
    public String getTitle() { return Util.getString("homePane"); }
    
    public int getHeight() { return Util.getInt("homeHeight"); }
    
    public int getWidth() { return Util.getInt("homeWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/homePane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, REFERENCE {
    public String getName() { return Util.getString("referenceName"); }
    
    public String getTitle() { return Util.getString("referencePane"); }
    
    public int getHeight() { return Util.getInt("referenceHeight"); }
    
    public int getWidth() { return Util.getInt("referenceWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/referencePane.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, ADD {
    public String getName() { return Util.getString("addName"); }
    
    public String getTitle() { return Util.getString("addPane"); }
    
    public int getHeight() { return Util.getInt("modifyHeight"); }
    
    public int getWidth() { return Util.getInt("modifyWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, EDIT {
    public String getName() { return Util.getString("editName"); }
    
    public String getTitle() { return Util.getString("editPane"); }
    
    public int getHeight() { return Util.getInt("modifyHeight"); }
    
    public int getWidth() { return Util.getInt("modifyWidth"); }
    
    public int getMaxHeight() { return getHeight(); }
    
    public int getMaxWidth() { return getWidth(); }
    
    public String getFxmlLoc() { return "/fxml/modify.fxml"; }
    
    public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
  }, SHIPMENT_DETAILS {
    public String getName() { return Util.getString("shipmentName"); }
    
    public String getTitle() { return Util.getString("shipmentInfoPane"); }
    
    public int getHeight() { return Util.getInt("shipmentDetailsHeight"); }
    
    public int getWidth() { return Util.getInt("shipmentDetailsWidth"); }
    
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
