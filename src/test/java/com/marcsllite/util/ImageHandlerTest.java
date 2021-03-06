package com.marcsllite.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.File;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationExtension;

import javafx.scene.image.Image;

@ExtendWith(ApplicationExtension.class)
public class ImageHandlerTest {
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
  
    @ParameterizedTest
    @MethodSource("invalidColor_data")
    public void testGetShipmentImageExceptionChecker(String color, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> ImageHandler.getShipmentImage(color)
        );
        assertTrue(exception.getMessage().contains(message));
    }
  
    private static Object[] invalidColor_data() {
        return new Object[] {
            new Object[] { null, "The color cannot be null" },
            new Object[] { "fakeColor", "Invalid color" }
        };
    }
  
    @ParameterizedTest
    @CsvSource({
        "umlBlue, /images/color_single_box.png",
        "defaultWhite, /images/white_single_box.png",
        "defaultGrey, /images/grey_single_box.png"
    })
    public void testGetShipmentImage(String color, String url) {
        String c = Util.getString(color);
        
        assumeFalse(c == null);
        assumeFalse(c.isEmpty());

        Image actual = ImageHandler.getShipmentImage(c);
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains(url));
    }
  
    @ParameterizedTest
    @MethodSource("invalidColor_data")
    public void testGetReferenceImageExceptionChecker(String color, String message) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> ImageHandler.getReferenceImage(color)
        );
        assertTrue(exception.getMessage().contains(message));
    }
  
    @ParameterizedTest
    @CsvSource({
        "umlBlue, /images/color_paper.png",
        "defaultWhite, /images/white_paper.png",
        "defaultGrey, /images/grey_paper.png"
    })
    public void testGetReferenceImage(String color, String url) {
        String c = Util.getString(color);
        
        assumeFalse(c == null);
        assumeFalse(c.isEmpty());

        Image actual = ImageHandler.getReferenceImage(c);
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains(url));
    }
}
