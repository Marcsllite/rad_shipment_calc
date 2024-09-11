package com.marcsllite.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OSUtilTest {
    private static final String ACTUAL_OS = System.getProperty("os.name");

    @AfterAll
    public static void afterAll() {
        System.setProperty("os.name", ACTUAL_OS);
    }

    @ParameterizedTest(name = "os.name = {0}, getOs = {1}")
    @MethodSource("getOs_data")
    void testGetOS(String osName, OSUtil.OS expectedOs) {
        if(osName == null) {
            System.clearProperty("os.name");
        } else {
            System.setProperty("os.name", osName);
        }
        assertEquals(expectedOs, OSUtil.getOS());
    }

    private static Object[] getOs_data() {
        return new Object[] {
            new Object[] {"win", OSUtil.OS.WINDOWS},
            new Object[] {"mac", OSUtil.OS.MAC},
            new Object[] {"nix", OSUtil.OS.UNIX},
            new Object[] {"nux", OSUtil.OS.UNIX},
            new Object[] {"aix", OSUtil.OS.UNIX},
            new Object[] {"sunos", OSUtil.OS.SOLARIS},
            new Object[] {"fake", OSUtil.OS.NOT_SUPPORTED},
            new Object[] {null, OSUtil.OS.NOT_SUPPORTED},
        };
    }
}