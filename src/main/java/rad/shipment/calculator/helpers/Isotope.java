package rad.shipment.calculator.helpers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.joda.time.LocalDateTime;
import rad.shipment.calculator.gui.Main;

import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Isotope {
    // Declaring Variables
    private static final Logger LOGR = Logger.getLogger(Isotope.class.getName());  // getting logger
    private final float defaultVal = (float)Main.getInt("defaultInt");  // default float value for uninitialized variables
    private final DatabaseEditor dbEditor = Main.getDBEditor();  // getting the database editor from the main class
    private final SimpleStringProperty _Name;                    // The name of the isotope
    private final SimpleFloatProperty _A0;                      // Initial Activity (microCi) of isotope
    private final SimpleFloatProperty _ConcentrationToday;      // Today's concentration (microCi/gram or microCi/liter) of isotope
    private final SimpleFloatProperty _DecaysPerMinute;         // Decays per minute of isotope
    private final SimpleFloatProperty _ActivityConcentration;   // Activity Concentration (Bq) of isotope (Activity of isotope / sum of entire package concentration)
    private final SimpleFloatProperty _ActivityFraction;        // Activity Fraction (Bq) of isotope (Activity fraction of isotope / sum of entire package activity)
    private final SimpleFloatProperty _LimitedQuanMultiplier;   // Limited Quantities limit multiplier  (see 173.425_Table 4)
    private final SimpleFloatProperty _ReportableQuanFraction;  // Fraction of Reportable Quantity of isotope (Activity Today (microCi) / Reportable Quantity (microCuries))
    private final SimpleFloatProperty _LimitPercentage;         // Activity Percentage of isotope limit (activity of isotope / activity limit of isotope)
    private final SimpleFloatProperty _ConcentrationPercentage;  // Activity concentration of isotope (activity concentration of isotope / activity concentration limit of isotope)
    private final SimpleFloatProperty _LicensePercentage;       // Percentage of licensing limit (activity of isotope / license_limit from Info table in database)
    private final SimpleFloatProperty _ALimit;                  // A1 value (TBq) of isotope if Special form, A2 value (TBq) of isotope if Normal Form
    private final SimpleFloatProperty _HRCQLimit;               // Highway Route Control Limit (either 1000 * TBq or 3000 * A1/A2 whichever is the lowest number)
    private final SimpleBooleanProperty _isFissile;              // true if isotope is fissile
    private final SimpleBooleanProperty _isReportableQuan;      // true if isotope is a reportable quantity
    private final SimpleBooleanProperty _isLong;                // for isotopes with different half lives, true if longer half life else false
    private final SimpleIntegerProperty _isotopeClass;          // Classification of isotope as an integer
                                                                // (0 = Exempt, 1 = Excepted, 2 = Type A, 4 = Type B, 8 = Type B: Highway Route Control)
    // Constant values from database
    private final SimpleFloatProperty _A1;                  // A1 (TBq) of isotope (from Info table in database)
    private final SimpleFloatProperty _A2;                  // A2 (TBq) of isotope (from Info table in database)
    private final SimpleFloatProperty _DecayConstant;       // Decay Constant (1 / halflife(days)) of isotope (from Info table in database)
    private final SimpleFloatProperty _ExemptConcentration; // Exempt Concentration (Bq/gram) of isotope (from Info table in database)
    private final SimpleFloatProperty _ExemptLimit;         // Exempt Limit (Bq)of isotope (from Info table in database)
    private final SimpleFloatProperty _HalfLife;            // Halflife (days) of isotope (from Info table in database)
    private final SimpleFloatProperty _IALimitedMultiplier; // Instruments/Articles multiplier (see 173.425_Table 4) of isotope (from Info table in database)
    private final SimpleFloatProperty _IAPackageLimit;      // Instruments/Articles package limit multiplier (see 173.425_Table 4) of isotope (from Info table in database)
    private final SimpleFloatProperty _LicenseLimit;        // Licensing Limit (microCi) of isotope (from Info table in database)
    private final SimpleFloatProperty _LimitedLimit;        // limited Limit (TBq) of isotope (from Info table in database)
    private final SimpleFloatProperty _ReportableQuan;      // Reportable Quantity (Ci) of isotope (from Info table in database)

    /*/////////////////////////////////////////////////// ISOTOPE ////////////////////////////////////////////////////*/
    /**
     * Constructs an Isotope object with the given name and initial activity
     *
     * @param name the name of the isotope
     * @param A0 the initial activity (microCi) of the isotope
     */
    Isotope(String name, float A0) throws RuntimeException {
        try {
            // getting the values from the database
            _A1 = new SimpleFloatProperty(dbEditor.getA1(name));
            _A2 = new SimpleFloatProperty(dbEditor.getA2(name));
            _DecayConstant = new SimpleFloatProperty(dbEditor.getDecayConstant(name));
            _ExemptConcentration = new SimpleFloatProperty(dbEditor.getExemptConcentration(name));
            _ExemptLimit = new SimpleFloatProperty(dbEditor.getExemptLimit(name));
            _HalfLife = new SimpleFloatProperty(dbEditor.getHalfLife(name));
            _IALimitedMultiplier = new SimpleFloatProperty(dbEditor.getIALimitedMultiplier(name));
            _IAPackageLimit = new SimpleFloatProperty(dbEditor.getIAPackageLimit(name));
            _LicenseLimit = new SimpleFloatProperty(dbEditor.getLicenseLimit(name));
            _LimitedLimit = new SimpleFloatProperty(dbEditor.getLimitedLimit(name));
            _ReportableQuan = new SimpleFloatProperty(dbEditor.getReportableQuantity(name));
        } catch (InvalidParameterException e) {
            LOGR.log(Level.SEVERE, "Failed to create isotope named " + name + ". Error: ", e);
            throw new RuntimeException("Failed to create isotope named " + name);
        }

        // Saving isotope name
        _Name = new SimpleStringProperty(name);

        // making sure initial activity is valid
        if(A0 <= 0) throw new InvalidParameterException("Initial Activity of isotope cannot be less than or equal to 0");

        _A0 = new SimpleFloatProperty(A0);
        _ConcentrationToday = new SimpleFloatProperty(defaultVal);
        _DecaysPerMinute = new SimpleFloatProperty(defaultVal);
        _ActivityConcentration = new SimpleFloatProperty(defaultVal);
        _ActivityFraction = new SimpleFloatProperty(defaultVal);
        _LimitedQuanMultiplier = new SimpleFloatProperty(defaultVal);
        _ReportableQuanFraction = new SimpleFloatProperty(defaultVal);
        _LimitPercentage = new SimpleFloatProperty(defaultVal);
        _ConcentrationPercentage = new SimpleFloatProperty(defaultVal);
        _LicensePercentage = new SimpleFloatProperty(defaultVal);
        _ALimit = new SimpleFloatProperty(defaultVal);
        _HRCQLimit = new SimpleFloatProperty(defaultVal);
        _isFissile = new SimpleBooleanProperty(false);
        _isReportableQuan = new SimpleBooleanProperty(false);
        _isLong = new SimpleBooleanProperty(false);
        _isotopeClass = new SimpleIntegerProperty((int)defaultVal);
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/
    /**
     * Helper function to get this isotope's decay date
     *
     * @return the decay date of this isotope
     */
    public LocalDateTime getDecayDate() {
        // TODO: finish getDecayDate function
        return null;
    }

    /**
     * Helper function to get this isotope's current Activity
     *
     * @return the current activity of this isotope
     */
    public float getActivity() {
        // TODO: finish getActivity function
        return defaultVal;
    }

    /**
     * Helper function to get the date this isotope will be exempt from licensing
     *
     * @return the date this isotope will be exempt from licensing
     */
    public LocalDateTime getDateForExemptLicensing() {
        // TODO: finish getDateForExemptLicensing function
        return null;
    }

    /**
     * Helper function to get the date this isotope will be exempt from shipping
     *
     * @return the date this isotope will be exempt from shipping
     */
    public LocalDateTime getDateForExemptShipping() {
        // TODO: finish getDateForExemptShipping function
        return null;
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get this isotope's name
     *
     * @return the name of this isotope
     */
    public String get_Name() { return _Name.get(); }

    /**
     * Getter function to get this isotope's initial activity in microCi
     * 
     * @return the initial activity of this isotope in microCi
     */
    public float get_A0() { return _A0.get(); }

    /**
     * Getter function to get this isotope's current concentration value (as of today)
     * in microCi/gram or microCi/liter
     *
     * @return the current concentration as of today of this isotope in microCi/gram or microCi/liter
     */
    public float get_ConcentrationToday() { return _ConcentrationToday.get(); }

    /**
     * Getter function to get this isotope's decays per minute
     *
     * @return the decays per minute of this isotope
     */
    public float get_DecaysPerMinute() { return _DecaysPerMinute.get(); }

    /**
     * Getter function to get this isotope's activity concentration in (Bq)
     * Activity Concentration = Activity of isotope / sum of entire package concentration
     *
     * @return the activity concentration of this isotope
     */
    public float get_ActivityConcentration() { return _ActivityConcentration.get(); }

    /**
     * Getter function to get this isotope's initial activity fraction in Bq
     * Activity Fraction = Activity fraction of isotope / sum of entire package activity
     *
     * @return the activity fraction of this isotope
     */
    public float get_ActivityFraction() { return _ActivityFraction.get(); }

    /**
     * Getter function to get this isotope's limited quantity multiplier
     * from 173.425_Table 4
     *
     * @return the limited quantity multiplier of this isotope
     */
    public float get_LimitedQuanMultiplier() { return _LimitedQuanMultiplier.get(); }

    /**
     * Getter function to get this isotope's reportable quantity fraction
     * Reportable Quantity Fraction = Activity Today (microCi) / Reportable Quantity (microCuries)
     *
     * @return the reportable quantity fraction of this isotope
     */
    public float get_ReportableQuanFraction() { return _ReportableQuanFraction.get(); }

    /**
     * Getter function to get this isotope's activity percentage of the limit
     * Limit Percentage = (activity of isotope / activity limit of isotope)
     *
     * @return the activity percentage of this isotope
     */
    public float get_LimitPercentage() { return _LimitPercentage.get(); }

    /**
     * Getter function to get this isotope's activity concentration of the limit
     * Concentration Percentage = activity concentration of isotope / activity concentration limit of isotope
     *
     * @return the activity concentration percentage of this isotope
     */
    public float get_ConcentrationPercentage() { return _ConcentrationPercentage.get(); }

    /**
     * Getter function to get this isotope's percentage of licensing limit
     * License percentage activity of isotope / license_limit (from Info table in database)
     *
     * @return the percentage of licensing limit of this isotope
     */
    public float get_LicensePercentage() { return _LicensePercentage.get(); }

    /**
     * Getter function to get this isotope's A1 or A2 value (TBq)
     * For Special Form Isotopes: A1 value (TBq)
     * For Normal Form Isotopes: A2 value (TBq)
     *
     * @return the A1 or A2 value (TBq) of this isotope
     */
    public float get_ALimit() { return _ALimit.get(); }

    /**
     * Getter function to get this isotope's Highway Route Control Quantity Limit
     * Highway Route Control Quantity Limit = 1000 * TBq
     *                                            or
     *                                       3000 * A1/A2
     *                                 (whichever is the lowest number)
     *
     * @return the Highways Route Control Quantity Limit of this isotope
     */
    public float get_HRCQLimit() { return _HRCQLimit.get(); }

    /**
     * Getter function to get whether this isotope is fissile or not
     *
     * @return true if isotope is fissile, else false
     */
    public boolean get_isFissile() { return _isFissile.get(); }

    /**
     * Getter function to get whether this isotope is a reportable quantity or not
     *
     * @return true if isotope is a reportable quantity, else false
     */
    public boolean get_isReportableQuan() { return _isReportableQuan.get(); }

    /**
     * Getter function to get whether this isotope has different half lives
     * and this is the isotope with the longer half life
     *
     * @return true if this has the longer half life, else false
     */
    public boolean get_isLong() { return _isLong.get(); }

    /**
     * Getter function to get this isotope's classification as an integer
     *  0 = Exempt Classification
     *  1 = Excepted/Limited Classification
     *  2 = Type A Classification
     *  4 = Type B Classification
     *  8 = Type B: Highway Route Control Classification
     *
     * @return the classification of this isotope as an integer
     */
    public int get_isotopeClass() { return _isotopeClass.get(); }

    /**
     * Getter function to get this isotope's A1 value (from Info table in database)
     *
     * @return the A1 value of this isotope (from Info table in database)
     */
    public float get_A1() { return _A1.get(); }

    /**
     * Getter function to get this isotope's A2 value (from Info table in database)
     *
     * @return the A2 value of this isotope (from Info table in database)
     */
    public float get_A2() { return _A2.get(); }

    /**
     * Getter function to get this isotope's decay constant (from Info table in database)
     *
     * @return the decay constant of this isotope (from Info table in database)
     */
    public float get_DecayConstant() { return _DecayConstant.get(); }

    /**
     * Getter function to get this isotope's exempt concentration (from Info table in database)
     *
     * @return the exempt concentration of this isotope (from Info table in database)
     */
    public float get_ExemptConcentration() { return _ExemptConcentration.get(); }

    /**
     * Getter function to get this isotope's exempt limit (from Info table in database)
     *
     * @return the exempt limit of this isotope (from Info table in database)
     */
    public float get_ExemptLimit() { return _ExemptLimit.get(); }

    /**
     * Getter function to get this isotope's halflife (from Info table in database)
     *
     * @return the halflife of this isotope (from Info table in database)
     */
    public float get_HalfLife() { return _HalfLife.get(); }

    /**
     * Getter function to get this isotope's instruments/articles limited limit (from Info table in database)
     *
     * @return the instruments/articles limited limit of this isotope (from Info table in database)
     */
    public float get_IALimitedMultiplier() { return _IALimitedMultiplier.get(); }

    /**
     * Getter function to get this isotope's instruments/articles package limit (from Info table in database)
     *
     * @return the instruments/articles package limit of this isotope (from Info table in database)
     */
    public float get_IAPackageLimit() { return _IAPackageLimit.get(); }

    /**
     * Getter function to get this isotope's license limit (from Info table in database)
     *
     * @return the license limit of this isotope (from Info table in database)
     */
    public float get_LicenseLimit() { return _LicenseLimit.get(); }

    /**
     * Getter function to get this isotope's limited limit (from Info table in database)
     *
     * @return the limited limit of this isotope (from Info table in database)
     */
    public float get_LimitedLimit() { return _LimitedLimit.get(); }

    /**
     * Getter function to get this isotope's reportable quantity limit (from Info table in database)
     *
     * @return the reportable quantity limit of this isotope (from Info table in database)
     */
    public float get_ReportableQuan() { return _ReportableQuan.get(); }
    
    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    // TODO: Add Javadocs to set_Name
    public void set_Name(String name) { _Name.set(name); }

    // TODO: Add Javadocs to set_A0
    public void set_A0(float A0) { _A0.set(A0); }

    // TODO: Add Javadocs to set_ConcentrationToday
    public void set_ConcentrationToday(float CToday) { _ConcentrationToday.set(CToday); }

    // TODO: Add Javadocs to set_DecaysPerMinute
    public void set_DecaysPerMinute(float dpm) { _DecaysPerMinute.set(dpm); }

    // TODO: Add Javadocs to set_ActivityConcentration
    public void set_ActivityConcentration(float ACon) { _ActivityConcentration.set(ACon); }

    // TODO: Add Javadocs to set_ActivityFraction
    public void set_ActivityFraction(float Afrac ) { _ActivityFraction.set(Afrac); }
    
    // TODO: Add Javadocs to set_LimitedQuanMultiplier
    public void set_LimitedQuanMultiplier(float LimMult) { _LimitedQuanMultiplier.set(LimMult); }

    // TODO: Add Javadocs to set_ReportableQuanFranction
    public void set_ReportableQuanFraction(float RQFrac) { _ReportableQuanFraction.set(RQFrac); }

    // TODO: Add Javadocs to set_LimitPercentage
    public void set_LimitPercentage(float LimPer) { _LimitPercentage.set(LimPer); }

    // TODO: Add Javadocs to set_ConcentrationPercentage
    public void set_ConcentrationPercentage(float ConPer) { _ConcentrationPercentage.set(ConPer); }

    // TODO: Add Javadocs to set_LicensePercentage
    public void set_LicensePercentage(float LicPer) { _LicensePercentage.set(LicPer); }

    // TODO: Add Javadocs to set_ALimit
    public void set_ALimit(float ALim) { _ALimit.set(ALim); }

    // TODO: Add Javadocs to set_HRCQLimit
    public void set_HRCQLimit(float HRCQLim) { _HRCQLimit.set(HRCQLim); }

    // TODO: Add Javadocs to set_isFissile
    public void set_isFissile(boolean isFissile) { _isFissile.set(isFissile); }

    // TODO: Add Javadocs to set_isReportableQuan
    public void set_isReportableQuan(boolean isRQ) { _isReportableQuan.set(isRQ); }

    // TODO: Add Javadocs to set_isLong
    public void set_isLong(boolean isLong) { _isLong.set(isLong); }

    // TODO: Add Javadocs to set_isotopeClass
    public void set_isotopeClass(int isoClass) { _isotopeClass.set(isoClass); }

    // TODO: Add Javadocs to set_A1
    public void set_A1(float A1) { _A1.set(A1); }

    // TODO: Add Javadocs to set_A2
    public void set_A2(float A2) { _A2.set(A2); }

    // TODO: Add Javadocs to set_DecayConstant
    public void set_DecayConstant(float decayCon) { _DecayConstant.set(decayCon); }

    // TODO: Add Javadocs to set_ExemptConcentration
    public void set_ExemptConcentration(float ExemptCon) { _ExemptConcentration.set(ExemptCon); }

    // TODO: Add Javadocs to set_ExemptLimit
    public void set_ExemptLimit(float ExemptLim) { _ExemptLimit.set(ExemptLim); }

    // TODO: Add Javadocs to set_HalfLife
    public void set_HalfLife(float halfLife) { _HalfLife.set(halfLife); }

    // TODO: Add Javadocs to set_IALimitedMultiplier
    public void set_IALimitedMultiplier(float IAMult) { _IALimitedMultiplier.set(IAMult); }

    // TODO: Add Javadocs to set_IAPackageLimit
    public void set_IAPackageLimit(float IAPLim) { _IAPackageLimit.set(IAPLim); }

    // TODO: Add Javadocs to set_LicenseLimit
    public void set_LicenseLimit(float LicLim) { _LicenseLimit.set(LicLim); }

    // TODO: Add Javadocs to set_LimitedLimit
    public void set_LimitedLimit(float LimLim) { _LimitedLimit.set(LimLim); }

    // TODO: Add Javadocs to set_ReportableQuan
    public void set_ReportableQuan(float RQ) { _ReportableQuan.set(RQ); }
    
    /*///////////////////////////////////////////////// PROPERTIES ///////////////////////////////////////////////////*/
    
    // TODO: Add Javadocs to _NameProperty
    public SimpleStringProperty _NameProperty() { return _Name; }

    // TODO: Add Javadocs to _A0Property
    public SimpleFloatProperty _A0Property() { return _A0; }

    // TODO: Add Javadocs to _ConcentrationTodayProperty
    public SimpleFloatProperty _ConcentrationTodayProperty() { return _ConcentrationToday; }

    // TODO: Add Javadocs to _DecaysPerMinuteProperty
    public SimpleFloatProperty _DecaysPerMinuteProperty() { return _DecaysPerMinute; }

    // TODO: Add Javadocs to _AcivityConcentrationProperty
    public SimpleFloatProperty _ActivityConcentrationProperty() { return _ActivityConcentration; }

    // TODO: Add Javadocs to _AcivityFractionProperty
    public SimpleFloatProperty _ActivityFractionProperty() { return _ActivityFraction; }

    // TODO: Add Javadocs to _LimitedQuanMultiplierProperty
    public SimpleFloatProperty _LimitedQuanMultiplierProperty() { return _LimitedQuanMultiplier; }

    // TODO: Add Javadocs to _ReportableQuanFractionProperty
    public SimpleFloatProperty _ReportableQuanFractionProperty() { return _ReportableQuanFraction; }

    // TODO: Add Javadocs to _LimitPercentageProperty
    public SimpleFloatProperty _LimitPercentageProperty() { return _LimitPercentage; }

    // TODO: Add Javadocs to _ConcentrationPercentageProperty
    public SimpleFloatProperty _ConcentrationPercentageProperty() { return _ConcentrationPercentage; }

    // TODO: Add Javadocs to _LicensePercentageProperty
    public SimpleFloatProperty _LicensePercentageProperty() { return _LicensePercentage; }

    // TODO: Add Javadocs to _ALimitProperty
    public SimpleFloatProperty _ALimitProperty() { return _ALimit; }

    // TODO: Add Javadocs to _HRCQLimitProperty
    public SimpleFloatProperty _HRCQLimitProperty() { return _HRCQLimit; }

    // TODO: Add Javadocs to _isFissileProperty
    public SimpleBooleanProperty _isFissileProperty() { return _isFissile; }

    // TODO: Add Javadocs to _isReportableQuanProperty
    public SimpleBooleanProperty _isReportableQuanProperty() { return _isReportableQuan; }

    // TODO: Add Javadocs to _isLongProperty
    public SimpleBooleanProperty _isLongProperty() { return _isLong; }

    // TODO: Add Javadocs to _isotopeClassProperty
    public SimpleIntegerProperty _isotopeClassProperty() { return _isotopeClass; }

    // TODO: Add Javadocs to _A1Property
    public SimpleFloatProperty _A1Property() { return _A1; }

    // TODO: Add Javadocs to _A2Property
    public SimpleFloatProperty _A2Property() { return _A2; }

    // TODO: Add Javadocs to _DecayConstantProperty
    public SimpleFloatProperty _DecayConstantProperty() { return _DecayConstant; }

    // TODO: Add Javadocs to _ExemptConcentrationProperty
    public SimpleFloatProperty _ExemptConcentrationProperty() { return _ExemptConcentration; }

    // TODO: Add Javadocs to _ExemptLimitProperty
    public SimpleFloatProperty _ExemptLimitProperty() { return _ExemptLimit; }

    // TODO: Add Javadocs to _HalfLifeProperty
    public SimpleFloatProperty _HalfLifeProperty() { return _HalfLife; }

    // TODO: Add Javadocs to _IALimitedMultiplierProperty
    public SimpleFloatProperty _IALimitedMultiplierProperty() { return _IALimitedMultiplier; }

    // TODO: Add Javadocs to _IAPackageMultiplierProperty
    public SimpleFloatProperty _IAPackageLimitProperty() { return _IAPackageLimit; }

    // TODO: Add Javadocs to _LicenseLimitProperty
    public SimpleFloatProperty _LicenseLimitProperty() { return _LicenseLimit; }

    // TODO: Add Javadocs to _LimitedLimitProperty
    public SimpleFloatProperty _LimitedLimitProperty() { return _LimitedLimit; }

    // TODO: Add Javadocs to _ReportableQuanProperty
    public SimpleFloatProperty _ReportableQuanProperty() { return _ReportableQuan; }
}
