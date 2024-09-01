-- Creating table
create table if not exists NUCLIDE (Atomic_Number tinyint not null, Name varchar(255) not null, Symbol char(2) not null, Mass_Number varchar(15) not null, version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists SHIPMENT (Id bigint auto_increment, Reference_Date date not null, Mass decfloat(2) not null default '-Infinity', Mass_Prefix varchar(10) not null default '', Mass_Unit varchar(10) not null default 'grams (g)', Nature varchar(10) not null default 'regular', State varchar(6) not null default 'solid', Form varchar(7) not null default 'normal', version bigint default 0, primary key (Id));

create table if not exists SHIPMENT_NUCLIDE (Id bigint auto_increment, Shipment_Id bigint not null, Nuclide_Symbol char(2) not null, Mass_Number varchar(15) not null, version bigint default 0, primary key (Id), foreign key (Shipment_Id) references Shipment(Id), foreign key (Nuclide_Symbol, Mass_Number) references Nuclide(Symbol, Mass_Number));

create table if not exists A_ONE (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists A_TWO (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists DECAY_CONSTANT (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists EXEMPT_CONCENTRATION (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists EXEMPT_LIMIT (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists HALF_LIFE (Symbol char(2) not null, Mass_Number varchar(15) not null, Val decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

create table if not exists LIMITS (State varchar(6) not null, Form varchar(7) not null, IA_Limited decfloat(2) not null default '-Infinity', IA_Package decfloat(2) not null default '-Infinity', Limited decfloat(2) not null default '-Infinity', version bigint default 0, primary key (State, Form));

create table if not exists REPORTABLE_QUANTITY (Symbol char(2) not null, Mass_Number varchar(15) not null, Ci decfloat(2) not null default '-Infinity', TBq decfloat(2) not null default '-Infinity', version bigint default 0, primary key (Symbol, Mass_Number));

-- Adding Data
insert into LIMITS(State, Form, IA_Limited, IA_Package, Limited, version) values('solid', 'normal', 1.5, 2.0, 3.5, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(1, 'Abbreviation', 'Ab', '1', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('Ab', '1', 1.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('Ab', '1', 1, 0.037, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(2, 'Annual', 'An', '1', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('An', '1', 2.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('An', '1', 1, 0.037, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(3, 'Bofuri', 'Bf', '1', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('Bf', '1', 3.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('Bf', '1', 3, 0.074, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(3, 'Bofuri', 'Bf', '1(short)', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('Bf', '1(short)', 3.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('Bf', '1(short)', 3, 0.074, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(3, 'Bofuri', 'Bf', '1(long)', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('Bf', '1(long)', 3.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('Bf', '1(long)', 3, 0.074, -1);

insert into NUCLIDE(Atomic_Number, Name, Symbol, Mass_Number, version) values(4, 'Best', 'Bs', '1fast', -1);
insert into A_ONE(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into A_TWO(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into DECAY_CONSTANT(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into EXEMPT_CONCENTRATION(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into EXEMPT_LIMIT(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into HALF_LIFE(Symbol, Mass_Number, Val, version) values('Bs', '1fast', 4.0, -1);
insert into REPORTABLE_QUANTITY(Symbol, Mass_Number, Ci, TBq, version) values('Bs', '1fast', 4, 0.074, -1);
