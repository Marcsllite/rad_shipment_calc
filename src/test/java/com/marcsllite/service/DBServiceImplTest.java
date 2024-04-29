package com.marcsllite.service;

import com.marcsllite.DBTest;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.dao.A1DaoImpl;
import com.marcsllite.dao.A2DaoImpl;
import com.marcsllite.dao.DecayConstantDaoImpl;
import com.marcsllite.dao.ExemptConcentrationDaoImpl;
import com.marcsllite.dao.ExemptLimitDaoImpl;
import com.marcsllite.dao.HalfLifeDaoImpl;
import com.marcsllite.dao.LimitsDaoImpl;
import com.marcsllite.dao.NuclideDaoImpl;
import com.marcsllite.dao.ReportableQuantityDaoImpl;
import com.marcsllite.dao.ShipmentDaoImpl;
import com.marcsllite.model.Limits;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.model.db.ReportableQuantityModel;
import com.marcsllite.model.db.ShipmentModel;
import com.marcsllite.util.RadBigDecimal;
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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
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
    private NuclideDaoImpl nuclideDao;
    @Mock
    private LimitsDaoImpl limitsDao;
    @Mock
    private ReportableQuantityDaoImpl reportableQuanDao;
    @Mock
    private ShipmentDaoImpl shipmentDao;
    @InjectMocks
    private DBServiceImpl service;
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId NUCLIDE_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);

    @BeforeEach
    public void setUp() {
        super.setUp();
        service = spy(new DBServiceImpl(new PropHandlerTestObj()));
    }

    @Test
    void testValidateDB() {
        int exp = 1;

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.findSingleResult(anyString())).thenReturn(exp);

        assertEquals(exp, service.validateDb());
    }

    @Test
    void testGetA1() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getA1Dao()).thenReturn(a1Dao);
        when(a1Dao.getA1(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getA1(NUCLIDE_ID));
        verify(a1Dao).getA1(NUCLIDE_ID);
    }

    @Test
    void testGetA2() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getA2Dao()).thenReturn(a2Dao);
        when(a2Dao.getA2(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getA2(NUCLIDE_ID));
        verify(a2Dao).getA2(NUCLIDE_ID);
    }

    @Test
    void testGetDecayConstant() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getDecayConstantDao()).thenReturn(decayConstantDao);
        when(decayConstantDao.getDecayConstant(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getDecayConstant(NUCLIDE_ID));
        verify(decayConstantDao).getDecayConstant(NUCLIDE_ID);
    }

    @Test
    void testGetExemptConcentration() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getExemptConDao()).thenReturn(exemptConDao);
        when(exemptConDao.getExemptConcentration(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getExemptConcentration(NUCLIDE_ID));
        verify(exemptConDao).getExemptConcentration(NUCLIDE_ID);
    }

    @Test
    void testGetExemptLimit() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getExemptLimitDao()).thenReturn(exemptLimitDao);
        when(exemptLimitDao.getExemptLimit(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getExemptLimit(NUCLIDE_ID));
        verify(exemptLimitDao).getExemptLimit(NUCLIDE_ID);
    }

    @Test
    void testGetHalfLife() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getHalfLifeDao()).thenReturn(halfLifeDao);
        when(halfLifeDao.getHalfLife(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getHalfLife(NUCLIDE_ID));
        verify(halfLifeDao).getHalfLife(NUCLIDE_ID);
    }

    @Test
    void testCountAllNuclides() {
        int exp = 10;

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.count()).thenReturn(exp);

        assertEquals(exp, service.countAllNuclides());
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetAllNuclideModels() {
        NuclideModel model = new NuclideModel();
        List<NuclideModel> exp = new ArrayList<>();
        exp.add(model);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getAllNuclides()).thenReturn(exp);

        assertEquals(exp, service.getAllNuclideModels());
        verify(nuclideDao).getAllNuclides();
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetAllNuclides() {
        NuclideModel model = new NuclideModel();
        List<NuclideModel> exp = new ArrayList<>();
        exp.add(model);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getAllNuclides()).thenReturn(exp);
        isoConstantsDbInit(model.getNuclideId());

        List<Nuclide> actual = service.getAllNuclides();
        assertEquals(model.getNuclideId(), actual.get(0).getNuclideId());
        verify(nuclideDao).getAllNuclides();
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetNuclide_Null() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getNuclide(nuclideId)).thenReturn(null);

        assertNull(service.getNuclide(nuclideId));
        verify(nuclideDao).getNuclide(nuclideId);
    }

    protected void isoConstantsDbInit(NuclideModelId nuclideId) {
        LimitsModelId limitsId = new LimitsModelId();
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        doReturn(exp).when(service).getA1(nuclideId);
        doReturn(exp).when(service).getA2(nuclideId);
        doReturn(exp).when(service).getDecayConstant(nuclideId);
        doReturn(exp).when(service).getExemptConcentration(nuclideId);
        doReturn(exp).when(service).getExemptLimit(nuclideId);
        doReturn(exp).when(service).getHalfLife(nuclideId);
        doReturn(exp).when(service).getIALimited(limitsId);
        doReturn(exp).when(service).getIAPackage(limitsId);
        doReturn(exp).when(service).getLimited(limitsId);
        doReturn(exp).when(service).getCiReportQuan(nuclideId);
        doReturn(exp).when(service).getTBqReportQuan(nuclideId);

//        when(service.getA1Dao()).thenReturn(a1Dao);
//        when(a1Dao.getA1(nuclideId)).thenReturn(exp);
//
//        when(service.getA2Dao()).thenReturn(a2Dao);
//        when(a2Dao.getA2(nuclideId)).thenReturn(exp);
//
//        when(service.getDecayConstantDao()).thenReturn(decayConstantDao);
//        when(decayConstantDao.getDecayConstant(nuclideId)).thenReturn(exp);
//
//        when(service.getExemptConDao()).thenReturn(exemptConDao);
//        when(exemptConDao.getExemptConcentration(nuclideId)).thenReturn(exp);
//
//        when(service.getExemptLimitDao()).thenReturn(exemptLimitDao);
//        when(exemptLimitDao.getExemptLimit(nuclideId)).thenReturn(exp);
//
//        when(service.getHalfLifeDao()).thenReturn(halfLifeDao);
//        when(halfLifeDao.getHalfLife(nuclideId)).thenReturn(exp);
//
//        when(service.getLimitsDao()).thenReturn(limitsDao);
//        when(limitsDao.getIALimited(limitsId)).thenReturn(exp);
//        when(limitsDao.getIAPackage(limitsId)).thenReturn(exp);
//        when(limitsDao.getLimited(limitsId)).thenReturn(exp);
//
//        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
//        when(reportableQuanDao.getCi(nuclideId)).thenReturn(exp);
//        when(reportableQuanDao.getTBq(nuclideId)).thenReturn(exp);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetNuclide() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);
        NuclideModel model = new NuclideModel(nuclideId);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getNuclide(nuclideId)).thenReturn(model);

        assertEquals(model.getNuclideId(), service.getNuclide(nuclideId).getNuclideId());
        verify(nuclideDao).getNuclide(nuclideId);
    }

    @Test
    void testGetNuclideNameNotation() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getNuclideNameNotation(nuclideId)).thenReturn(symbol);

        assertEquals(symbol, service.getNuclideNameNotation(nuclideId));
        verify(nuclideDao).getNuclideNameNotation(nuclideId);
    }

    @Test
    void testGetNuclideAbbrNotation() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getNuclideAbbrNotation(nuclideId)).thenReturn(massNumber);

        assertEquals(massNumber, service.getNuclideAbbrNotation(nuclideId));
        verify(nuclideDao).getNuclideAbbrNotation(nuclideId);
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
        RadBigDecimal expIaLim = RadBigDecimal.valueOf(1.0f);
        RadBigDecimal expIaPackage = RadBigDecimal.valueOf(2.0f);
        RadBigDecimal expLimited = RadBigDecimal.valueOf(3.0f);
        LimitsModel model = new LimitsModel();
        model.setIaLimitedStr(expIaLim.toString());
        model.setIaPackageStr(expIaPackage.toString());
        model.setLimitedStr(expLimited.toString());

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
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIALimited(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getIALimited(limitsId));
        verify(limitsDao).getIALimited(limitsId);
    }

    @Test
    void testGetIAPackage() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIAPackage(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getIAPackage(limitsId));
        verify(limitsDao).getIAPackage(limitsId);
    }

    @Test
    void testGetLimited() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getLimited(limitsId)).thenReturn(exp);

        assertEquals(exp, service.getLimited(limitsId));
        verify(limitsDao).getLimited(limitsId);
    }

    @Test
    void testGetReportQuan_Null() {
        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getReportQuan(NUCLIDE_ID)).thenReturn(null);
    
        assertNull(service.getReportQuan(NUCLIDE_ID));
        verify(reportableQuanDao).getReportQuan(NUCLIDE_ID);
    }

    @Test
    void testGetReportQuan() {
        RadBigDecimal expCi = RadBigDecimal.valueOf(1.0f);
        RadBigDecimal expTBq = RadBigDecimal.valueOf(2.2f);
        ReportableQuantityModel model = new ReportableQuantityModel();
        model.setNuclideId(NUCLIDE_ID);
        model.setCurieStr(expCi.toString());
        model.setTeraBqStr(expTBq.toString());

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getReportQuan(NUCLIDE_ID)).thenReturn(model);

        ReportableQuantity actual = service.getReportQuan(NUCLIDE_ID);
        
        assertEquals(NUCLIDE_ID, actual.getNuclideId());
        assertEquals(expCi, actual.getCurie());
        assertEquals(expTBq, actual.getTeraBq());
        verify(reportableQuanDao).getReportQuan(NUCLIDE_ID);
    }

    @Test
    void testGetCiReportQuan() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getCi(NUCLIDE_ID)).thenReturn(exp);
        
        assertEquals(exp, service.getCiReportQuan(NUCLIDE_ID));
        verify(reportableQuanDao).getCi(NUCLIDE_ID);
    }

    @Test
    void testGetTBqReportQuan() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getTBq(NUCLIDE_ID)).thenReturn(exp);

        assertEquals(exp, service.getTBqReportQuan(NUCLIDE_ID));
        verify(reportableQuanDao).getTBq(NUCLIDE_ID);
    }

    @Test
    void testGetShipment() {
        long id = -1L;
        ShipmentModel model = new ShipmentModel();
        Shipment exp = new Shipment(model);

        when(service.getShipmentDao()).thenReturn(shipmentDao);
        when(shipmentDao.getShipment(id)).thenReturn(model);

        Shipment actual = service.getShipment(id);

        assertNotNull(actual);
        assertEquals(exp, actual);
    }
}