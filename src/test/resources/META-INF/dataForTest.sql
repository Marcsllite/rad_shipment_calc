-- Creating table
create table if not exists A_ONE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists  A_TWO (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists DECAY_CONSTANT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists EXEMPT_CONCENTRATION (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists EXEMPT_LIMIT (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists HALF_LIFE (Abbr char(15) not null, Val real, version bigint default 0, primary key (Abbr));

create table if not exists ISOTOPES (Name varchar(255) not null, Abbr char(15) not null, version bigint default 0, primary key (Name, Abbr));

create table if not exists LIMITS (State char(6) not null, Form char(7) not null, IA_Limited real, IA_Package real, Limited real, version bigint default 0, primary key (State, Form));

create table if not exists REPORTABLE_QUANTITY (Abbr char(15) not null, Ci real, TBq real, version bigint default 0, primary key (Abbr));

-- Adding Data
merge into A_ONE(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into A_TWO(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into DECAY_CONSTANT(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into EXEMPT_CONCENTRATION(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into EXEMPT_LIMIT(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into HALF_LIFE(Abbr, Val, version) values('Abbr', 1.0, -1);

merge into ISOTOPES(Name, Abbr, version) values('Abbreviation', 'Abbr', -1);

merge into LIMITS(State, Form, IA_Limited, IA_Package, Limited, version) values('Solid', 'Normal', 1.5, 2.0, 3.5, -1);

merge into REPORTABLE_QUANTITY(Abbr, Ci, TBq, version) values('Abbr', 1, 0.037, -1);
