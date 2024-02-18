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
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DBServiceImpl implements DBService {
    private final PropHandler propHandler;
    private A1DaoImpl a1Dao;
    private A2DaoImpl a2Dao;
    private DecayConstantDaoImpl decayConstantDao;
    private ExemptConcentrationDaoImpl exemptConDao;
    private ExemptLimitDaoImpl exemptLimitDao;
    private HalfLifeDaoImpl halfLifeDao;
    private IsotopeDaoImpl isotopeDao;
    private LimitsDaoImpl limitsDao;
    private ReportableQuantityDaoImpl reportableQuanDao;

    public DBServiceImpl() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public DBServiceImpl(PropHandler propHandler) {
        this(propHandler, null);
    }

    public DBServiceImpl(PropHandler propHandler, EntityManager em) {
        this.propHandler = propHandler;
        setA1Dao(new A1DaoImpl(em));
        setA2Dao(new A2DaoImpl(em));
        setDecayConstantDao(new DecayConstantDaoImpl(em));
        setExemptConDao(new ExemptConcentrationDaoImpl(em));
        setExemptLimitDao(new ExemptLimitDaoImpl(em));
        setHalfLifeDao(new HalfLifeDaoImpl(em));
        setIsotopeDao(new IsotopeDaoImpl(em));
        setLimitsDao(new LimitsDaoImpl(em));
        setReportableQuanDao(new ReportableQuantityDaoImpl(em));
    }

    public void setA1Dao(A1DaoImpl a1Dao) {
        this.a1Dao = a1Dao;
    }

    public void setA2Dao(A2DaoImpl a2Dao) {
        this.a2Dao = a2Dao;
    }

    public void setDecayConstantDao(DecayConstantDaoImpl decayConstantDao) {
        this.decayConstantDao = decayConstantDao;
    }

    public void setExemptConDao(ExemptConcentrationDaoImpl exemptConDao) {
        this.exemptConDao = exemptConDao;
    }

    public void setExemptLimitDao(ExemptLimitDaoImpl exemptLimitDao) {
        this.exemptLimitDao = exemptLimitDao;
    }

    public void setHalfLifeDao(HalfLifeDaoImpl halfLifeDao) {
        this.halfLifeDao = halfLifeDao;
    }

    public void setIsotopeDao(IsotopeDaoImpl isotopeDao) {
        this.isotopeDao = isotopeDao;
    }

    public void setLimitsDao(LimitsDaoImpl limitsDao) {
        this.limitsDao = limitsDao;
    }

    public void setReportableQuanDao(ReportableQuantityDaoImpl reportableQuanDao) {
        this.reportableQuanDao = reportableQuanDao;
    }

    @Override
    public float getA1(String abbr) {
        return a1Dao.getA1(abbr);
    }

    @Override
    public float getA2(String abbr) {
        return a2Dao.getA2(abbr);
    }

    @Override
    public float getDecayConstant(String abbr) {
        return decayConstantDao.getDecayConstant(abbr);
    }

    @Override
    public float getExemptConcentration(String abbr) {
        return exemptConDao.getExemptConcentration(abbr);
    }

    @Override
    public float getExemptLimit(String abbr) {
        return exemptLimitDao.getExemptLimit(abbr);
    }

    @Override
    public float getHalfLife(String abbr) {
        return halfLifeDao.getHalfLife(abbr);
    }

    @Override
    public List<Isotope> getMatchingIsotopes(String substr) {
        return isotopeDao.getMatchingIsotopes(substr)
            .stream()
            .map(model -> new Isotope(new IsotopeModelId(model.getIsotopeId().getName(), model.getIsotopeId().getAbbr())))
            .collect(Collectors.toList());
    }

    @Override
    public Isotope getIsotope(IsotopeModelId modelId) {
        IsotopeModel model = isotopeDao.getIsotope(modelId);
        return model == null? null : new Isotope(new IsotopeModelId(model.getIsotopeId().getName(), model.getIsotopeId().getAbbr()));
    }

    @Override
    public String getIsotopeName(IsotopeModelId modelId) {
        return isotopeDao.getIsotopeName(modelId);
    }

    @Override
    public String getIsotopeAbbr(IsotopeModelId modelId) {
        return isotopeDao.getIsotopeAbbr(modelId);
    }

    @Override
    public Limits getAllLimits(LimitsModelId modelId) {
        LimitsModel model = limitsDao.getAllLimits(modelId);
        return model == null? null : new Limits(propHandler,
            new LimitsModelId(model.getLimitsId().getState(), model.getLimitsId().getForm()),
            model.getIa_limited(),
            model.getIa_package(),
            model.getLimited());
    }

    @Override
    public float getIALimited(LimitsModelId modelId) {
        return limitsDao.getIALimited(modelId);
    }

    @Override
    public float getIAPackage(LimitsModelId modelId) {
        return limitsDao.getIAPackage(modelId);
    }

    @Override
    public float getLimited(LimitsModelId modelId) {
        return limitsDao.getLimited(modelId);
    }

    @Override
    public ReportableQuantity getReportQuan(String abbr) {
        ReportableQuantityModel model = reportableQuanDao.getReportQuan(abbr);
        return model == null? null : new ReportableQuantity(propHandler, model.getAbbr(), model.getCurie(), model.getTeraBq());
    }

    @Override
    public float getCiReportQuan(String abbr) {
        return reportableQuanDao.getCi(abbr);
    }

    @Override
    public float getTBqReportQuan(String abbr) {
        return reportableQuanDao.getTBq(abbr);
    }
}
