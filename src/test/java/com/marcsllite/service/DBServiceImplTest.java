package com.marcsllite.service;

import com.marcsllite.dao.A1DaoImpl;
import com.marcsllite.dao.A2DaoImpl;
import com.marcsllite.dao.DecayConstantDaoImpl;
import com.marcsllite.dao.ExemptConcentrationDaoImpl;
import com.marcsllite.dao.ExemptLimitDaoImpl;
import com.marcsllite.dao.HalfLifeDaoImpl;
import com.marcsllite.dao.IsotopeDaoImpl;
import com.marcsllite.dao.LimitsDaoImpl;
import com.marcsllite.dao.ReportableQuantityDaoImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DBServiceImplTest {
    @Mock
    protected EntityManager em;
    @Mock
    private A1DaoImpl a1Dao;
    @Mock
    private A2DaoImpl a2Dao;
    @Mock
    private DecayConstantDaoImpl decayConstantDao;
    @Mock
    private ExemptConcentrationDaoImpl exemptConDao;
    @Mock
    private ExemptLimitDaoImpl exemptLimitDao;
    @Mock
    private HalfLifeDaoImpl halfLifeDao;
    @Mock
    private IsotopeDaoImpl isotopeDao;
    @Mock
    private LimitsDaoImpl limitsDao;
    @Mock
    private ReportableQuantityDaoImpl reportableQuanDao;
    @InjectMocks
    private DBServiceImpl dbService;

    @Disabled("Research DB Unit Tests")
    void testSetA1Dao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetA2Dao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetDecayConstantDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetExemptConDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetExemptLimitDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetHalfLifeDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetIsotopeDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetLimitsDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testSetReportableQuanDao() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetA1() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetA2() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetDecayConstant() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetExemptConcentration() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetExemptLimit() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetHalfLife() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetMatchingIsotopes() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetIsotope() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetIsotopeName() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetIsotopeAbbr() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetAllLimits() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetIALimited() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetIAPackage() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetLimited() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetReportQuan() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetCiReportQuan() {
    }

    @Disabled("Research DB Unit Tests")
    void testGetTBqReportQuan() {
    }
}