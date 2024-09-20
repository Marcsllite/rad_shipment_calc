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
import com.marcsllite.util.handler.StageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
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
    private final String expected = RadBigDecimal.valueOf(1.0f).toDisplayString();

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
        when(service.getA1Dao()).thenReturn(a1Dao);
        when(a1Dao.getA1(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getA1(NUCLIDE_ID));
        verify(a1Dao).getA1(NUCLIDE_ID);
    }

    @Test
    void testGetA2() {
        when(service.getA2Dao()).thenReturn(a2Dao);
        when(a2Dao.getA2(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getA2(NUCLIDE_ID));
        verify(a2Dao).getA2(NUCLIDE_ID);
    }

    @Test
    void testGetDecayConstant() {
        when(service.getDecayConstantDao()).thenReturn(decayConstantDao);
        when(decayConstantDao.getDecayConstant(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getDecayConstant(NUCLIDE_ID));
        verify(decayConstantDao).getDecayConstant(NUCLIDE_ID);
    }

    @Test
    void testGetExemptConcentration() {
        when(service.getExemptConDao()).thenReturn(exemptConDao);
        when(exemptConDao.getExemptConcentration(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getExemptConcentration(NUCLIDE_ID));
        verify(exemptConDao).getExemptConcentration(NUCLIDE_ID);
    }

    @Test
    void testGetExemptLimit() {
        when(service.getExemptLimitDao()).thenReturn(exemptLimitDao);
        when(exemptLimitDao.getExemptLimit(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getExemptLimit(NUCLIDE_ID));
        verify(exemptLimitDao).getExemptLimit(NUCLIDE_ID);
    }

    @Test
    void testGetHalfLife() {
        when(service.getHalfLifeDao()).thenReturn(halfLifeDao);
        when(halfLifeDao.getHalfLife(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getHalfLife(NUCLIDE_ID));
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
    @SetSystemProperty(key = StageHandler.KEEP_PLATFORM_OPEN_PROPERTY,value = "true")
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

        List<Nuclide> actual = service.getAllNuclides();
        assertEquals(model.getNuclideId(), actual.get(0).getNuclideId());
        verify(nuclideDao).getAllNuclides();
    }

    @Test
    @SetSystemProperty(key = StageHandler.KEEP_PLATFORM_OPEN_PROPERTY,value = "true")
    void testGetNuclide_Null() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);

        when(service.getNuclideDao()).thenReturn(nuclideDao);
        when(nuclideDao.getNuclide(nuclideId)).thenReturn(null);

        assertNull(service.getNuclide(nuclideId));
        verify(nuclideDao).getNuclide(nuclideId);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testGetNuclide() {
        String symbol = "symbol";
        String massNumber = "massNumber";
        NuclideModelId nuclideId = new NuclideModelId(symbol, massNumber);
        NuclideModel model = new NuclideModel(1, "Name", nuclideId);

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
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIALimited(limitsId)).thenReturn(expected);

        assertEquals(expected, service.getIALimited(limitsId));
        verify(limitsDao).getIALimited(limitsId);
    }

    @Test
    void testGetIAPackage() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getIAPackage(limitsId)).thenReturn(expected);

        assertEquals(expected, service.getIAPackage(limitsId));
        verify(limitsDao).getIAPackage(limitsId);
    }

    @Test
    void testGetLimited() {
        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.NORMAL);

        when(service.getLimitsDao()).thenReturn(limitsDao);
        when(limitsDao.getLimited(limitsId)).thenReturn(expected);

        assertEquals(expected, service.getLimited(limitsId));
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
        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getCi(NUCLIDE_ID)).thenReturn(expected);
        
        assertEquals(expected, service.getCiReportQuan(NUCLIDE_ID));
        verify(reportableQuanDao).getCi(NUCLIDE_ID);
    }

    @Test
    void testGetTBqReportQuan() {
        when(service.getReportableQuanDao()).thenReturn(reportableQuanDao);
        when(reportableQuanDao.getTBq(NUCLIDE_ID)).thenReturn(expected);

        assertEquals(expected, service.getTBqReportQuan(NUCLIDE_ID));
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