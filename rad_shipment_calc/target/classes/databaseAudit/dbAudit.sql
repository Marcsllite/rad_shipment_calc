-- Change this to the directory where you would like the csv files to be saved
set @macLoc = '/Users/marcsllite/Desktop/Rad_Shipment_Calculator/src/main/resources/databaseAudit';
set @windowsLoc = 'D:\Marcs\Documents\GitHub\Rad_Shipment_Calculator\src\main\resources\databaseAudit';
set @saveDir = @windowsLoc;

-- Things to be added to Isotopes table
call csvwrite(concat(@saveDir, '/InAllOtherTables_NotInIsotopes.csv'),
    'select abbr from (select abbr from a1 where abbr not in (select abbr from isotopes))
        where abbr in (select abbr from a2 where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from DECAY_CONSTANT where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from EXEMPT_CONCENTRATION where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from EXEMPT_LIMIT where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from HALFLIFE where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from LICENSING_LIMIT where abbr not in (select abbr from isotopes)) and
        abbr in (select abbr from REPORTABLE_QUANTITY where abbr not in (select abbr from isotopes))');

-- Things in A1 table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InA1_NotInIsotopes.csv'),
              'select abbr from a1 where abbr not in (select abbr from isotopes)');

-- Things in A2 table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InA2_NotInIsotopes.csv'),
              'select abbr from a2 where abbr not in (select abbr from isotopes)');

-- Things in Decay_Constant table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InDecayConstant_NotInIsotopes.csv'),
              'select abbr from DECAY_CONSTANT where abbr not in (select abbr from isotopes)');

-- Things in Exempt_Concentration table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InExemptConcentration_NotInIsotopes.csv'),
              'select abbr from EXEMPT_CONCENTRATION where abbr not in (select abbr from isotopes)');

-- Things in Exempt_Limit table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InExemptLimit_NotInIsotopes.csv'),
              'select abbr from EXEMPT_LIMIT where abbr not in (select abbr from isotopes)');

-- Things in HalfLife table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InHalfLife_NotInIsotopes.csv'),
              'select abbr from HALFLIFE where abbr not in (select abbr from isotopes)');

-- Things in Licensing_Limit table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InLicensingLimit_NotInIsotopes.csv'),
              'select abbr from LICENSING_LIMIT where abbr not in (select abbr from isotopes)');

-- Things in Reportable_Quantity table but not in Isotopes table
call csvwrite(concat(@saveDir, '/InReportableQuantity(TBq)_NotInIsotopes.csv'),
              'select abbr from REPORTABLE_QUANTITY where abbr not in (select abbr from isotopes)');

-- #################################################################################### --
-- Things in Isotopes table but not in the A1 table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInA1.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from A1)');

-- Things in Isotopes table but not in the A2 table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInA2.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from A2)');

-- Things in Isotopes table but not in the Decay_Constant table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInDecayConstant.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from DECAY_CONSTANT)');

-- Things in Isotopes table but not in the Exempt_Concentration table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInExemptConcentration.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from EXEMPT_CONCENTRATION)');

-- Things in Isotopes table but not in the Exempt_Limit table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInExemptLimit.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from EXEMPT_LIMIT)');

-- Things in Isotopes table but not in the HalfLife table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInHalfLife.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from HALFLIFE)');

-- Things in Isotopes table but not in the Licensing_Limit table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInLicensingLimit.csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from LICENSING_LIMIT)');

-- Things in Isotopes table but not in the Reportable_Quantity table
call csvwrite(concat(@saveDir, '/InIsotopes_NotInReportableQuantity(TBq).csv'),
              'select abbr from ISOTOPES where abbr not in (select abbr from REPORTABLE_QUANTITY)');