-- Creating table
create table if not exists A_ONE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists A_TWO (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists DECAY_CONSTANT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists EXEMPT_CONCENTRATION (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists EXEMPT_LIMIT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists HALF_LIFE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists ISOTOPES (Name varchar(255) not null, Abbr char(15) not null, version bigint default 0, primary key (Name, Abbr));

create table if not exists LIMITS (State char(6) not null, Form char(7) not null, IA_Limited real, IA_Package real, Limited real, version bigint default 0, primary key (State, Form));

create table if not exists REPORTABLE_QUANTITY (Abbr char(15) not null, Ci real, TBq real, version bigint default 0, primary key (Abbr));

create table if not exists SHIPMENTS (Id bigint auto_increment, Reference_Date date not null, Mass real, Mass_Unit char(6) not null, Nature char(10) not null, State char(6) not null, Form char(7) not null, version bigint default 0, primary key (Id));

create table if not exists SHIPMENT_ISOTOPES (Id bigint auto_increment, Shipment_Id bigint not null, Isotope_Name varchar(255) not null, Isotope_Abbr char(15) not null, version bigint default 0, primary key (Id), foreign key (Shipment_Id) references Shipment(Id), foreign key (Isotope_Name, Isotope_Abbr) references Isotopes(Name, Abbr));

-- Adding Data
insert into LIMITS(State, Form, IA_Limited, IA_Package, Limited, version) values('solid', 'normal', 1.5, 2.0, 3.5, -1);

insert into ISOTOPES(Name, Abbr, version) values('Abbreviation', 'Abbr', -1);
insert into A_ONE(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into A_TWO(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into DECAY_CONSTANT(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into EXEMPT_CONCENTRATION(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into EXEMPT_LIMIT(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into HALF_LIFE(Abbr, Val, version) values('Abbr', 1.0, -1);
insert into REPORTABLE_QUANTITY(Abbr, Ci, TBq, version) values('Abbr', 1, 0.037, -1);

insert into ISOTOPES(Name, Abbr, version) values('Annual', 'Anny', -1);
insert into A_ONE(Abbr, Val, version) values('Anny', 2.0, -1);
insert into A_TWO(Abbr, Val, version) values('Anny', 2.0, -1);
insert into DECAY_CONSTANT(Abbr, Val, version) values('Anny', 2.0, -1);
insert into EXEMPT_CONCENTRATION(Abbr, Val, version) values('Anny', 2.0, -1);
insert into EXEMPT_LIMIT(Abbr, Val, version) values('Anny', 2.0, -1);
insert into HALF_LIFE(Abbr, Val, version) values('Anny', 2.0, -1);
insert into REPORTABLE_QUANTITY(Abbr, Ci, TBq, version) values('Anny', 2, 0.074, -1);
