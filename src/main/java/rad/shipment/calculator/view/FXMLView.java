package rad.shipment.calculator.view;

import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.helpers.ImageHandler;
import javafx.scene.image.Image;

public enum FXMLView {

    MAIN {
        @Override public String getTitle() { return Main.getString("mainPane"); }

        @Override public int getHeight() { return Main.getInt("mainHeight");}

        @Override public int getWidth() { return Main.getInt("mainWidth");}

        @Override public int getMaxHeight() { return Main.getInt("maxMainHeight");}

        @Override public int getMaxWidth() { return Main.getInt("maxMainWidth");}

        @Override public String getFxmlName() { return getFxmlLoc().replace("/fxml/", ""); }

        @Override public String getFxmlLoc() { return "/fxml/main.fxml"; }

        @Override public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
    }, MENU {
        @Override public String getTitle() { return Main.getString("menuPane"); }

        @Override public int getHeight() { return Main.getInt("menuHeight");}

        @Override public int getWidth() { return Main.getInt("menuWidth");}

        @Override public int getMaxHeight() { return getHeight();}

        @Override public int getMaxWidth() { return getWidth();}

        @Override public String getFxmlName() { return getFxmlLoc().replace("/fxml/", ""); }

        @Override public String getFxmlLoc() { return "/fxml/menuPane.fxml"; }

        @Override public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
    }, SUMMARY {
        @Override public String getTitle() { return Main.getString("summaryPane"); }

        @Override public int getHeight() { return Main.getInt("summaryHeight");}

        @Override public int getWidth() { return Main.getInt("summaryWidth");}

        @Override public int getMaxHeight() { return getHeight();}

        @Override public int getMaxWidth() { return getWidth();}

        @Override public String getFxmlName() { return getFxmlLoc().replace("/fxml/", ""); }

        @Override public String getFxmlLoc() { return "/fxml/summaryPane.fxml"; }

        @Override public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
    }, HOME {
        @Override public String getTitle() { return Main.getString("homePane"); }

        @Override public int getHeight() { return Main.getInt("homeHeight");}

        @Override public int getWidth() { return Main.getInt("homeWidth");}

        @Override public int getMaxHeight() { return getHeight();}

        @Override public int getMaxWidth() { return getWidth();}

        @Override public String getFxmlName() { return getFxmlLoc().replace("/fxml/", ""); }

        @Override public String getFxmlLoc() { return "/fxml/homePane.fxml"; }

        @Override public Image getIconImage() { return ImageHandler.getColorLogoBkgImage(); }
    };
    
    /**
     * Getter function to get the title of the FXMLView
     * 
     * @return the title of the FXMLView
     */
    public abstract String getTitle();
    
    /**
     * Getter function to get the height of the FXMLView
     *
     * @return the height of the FXMLView
     */
    public abstract int getHeight();
    
    /**
     * Getter function to get the width of the FXMLView
     *
     * @return the width of the FXMLView
     */
    public abstract int getWidth();

    /**
     * Getter function to get the max height of the FXMLView
     *
     * @return the max height of the FXMLView
     */
    public abstract int getMaxHeight();

    /**
     * Getter function to get the max width of the FXMLView
     *
     * @return the max width of the FXMLView
     */
    public abstract int getMaxWidth();

    /**
     * Getter function to get the name the fxml file in the FXMLView
     *
     * @return the name of the fxml file in the FXMLView
     */
    public abstract String getFxmlName();

    /**
     * Getter function to get the file path of the fxml file in the FXMLView
     *
     * @return the file path of the fxml file in the FXMLView
     */
    public abstract String getFxmlLoc();
    
    /**
     * Getter function to get the icon image of the FXMLView
     *
     * @return the icon image of the FXMLView
     */
    public abstract Image getIconImage();
}
