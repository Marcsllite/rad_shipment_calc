package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.db.NuclideModelId;
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
    void testSetupTableDataLinking_NullNuclide() {
        doNothing().when(controller).unbindNuclideConstants();

        controller.setupTableDataLinking(null);

        verify(controller).unbindNuclideConstants();
        verify(controller, times(0)).bindNuclideConstants(any());
        verify(controller, times(0)).selectBaseDropDownItems();
    }

    @Test
    void testDoesNuclideNameMatch_NullNuclide() {
        assertFalse(controller.doesNuclideNameMatch(null, ""));
    }

    @Test
    void testDoesNuclideNameMatch_NullString() {
        Nuclide nuclide = new Nuclide(1, "Name", new NuclideModelId("Sy", "1"));
        assertTrue(controller.doesNuclideNameMatch(nuclide, null));
        assertTrue(controller.doesNuclideNameMatch(nuclide, ""));
        assertTrue(controller.doesNuclideNameMatch(nuclide, " "));
    }

    @Test
    void testDoesNuclideNameMatch() {
        Nuclide nuclide = new Nuclide(1, "Abbreviation", new NuclideModelId("Ab", "1"));
        assertTrue(controller.doesNuclideNameMatch(nuclide, "A"));
        assertTrue(controller.doesNuclideNameMatch(nuclide, "ab"));
        assertTrue(controller.doesNuclideNameMatch(nuclide, "aB-"));
        assertFalse(controller.doesNuclideNameMatch(nuclide, "Ab-z"));
        assertTrue(controller.doesNuclideNameMatch(nuclide, "aB-1"));
    }
}