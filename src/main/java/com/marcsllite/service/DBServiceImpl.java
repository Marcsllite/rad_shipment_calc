package com.marcsllite.service;

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
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.stream.Collectors;

public class DBServiceImpl implements DBService {
    private static final Logger logr = LogManager.getLogger();
    protected static final String VALIDATION_QUERY = "select 1";
    private PropHandler propHandler;
    private final A1DaoImpl a1Dao;
    private final A2DaoImpl a2Dao;
    private final DecayConstantDaoImpl decayConstantDao;
    private final ExemptConcentrationDaoImpl exemptConDao;
    private final ExemptLimitDaoImpl exemptLimitDao;
    private final HalfLifeDaoImpl halfLifeDao;
    private final LimitsDaoImpl limitsDao;
    private final ReportableQuantityDaoImpl reportableQuanDao;
    private final ShipmentDaoImpl shipmentDao;
    private final NuclideDaoImpl nuclideDao;

    public DBServiceImpl() {
        this(null);
    }

    public DBServiceImpl(PropHandler propHandler) {
        try {
            this.propHandler = propHandler == null? new PropHandlerFactory().getPropHandler(null) : propHandler;
        } catch (IOException e) {
            logr.catching(e);
        }

        a1Dao = new A1DaoImpl();
        a2Dao = new A2DaoImpl();
        decayConstantDao = new DecayConstantDaoImpl();
        exemptConDao = new ExemptConcentrationDaoImpl();
        exemptLimitDao = new ExemptLimitDaoImpl();
        halfLifeDao = new HalfLifeDaoImpl();
        limitsDao = new LimitsDaoImpl();
        reportableQuanDao = new ReportableQuantityDaoImpl();
        shipmentDao = new ShipmentDaoImpl();
        nuclideDao = new NuclideDaoImpl();
    }

    public int validateDb() {
        return (Integer) getNuclideDao().findSingleResult(VALIDATION_QUERY);
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public A1DaoImpl getA1Dao() {
        return a1Dao;
    }

    public A2DaoImpl getA2Dao() {
        return  a2Dao;
    }

    public DecayConstantDaoImpl getDecayConstantDao() {
        return  decayConstantDao;
    }

    public ExemptConcentrationDaoImpl getExemptConDao() {
        return  exemptConDao;
    }

    public ExemptLimitDaoImpl getExemptLimitDao() {
        return  exemptLimitDao;
    }

    public HalfLifeDaoImpl getHalfLifeDao() {
        return  halfLifeDao;
    }

    public LimitsDaoImpl getLimitsDao() {
        return limitsDao;
    }

    public ReportableQuantityDaoImpl getReportableQuanDao() {
        return reportableQuanDao;
    }

    public ShipmentDaoImpl getShipmentDao() {
        return shipmentDao;
    }

    public NuclideDaoImpl getNuclideDao() {
        return nuclideDao;
    }

    @Override
    public String getA1(NuclideModelId nuclideId) {
        return getA1Dao().getA1(nuclideId);
    }

    @Override
    public String getA2(NuclideModelId nuclideId) {
        return getA2Dao().getA2(nuclideId);
    }

    @Override
    public String getDecayConstant(NuclideModelId nuclideId) {
        return getDecayConstantDao().getDecayConstant(nuclideId);
    }

    @Override
    public String getExemptConcentration(NuclideModelId nuclideId) {
        return getExemptConDao().getExemptConcentration(nuclideId);
    }

    @Override
    public String getExemptLimit(NuclideModelId nuclideId) {
        return getExemptLimitDao().getExemptLimit(nuclideId);
    }

    @Override
    public String getHalfLife(NuclideModelId nuclideId) {
        return getHalfLifeDao().getHalfLife(nuclideId);
    }

    @Override
    public int countAllNuclides() {
        return getNuclideDao().count();
    }

    @Override
    public ObservableList<NuclideModel> getAllNuclideModels() {
        return getNuclideDao().getAllNuclides().stream()
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<Nuclide> getAllNuclides() {
        return getNuclideDao().getAllNuclides()
            .stream()
            .map(model -> new Nuclide(model) {
                @Override
                public DBService getDbService() {
                    return DBServiceImpl.this;
                }
            })
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    
    @Override
    public NuclideModel getNuclide(NuclideModelId isoId) {
        return getNuclideDao().getNuclide(isoId);
    }

    @Override
    public String getNuclideNameNotation(NuclideModelId isoId) {
        return getNuclideDao().getNuclideNameNotation(isoId);
    }

    @Override
    public String getNuclideAbbrNotation(NuclideModelId isoId) {
        return getNuclideDao().getNuclideAbbrNotation(isoId);
    }

    @Override
    public Limits getLimits(LimitsModelId modelId) {
        LimitsModel model = getLimitsDao().getLimits(modelId);
        return model == null? null : new Limits(model.getLimitsId(),
            model.getIaLimitedStr(),
            model.getIaPackageStr(),
            model.getLimitedStr()
        );
    }

    @Override
    public String getIALimited(LimitsModelId modelId) {
        return getLimitsDao().getIALimited(modelId);
    }

    @Override
    public String getIAPackage(LimitsModelId modelId) {
        return getLimitsDao().getIAPackage(modelId);
    }

    @Override
    public String getLimited(LimitsModelId modelId) {
        return getLimitsDao().getLimited(modelId);
    }

    @Override
    public ReportableQuantity getReportQuan(NuclideModelId nuclideId) {
        ReportableQuantityModel model = getReportableQuanDao().getReportQuan(nuclideId);
        return model == null? null : new ReportableQuantity(
            model.getNuclideId(),
            model.getCurie(),
            model.getTeraBq()
        ) {
            @Override
            public PropHandler getPropHandler() {
                return DBServiceImpl.this.getPropHandler();
            }
        };
    }

    @Override
    public String getCiReportQuan(NuclideModelId nuclideId) {
        return getReportableQuanDao().getCi(nuclideId);
    }

    @Override
    public String getTBqReportQuan(NuclideModelId nuclideId) {
        return getReportableQuanDao().getTBq(nuclideId);
    }

    @Override
    public Shipment getShipment(Long id) {
        return new Shipment(getShipmentDao().getShipment(id));
    }
}
