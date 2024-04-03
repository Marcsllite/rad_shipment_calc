package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.IsotopeModelId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReferencePaneControllerTest {
    ReferencePaneController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = spy(new ReferencePaneController(new PropHandlerTestObj()));
        assertEquals(BaseController.Page.REFERENCE, controller.getPage());
    }

    @Test
    void testSetupTableDataLinking_NullIsotope() {
        doNothing().when(controller).unbindIsotopeConstants();

        controller.setupTableDataLinking(null);

        verify(controller).unbindIsotopeConstants();
        verify(controller, times(0)).bindIsotopeConstants(any());
        verify(controller, times(0)).selectBaseDropDownItems();
    }

    @Test
    void testDoesIsotopeNameMatch_NullIsotope() {
        assertFalse(controller.doesIsotopeNameMatch(null, ""));
    }

    @Test
    void testDoesIsotopeNameMatch_NullString() {
        Isotope isotope = new Isotope(new IsotopeModelId("Name", "Abbr"));
        assertTrue(controller.doesIsotopeNameMatch(isotope, null));
        assertTrue(controller.doesIsotopeNameMatch(isotope, ""));
        assertTrue(controller.doesIsotopeNameMatch(isotope, " "));
    }

    @Test
    void testDoesIsotopeNameMatch() {
        Isotope isotope = new Isotope(new IsotopeModelId("Ab", "Abbr"));
        assertTrue(controller.doesIsotopeNameMatch(isotope, "A"));
        assertTrue(controller.doesIsotopeNameMatch(isotope, "ab"));
        assertTrue(controller.doesIsotopeNameMatch(isotope, "aBb"));
        assertFalse(controller.doesIsotopeNameMatch(isotope, "Abbz"));
        assertTrue(controller.doesIsotopeNameMatch(isotope, "aBBR"));
    }
}