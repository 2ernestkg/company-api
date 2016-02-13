create table beneficiar (name varchar(150) not null, primary key (name));
create table company (id integer not null, address varchar(150) not null, city varchar(50) not null, country varchar(50) not null, email varchar(50), phone_number varchar(10), name varchar(50) not null, primary key (id));
create table company_owners (company_id integer not null, owner_name varchar(150) not null);
alter table company add constraint UK_niu8sfil2gxywcru9ah3r4ec5 unique (name);
create sequence hibernate_sequence start with 1 increment by 1;
alter table company_owners add constraint FKnkwhe7s8vn6d0osrd9h4qt5lw foreign key (owner_name) references beneficiar;
alter table company_owners add constraint FKfkyt7fjyc2rx2jdcnsgh9ay57 foreign key (company_id) references company;