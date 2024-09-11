package com.marcsllite.util.handler;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxToolkit;

import java.io.File;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("DataFlowIssue")
class ImageHandlerTest {
    @BeforeAll
    public static void setup() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    void testGetErrorImage() { 
        Image actual = ImageHandler.getErrorImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/error.png"));
    }
  
    @Test
    void testGetSuccessImage() {
        Image actual = ImageHandler.getSuccessImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/success.png"));
    }
  
    @Test
    void testGetMinusImage() {
        Image actual = ImageHandler.getMinusImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/minus.png"));
    }
  
    @Test
    void testGetColorLogoBkgImage() {
        Image actual = ImageHandler.getColorLogoBkgImage();
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains("/images/color_UMass_logo_background.png"));
    }
  
    @Test
    void testGetColorLogoBkgPath() {
        String expected = System.getProperty("user.dir") + File.separator + "color_UMass_logo_background.png";
        assertEquals(expected, ImageHandler.getColorLogoBkgPath());
    }
  
    @Test
    void testGetShipmentImage_NullColor() {
        assertThrows(
            NullPointerException.class, () -> ImageHandler.getShipmentImage(null)
        );
    }

    @ParameterizedTest
    @MethodSource("getShipmentImage_data")
    void testGetShipmentImage(String color, String url) {
        ImageHandler.AppColor c = ImageHandler.AppColor.valueOf(color);

        Image actual = ImageHandler.getShipmentImage(c);
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains(url));
    }

    private static Object[] getShipmentImage_data() {
        return new Object[] {
            new Object[] {ImageHandler.AppColor.UML_BLUE.name(), "/images/color_single_box.png"},
            new Object[] {ImageHandler.AppColor.DEFAULT_WHITE.name(), "/images/white_single_box.png"},
            new Object[] {ImageHandler.AppColor.DEFAULT_GREY.name(), "/images/grey_single_box.png"}
        };
    }
  
    @Test
    void testGetReferenceImage_InvalidColor() {
        assertThrows(
            NullPointerException.class, () -> ImageHandler.getReferenceImage(null)
        );
    }
  
    @ParameterizedTest
    @MethodSource("getReferenceImage_data")
    void testGetReferenceImage(String color, String url) {
        ImageHandler.AppColor c = ImageHandler.AppColor.valueOf(color);
        
        Image actual = ImageHandler.getReferenceImage(c);
        assertNotNull(actual);
        assertTrue(actual.getUrl().contains(url));
    }

    private static Object[] getReferenceImage_data() {
        return new Object[] {
            new Object[] {ImageHandler.AppColor.UML_BLUE.name(), "/images/color_paper.png"},
            new Object[] {ImageHandler.AppColor.DEFAULT_WHITE.name(), "/images/white_paper.png"},
            new Object[] {ImageHandler.AppColor.DEFAULT_GREY.name(), "/images/grey_paper.png"}
        };
    }
}
