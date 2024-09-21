package com.marcsllite.util.handler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityManagerHandlerTest {
    @Mock
    EntityManagerFactory factoryMock;
    @Mock
    EntityManager emMock;

    @Test
    void testGetFactory_Null() {
        EntityManagerHandler handler = spy(EntityManagerHandler.getInstance());

        handler.setFactory(null);

        try(MockedStatic<Persistence> staticPersistence = mockStatic(Persistence.class)) {
            staticPersistence.when(() -> Persistence.createEntityManagerFactory(anyString()))
                .thenReturn(factoryMock);

            assertEquals(factoryMock, handler.getFactory());
        }
    }

    @Test
    void testGetEntityManager_Null() {
        EntityManagerHandler handler = spy(EntityManagerHandler.getInstance());
        handler.setFactory(factoryMock);

        when(factoryMock.createEntityManager()).thenReturn(emMock);

        handler.setEntityManager(null);

        assertEquals(emMock, handler.getEntityManager());
    }
}