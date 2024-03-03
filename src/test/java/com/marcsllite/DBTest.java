package com.marcsllite;

import com.marcsllite.service.DBService;
import com.marcsllite.util.handler.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class DBTest {
    protected final MockedStatic<EntityManagerHandler> staticEmHandler;
    @Mock
    protected EntityManagerHandler emHandler;
    @Mock
    private EntityManagerFactory factory;
    @Mock
    protected EntityManager em;
    @Mock
    protected Query query;
    @Mock
    protected DBService dbService;

    public DBTest() {
        staticEmHandler = mockStatic(EntityManagerHandler.class);
    }

    @BeforeEach
    public void setUp() {
        staticEmHandler.when(EntityManagerHandler::getInstance).thenReturn(emHandler);
        when(emHandler.getEntityManager()).thenReturn(em);
    }

    @AfterEach
    public void tearDown() {
        staticEmHandler.close();
    }

}
