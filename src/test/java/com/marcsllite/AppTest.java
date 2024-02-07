package com.marcsllite;

import com.marcsllite.util.FXIds;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.NodeMatchers;

public class AppTest extends GUITest {

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.MENU_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.HOME_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.REF_PANE, NodeMatchers.isInvisible());
    }
}
