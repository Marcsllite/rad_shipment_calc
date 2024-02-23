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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DBServiceImplTest {
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

    @Test
    public void testSetA1Dao() {
    }

    @Test
    public void testSetA2Dao() {
    }

    @Test
    public void testSetDecayConstantDao() {
    }

    @Test
    public void testSetExemptConDao() {
    }

    @Test
    public void testSetExemptLimitDao() {
    }

    @Test
    public void testSetHalfLifeDao() {
    }

    @Test
    public void testSetIsotopeDao() {
    }

    @Test
    public void testSetLimitsDao() {
    }

    @Test
    public void testSetReportableQuanDao() {
    }

    @Test
    public void testGetA1() {
    }

    @Test
    public void testGetA2() {
    }

    @Test
    public void testGetDecayConstant() {
    }

    @Test
    public void testGetExemptConcentration() {
    }

    @Test
    public void testGetExemptLimit() {
    }

    @Test
    public void testGetHalfLife() {
    }

    @Test
    public void testGetMatchingIsotopes() {
    }

    @Test
    public void testGetIsotope() {
    }

    @Test
    public void testGetIsotopeName() {
    }

    @Test
    public void testGetIsotopeAbbr() {
    }

    @Test
    public void testGetAllLimits() {
    }

    @Test
    public void testGetIALimited() {
    }

    @Test
    public void testGetIAPackage() {
    }

    @Test
    public void testGetLimited() {
    }

    @Test
    public void testGetReportQuan() {
    }

    @Test
    public void testGetCiReportQuan() {
    }

    @Test
    public void testGetTBqReportQuan() {
    }
}