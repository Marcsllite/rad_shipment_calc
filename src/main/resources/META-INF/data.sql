create table if not exists NUCLIDE (Atomic_Number tinyint not null, Name varchar(255) not null, Symbol char(2) not null, Mass_Number char(15) not null, version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/AllNuclides.csv');

create table if not exists SHIPMENT (Id bigint auto_increment, Reference_Date date not null, Mass decfloat(2) not null default '-Infinity', Mass_Unit char(6) not null, Nature char(10) not null, State char(6) not null, Form char(7) not null, version bigint default 0, primary key (Id));

create table if not exists SHIPMENT_NUCLIDE (Id bigint auto_increment, Shipment_Id bigint not null, Nuclide_Symbol char(2) not null, Mass_Number char(15) not null, version bigint default 0, primary key (Id), foreign key (Shipment_Id) references Shipment(Id), foreign key (Nuclide_Symbol, Mass_Number) references Nuclide(Symbol, Mass_Number));

create table if not exists A_ONE (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/A1(TBq).csv');

create table if not exists A_TWO (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/A2(TBq).csv');

create table if not exists DECAY_CONSTANT (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/Decay_Constant.csv');

create table if not exists EXEMPT_CONCENTRATION (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/Exempt_concentration(Bq-g).csv');

create table if not exists EXEMPT_LIMIT (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/Exempt_limit(Bq).csv');

create table if not exists HALF_LIFE (Symbol char(2) not null, Mass_Number char(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/Half_Life(days).csv');

create table if not exists LIMITS (State char(6) not null, Form char(7) not null, IA_Limited decfloat(2) not null default '-Infinity', IA_Package decfloat(2) not null default '-Infinity', Limited decfloat(2) not null default '-Infinity', version bigint default 0, primary key (State, Form)) as select * from csvread('classpath:csv/Limits.csv');

create table if not exists REPORTABLE_QUANTITY (Symbol char(2) not null, Mass_Number char(15) not null, Ci decfloat(2) not null default '-Infinity', TBq decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number)) as select * from csvread('classpath:csv/Reportable_Quantities.csv');