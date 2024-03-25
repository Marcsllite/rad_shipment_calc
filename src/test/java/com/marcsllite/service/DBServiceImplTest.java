package com.marcsllite.service;

import com.marcsllite.DBTest;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.dao.A1DaoImpl;
import com.marcsllite.dao.A2DaoImpl;
import com.marcsllite.dao.DecayConstantDaoImpl;
import com.marcsllite.dao.ExemptConcentrationDaoImpl;
import com.marcsllite.dao.ExemptLimitDaoImpl;
import com.marcsllite.dao.HalfLifeDaoImpl;
import com.marcsllite.dao.IsotopeDaoImpl;
import com.marcsllite.dao.LimitsDaoImpl;
import com.marcsllite.dao.ReportableQuantityDaoImpl;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.Limits;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ReportableQuantityModel;
import com.marcsllite.util.handler.EntityManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DBServiceImplTest extends DBTest {
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
    private DBServiceImpl service;

    @BeforeEach
    public void setUp() {
        staticEmHandler.when(EntityManagerHandler::getInstance).thenReturn(emHandler);
        service = spy(new DBServiceImpl(new PropHandlerTestObj()));
    }

    @Test
    void testGetA1() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getA1Dao()).thenReturn(a1Dao);
        when(a1Dao.getA1(abbr)).thenReturn(exp);

        assertEquals(exp, service.getA1(abbr));
        verify(a1Dao).getA1(abbr);
    }

    @Test
    void testGetA2() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getA2Dao()).thenReturn(a2Dao);
        when(a2Dao.getA2(abbr)).thenReturn(exp);

        assertEquals(exp, service.getA2(abbr));
        verify(a2Dao).getA2(abbr);
    }

    @Test
    void testGetDecayConstant() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getDecayConstantDao()).thenReturn(decayConstantDao);
        when(decayConstantDao.getDecayConstant(abbr)).thenReturn(exp);

        assertEquals(exp, service.getDecayConstant(abbr));
        verify(decayConstantDao).getDecayConstant(abbr);
    }

    @Test
    void testGetExemptConcentration() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getExemptConDao()).thenReturn(exemptConDao);
        when(exemptConDao.getExemptConcentration(abbr)).thenReturn(exp);

        assertEquals(exp, service.getExemptConcentration(abbr));
        verify(exemptConDao).getExemptConcentration(abbr);
    }

    @Test
    void testGetExemptLimit() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getExemptLimitDao()).thenReturn(exemptLimitDao);
        when(exemptLimitDao.getExemptLimit(abbr)).thenReturn(exp);

        assertEquals(exp, service.getExemptLimit(abbr));
        verify(exemptLimitDao).getExemptLimit(abbr);
    }

    @Test
    void testGetHalfLife() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getHalfLifeDao()).thenReturn(halfLifeDao);
        when(halfLifeDao.getHalfLife(abbr)).thenReturn(exp);

        assertEquals(exp, service.getHalfLife(abbr));
        verify(halfLifeDao).getHalfLife(abbr);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetAllIsotopes() {
        IsotopeModel model = new IsotopeModel();
        List<IsotopeModel> exp = new ArrayList<>();
        exp.add(model);

        when(service.getIsotopeDao()).thenReturn(isotopeDao);
        when(isotopeDao.getAllIsotopes()).thenReturn(exp);

        List<Isotope> actual = service.getAllIsotopes();
        assertEquals(model.getIsotopeId(), actual.get(0).getIsoId());
        verify(isotopeDao).getAllIsotopes();
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetIsotope_Null() {
        String name = "name";
        String abbr = "abbr";
        IsotopeModelId isoId = new IsotopeModelId(name, abbr);

        when(service.getIsotopeDao()).thenReturn(isotopeDao);
        when(isotopeDao.getIsotope(isoId)).thenReturn(null);

        assertNull(service.getIsotope(isoId));
        verify(isotopeDao).getIsotope(isoId);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetIsotope() {
        String name = "name";
        String abbr = "abbr";
        IsotopeModelId isoId = new IsotopeModelId(name, abbr);
        IsotopeModel model = new IsotopeModel(isoId);

        when(service.getIsotopeDao()).thenReturn(isotopeDao);
        when(isotopeDao.getIsotope(isoId)).thenReturn(model);

        assertEquals(model.getIsotopeId(), service.getIsotope(isoId).getIsoId());
        verify(isotopeDao).getIsotope(isoId);
    }

    @Test
    void testGetIsotopeName() {
        String name = "name";
        String abbr = "abbr";
        IsotopeModelId isoId = new IsotopeModelId(name, abbr);

        when(service.getIsotopeDao()).thenReturn(isotopeDao);
        when(isotopeDao.getIsotopeName(isoId)).thenReturn(name);

        assertEquals(name, service.getIsotopeName(isoId));
        verify(isotopeDao).getIsotopeName(isoId);
    }

    @Test
    void testGetIsotopeAbbr() {
        String name = "name";
        String abbr = "abbr";
        IsotopeModelId isoId = new IsotopeModelId(name, abbr);

        when(service.getIsotopeDao()).thenReturn(isotopeDao);
        when(isotopeDao.getIsotopeAbbr(isoId)).thenReturn(abbr);

        assertEquals(abbr, service.getIsotopeAbbr(isoId));
        verify(isotopeDao).getIsotopeAbbr(isoId);
    }

    @Test
    void testGetLimits_Null() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getLimits(limitsId)).thenReturn(null);

        assertNull(service.getLimits(limitsId));
        verify(limitsDao).getLimits(limitsId);
    }
    
    @Test
    void testGetLimits() {
        float expIaLim = 1.0f;
        float expIaPackage = 2.0f;
        float expLimited = 3.0f;
        LimitsModel model = new LimitsModel();
        model.setIaLimited(expIaLim);
        model.setIaPackage(expIaPackage);
        model.setLimited(expLimited);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getLimits(model.getLimitsId())).thenReturn(model);

        Limits actual = service.getLimits(model.getLimitsId());

        assertEquals(model.getLimitsId(), actual.getLimitsId());
        assertEquals(expIaLim, actual.getIaLimited());
        assertEquals(expIaPackage, actual.getIaPackage());
        assertEquals(expLimited, actual.getLimited());
        verify(limitsDao).getLimits(model.getLimitsId());
    }

    @Test
    void testGetIALimited() {
        float exp = 1.0f;
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIALimited(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getIALimited(limitsId));
        verify(limitsDao).getIALimited(limitsId);
    }

    @Test
    void testGetIAPackage() {
        float exp = 1.0f;
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIAPackage(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getIAPackage(limitsId));
        verify(limitsDao).getIAPackage(limitsId);
    }

    @Test
    void testGetLimited() {
        float exp = 1.0f;
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getLimited(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getLimited(limitsId));
        verify(limitsDao).getLimited(limitsId);
    }

    @Test
    void testGetReportQuan_Null() {
        String abbr = "abbr";
        
        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getReportQuan(abbr)).thenReturn(null);
    
        assertNull(service.getReportQuan(abbr));
        verify(reportableQuanDao).getReportQuan(abbr);
    }

    @Test
    void testGetReportQuan() {
        String abbr = "abbr";
        float expCi = 1.0f;
        float expTBq = 2.1f;
        ReportableQuantityModel model = new ReportableQuantityModel();
        model.setAbbr(abbr);
        model.setCurie(expCi);
        model.setTeraBq(expTBq);

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getReportQuan(abbr)).thenReturn(model);

        ReportableQuantity actual = service.getReportQuan(abbr);
        
        assertEquals(abbr, actual.getAbbr());
        assertEquals(expCi, actual.getCurie());
        assertEquals(expTBq, actual.getTeraBq());
        verify(reportableQuanDao).getReportQuan(abbr);
    }

    @Test
    void testGetCiReportQuan() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getCi(abbr)).thenReturn(exp);
        
        assertEquals(exp, service.getCiReportQuan(abbr));
        verify(reportableQuanDao).getCi(abbr);
    }

    @Test
    void testGetTBqReportQuan() {
        String abbr = "abbr";
        float exp = 1.0f;

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getTBq(abbr)).thenReturn(exp);

        assertEquals(exp, service.getTBqReportQuan(abbr));
        verify(reportableQuanDao).getTBq(abbr);
    }
}