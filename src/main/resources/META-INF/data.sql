create table if not exists A_ONE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/A1(TBq).csv');

create table if not exists A_TWO (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/A2(TBq).csv');

create table if not exists DECAY_CONSTANT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/Decay_Constant.csv');

create table if not exists EXEMPT_CONCENTRATION (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/Exempt_concentration(Bq-g).csv');

create table if not exists EXEMPT_LIMIT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/Exempt_limit(Bq).csv');

create table if not exists HALF_LIFE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/Half_Life(days).csv');

create table if not exists ISOTOPES (Name varchar(255) not null, Abbr char(15) not null, version bigint default 0, primary key (Name, Abbr)) as select * from csvread('classpath:csv/ValidIsotopes.csv');

create table if not exists VALID_NUCLIDE (Name varchar(255) not null, Symbol char(2) not null, Mass_Number char(15) not null, version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/AllValidIsotopes.csv');

create table if not exists LIMITS (State char(6) not null, Form char(7) not null, IA_Limited real, IA_Package real, Limited real, version bigint default 0, primary key (State, Form)) as select * from csvread('classpath:csv/Limits.csv');

create table if not exists REPORTABLE_QUANTITY (Abbr char(15) not null, Ci real, TBq real, version bigint default 0, primary key (Abbr)) as select * from csvread('classpath:csv/Reportable_Quantities.csv');

create table if not exists SHIPMENTS (Id bigint auto_increment, Reference_Date date not null, Mass real, Mass_Unit char(6) not null, Nature char(10) not null, State char(6) not null, Form char(7) not null, version bigint default 0, primary key (Id));

create table if not exists SHIPMENT_ISOTOPES (Id bigint auto_increment, Shipment_Id bigint not null, Isotope_Name varchar(255) not null, Isotope_Abbr char(15) not null, version bigint default 0, primary key (Id), foreign key (Shipment_Id) references Shipments(Id), foreign key (Isotope_Name, Isotope_Abbr) references Isotopes(Name, Abbr));