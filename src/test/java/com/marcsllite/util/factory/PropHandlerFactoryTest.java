package com.marcsllite.util.factory;

import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropHandlerFactoryTest {
    PropHandlerFactory factory = spy(new PropHandlerFactory());
    private static final String baseName = "testBundle";
    private static final Locale locale = new Locale("en", "US");
    private static final String format = "xml";
    private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

    @Test
    public void testGetPropHandler_NullName() {
        try {
            factory.getPropHandler(null);
            verify(factory).newBundle(anyString(), any(), anyString(), any(), anyBoolean());
        } catch (IOException e) {
            fail("No exception should have been thrown");
        }
    }

    @Test
    public void testGetPropHandler() {
        try {
            String name = "Name";
            factory.getPropHandler(name);
            verify(factory).newBundle(anyString(), any(), anyString(), any(), anyBoolean());
        } catch (IOException e) {
            fail("No exception should have been thrown");
        }
    }

    @ParameterizedTest
    @MethodSource("nullParamChecker_data")
    void testNewBundle_NullParamChecker(String base, Locale loc, String form, ClassLoader load) {
        IllegalArgumentException exp = assertThrows(
            IllegalArgumentException.class, () -> factory.newBundle(base, loc, form, load, false)
        );
        assertEquals("baseName, locale, format and loader cannot be null", exp.getMessage());
    }

    private static Object[] nullParamChecker_data() {
        return new Object[] {
            new Object[] {null, locale, format, loader},
            new Object[] {baseName, null, format, loader},
            new Object[] {baseName, locale, null, loader},
            new Object[] {baseName, locale, format, null},
            new Object[] {null, null, null, null},
        };
    }

    @Test
    void testNewBundle_invalidFormat() {
        IllegalArgumentException exp = assertThrows(
            IllegalArgumentException.class, () -> factory.newBundle(baseName, locale, "java.properties", loader, false)
        );
        assertEquals("format must be xml", exp.getMessage());
    }

    @Test
    void testNewBundle_invalidResource() {
        try {
            assertNull(factory.newBundle("Invalid", locale, format, loader, false));
        } catch (IOException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    void testNewBundle_connectionFailure() {
        ClassLoader mLoader = mock(ClassLoader.class);
        URL url = mock(URL.class);

        try {
            when(mLoader.getResource(any())).thenReturn(url);
            when(url.openConnection()).thenReturn(null);
            assertNull(factory.newBundle(baseName, locale, format, mLoader, false));
        } catch (IOException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    void testNewBundle_reload() {
        ClassLoader mLoader = mock(ClassLoader.class);
        URL url = mock(URL.class);
        URLConnection connection = mock(URLConnection.class);

        try (InputStream stream = getClass().getResourceAsStream("/" + baseName.concat("_en_US.xml"))){
            when(mLoader.getResource(any())).thenReturn(url);
            when(url.openConnection()).thenReturn(connection);
            when(connection.getInputStream()).thenReturn(stream);
            factory.newBundle(baseName, locale, format, mLoader, true);

            assertFalse(connection.getUseCaches());
        } catch (IOException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    void testNewBundle() {
        try {
            Object ret = factory.newBundle(baseName, locale, format, loader, true);
            assertNotNull(ret);
            assertTrue(ret instanceof PropHandler);
        } catch (IOException e) {
            fail("No exceptions should be thrown.");
        }
    }

    @Test
    void testGetFormats() {
        List<String> ret = factory.getFormats(baseName);
        assertTrue(ret.contains("xml"));
        assertEquals(1, ret.size());
    }
}