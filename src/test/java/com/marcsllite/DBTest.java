package com.marcsllite;

import com.marcsllite.service.DBService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DBTest {
    @Mock
    protected EntityManager em;
    @Mock
    protected DBService dbService;
}
