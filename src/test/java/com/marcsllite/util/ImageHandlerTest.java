package com.marcsllite.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ImageHandlerTest extends ApplicationTest {
    public void start(Stage arg0) throws Exception {}
  
    @Test
    public void testGetErrorImage() throws MalformedURLException {
        Image actual = ImageHandler.getErrorImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/error.png"));
    }
  
    @Test
    public void testGetSuccessImage() {
        Image actual = ImageHandler.getSuccessImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/success.png"));
    }
  
    @Test
    public void testGetMinusImage() {
        Image actual = ImageHandler.getMinusImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/minus.png"));
    }
  
    @Test
    public void testGetColorLogoBkgImage() {
        Image actual = ImageHandler.getColorLogoBkgImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/color_UMass_logo_background.png"));
    }
  
    @Test
    public void testGetColorLogoBkgPath() {
        String expected = System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png";
        assertEquals(expected, ImageHandler.getColorLogoBkgPath());
    }
  
    @Test
    @Parameters(method = "testGetShipmentImageException_data")
    public void testGetShipmentImageExceptionChecker(String color, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> ImageHandler.getShipmentImage(color)
        );
        assertTrue(exception.getMessage().contains(message));
    }
  
    private Object[] testGetShipmentImageException_data() {
        return new Object[] {
            new Object[] { null, "The color cannot be null" },
            new Object[] { "fakeColor", "Invalid color" }
        };
    }
  
    @Test
    @Parameters({
        "umlBlue, /images/color_single_box.png",
        "defaultWhite, /images/white_single_box.png",
        "defaultGrey, /images/grey_single_box.png"
    })
    public void testGetShipmentImage(String color, String url) {
        Platform.runLater(() -> {
            String c = Util.getString(color);
            
            assumeNotNull(c);
            assumeFalse(c.isEmpty());

            Image actual = ImageHandler.getShipmentImage(c);
            assertNotNull(actual);
            assertTrue(actual.getUrl().contains(url));
        });
    }
  
    @Test
    @Parameters(method = "testGetReferenceImageException_data")
    public void testGetReferenceImageExceptionChecker(String color, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> ImageHandler.getReferenceImage(color)
        );
        assertTrue(exception.getMessage().contains(message));
    }
  
    private Object[] testGetReferenceImageException_data() {
        return new Object[] {
            new Object[] { null, "The color cannot be null" },
            new Object[] { "fakeColor", "Invalid color" }
        };
    }
  
    @Test
    @Parameters({
        "umlBlue, /images/color_paper.png",
        "defaultWhite, /images/white_paper.png",
        "defaultGrey, /images/grey_paper.png"
    })
    public void testGetReferenceImage(String color, String url) {
        Platform.runLater(() -> {
            String c = Util.getString(color);
            
            assumeNotNull(c);
            assumeFalse(c.isEmpty());

            Image actual = ImageHandler.getReferenceImage(c);
            assertNotNull(actual);
            assertTrue(actual.getUrl().contains(url));
        });
    }
}
