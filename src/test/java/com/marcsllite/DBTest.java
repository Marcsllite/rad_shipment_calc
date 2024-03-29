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

@ExtendWith(MockitoExtension.class)
public abstract class DBTest {
    protected MockedStatic<EntityManagerHandler> staticEmHandler;
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

    @BeforeEach
    public void setUp() {
        staticEmHandler = mockStatic(EntityManagerHandler.class);
        staticEmHandler.when(EntityManagerHandler::getInstance).thenReturn(emHandler);
    }

    @AfterEach
    public void tearDown() {
        if(!staticEmHandler.isClosed()) {
            staticEmHandler.close();
        }
    }

}
