package com.marcsllite.util.controller;

import com.marcsllite.model.db.NuclideModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertFalse;

@ExtendWith(MockitoExtension.class)
class ModifyUtilsTest {
    @Test
    void testDoesNuclideMatchSearch_NullEmptyParam() {
        assertFalse(ModifyUtils.doesNuclideMatchSearch(null, null));
        assertFalse(ModifyUtils.doesNuclideMatchSearch(null, " "));
        assertFalse(ModifyUtils.doesNuclideMatchSearch(new NuclideModel(), null));
        assertFalse(ModifyUtils.doesNuclideMatchSearch(new NuclideModel(), " "));
    }
}
