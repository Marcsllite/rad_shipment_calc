package com.marcsllite;

import com.marcsllite.service.DBService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class DBTest {
    @Mock
    protected EntityManager em;
    @Mock
    protected Query query;
    @Mock
    protected DBService dbService;
}
