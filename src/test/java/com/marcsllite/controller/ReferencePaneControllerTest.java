package com.marcsllite.controller;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

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
    }

    @Test
    void testSetupTableDataLinking_NullIsotope() {
        doNothing().when(controller).unbindIsotopeConstants();

        controller.setupTableDataLinking(null);

        verify(controller).unbindIsotopeConstants();
        verify(controller, times(0)).bindIsotopeConstants(any());
        verify(controller, times(0)).selectBaseDropDownItems();
    }
}