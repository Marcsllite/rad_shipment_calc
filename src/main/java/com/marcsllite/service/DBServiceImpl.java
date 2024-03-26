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
import com.marcsllite.model.Isotope;
import com.marcsllite.model.Limits;
import com.marcsllite.model.ReportableQuantity;
import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.ReportableQuantityModel;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class DBServiceImpl implements DBService {
    protected static final String VALIDATION_QUERY = "select 1";
    private PropHandler propHandler;
    private final A1DaoImpl a1Dao;
    private final A2DaoImpl a2Dao;
    private final DecayConstantDaoImpl decayConstantDao;
    private final ExemptConcentrationDaoImpl exemptConDao;
    private final ExemptLimitDaoImpl exemptLimitDao;
    private final HalfLifeDaoImpl halfLifeDao;
    private final IsotopeDaoImpl isotopeDao;
    private final LimitsDaoImpl limitsDao;
    private final ReportableQuantityDaoImpl reportableQuanDao;

    public DBServiceImpl() {
        this(null);
    }

    public DBServiceImpl(PropHandler propHandler) {
        setPropHandler(propHandler == null? new PropHandlerFactory().getPropHandler(null) : propHandler);

        a1Dao = new A1DaoImpl();
        a2Dao = new A2DaoImpl();
        decayConstantDao = new DecayConstantDaoImpl();
        exemptConDao = new ExemptConcentrationDaoImpl();
        exemptLimitDao = new ExemptLimitDaoImpl();
        halfLifeDao = new HalfLifeDaoImpl();
        isotopeDao = new IsotopeDaoImpl();
        limitsDao = new LimitsDaoImpl();
        reportableQuanDao = new ReportableQuantityDaoImpl();
    }

    public int validateDb() {
        return (Integer) getIsotopeDao().findSingleResult(VALIDATION_QUERY);
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

    public IsotopeDaoImpl getIsotopeDao() {
        return  isotopeDao;
    }

    public LimitsDaoImpl getLimitsDao() {
        return limitsDao;
    }

    public ReportableQuantityDaoImpl getReportableQuanDao() {
        return reportableQuanDao;
    }

    @Override
    public float getA1(String abbr) {
        return getA1Dao().getA1(abbr);
    }

    @Override
    public float getA2(String abbr) {
        return getA2Dao().getA2(abbr);
    }

    @Override
    public float getDecayConstant(String abbr) {
        return getDecayConstantDao().getDecayConstant(abbr);
    }

    @Override
    public float getExemptConcentration(String abbr) {
        return getExemptConDao().getExemptConcentration(abbr);
    }

    @Override
    public float getExemptLimit(String abbr) {
        return getExemptLimitDao().getExemptLimit(abbr);
    }

    @Override
    public float getHalfLife(String abbr) {
        return getHalfLifeDao().getHalfLife(abbr);
    }

    @Override
    public int countAllIsotopes() {
        return getIsotopeDao().count();
    }

    @Override
    public ObservableList<Isotope> getAllIsotopes() {
        return getIsotopeDao().getAllIsotopes()
            .stream()
            .map(model -> new Isotope(model.getIsotopeId()) {
                @Override
                public PropHandler getPropHandler() {
                    return DBServiceImpl.this.getPropHandler();
                }
            })
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public Isotope getIsotope(IsotopeModelId modelId) {
        IsotopeModel model = getIsotopeDao().getIsotope(modelId);
        return model == null? null : new Isotope(model.getIsotopeId()) {
            @Override
            public PropHandler getPropHandler() {
                return DBServiceImpl.this.getPropHandler();
            }
        };
    }

    @Override
    public String getIsotopeName(IsotopeModelId modelId) {
        return getIsotopeDao().getIsotopeName(modelId);
    }

    @Override
    public String getIsotopeAbbr(IsotopeModelId modelId) {
        return getIsotopeDao().getIsotopeAbbr(modelId);
    }

    @Override
    public Limits getLimits(LimitsModelId modelId) {
        LimitsModel model = getLimitsDao().getLimits(modelId);
        return model == null? null : new Limits(model.getLimitsId(),
            model.getIaLimited(),
            model.getIaPackage(),
            model.getLimited()
        ) {
            @Override
            public PropHandler getPropHandler() {
                return DBServiceImpl.this.getPropHandler();
            }
        };
    }

    @Override
    public float getIALimited(LimitsModelId modelId) {
        return getLimitsDao().getIALimited(modelId);
    }

    @Override
    public float getIAPackage(LimitsModelId modelId) {
        return getLimitsDao().getIAPackage(modelId);
    }

    @Override
    public float getLimited(LimitsModelId modelId) {
        return getLimitsDao().getLimited(modelId);
    }

    @Override
    public ReportableQuantity getReportQuan(String abbr) {
        ReportableQuantityModel model = getReportableQuanDao().getReportQuan(abbr);
        return model == null? null : new ReportableQuantity(
            model.getAbbr(),
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
    public float getCiReportQuan(String abbr) {
        return getReportableQuanDao().getCi(abbr);
    }

    @Override
    public float getTBqReportQuan(String abbr) {
        return getReportableQuanDao().getTBq(abbr);
    }
}
