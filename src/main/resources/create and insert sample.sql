use cs4961stu01;
drop table if exists users;
drop table if exists updates;
drop table if exists tickets;
drop table if exists units;
drop table if exists assignments;

create table if not exists users (
id int auto_increment primary key not null,
firstname varchar(20) not null, 
lastname varchar(20) not null,
username varchar(20) unique not null,
pass varchar(255) default "",
phone varchar(15) default "",
email varchar(255) default "",
position int default 2,
unit_id int default 0
);

create table if not exists updates (
id int auto_increment primary key not null,
ticketId int not null,
modifier varchar(20) not null,
updateDetails TEXT,
modifiedDate DATETIME not null
);

create table if not exists assignments (
ticketId int not null,
technicianUser varchar(20) not null
);

create table if not exists tickets (
id int auto_increment primary key not null,
username varchar(20) not null,
userFirstName varchar(20) not null, 
userLastName varchar(20) not null,
phone varchar(15) not null,
email varchar(50) not null,
Progress int default 0,
unitId int not null,
details TEXT not null,
startDate DATETIME,
endDate DATETIME,
lastUpdated DATETIME,
ticketLocation varchar(50) not null
);

create table if not exists units(
id int auto_increment primary key not null,
unitName varchar(255) not null,
phone varchar(15) default "",
location varchar(255) default "",
description TEXT
);

insert into users (id, firstname, lastname, username, pass, position) values(1, "John", "Smith", "jsmith", "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589", 1);
insert into users (firstname, lastname, username, pass, position) values("Bob", "Lee", "blee", "7140cce4f5d226132b03b2942f5d0478e1823dc4f69d1eb30b03f1e195b5bb9c", 2);
insert into users (firstname, lastname, username, pass, position) values("Joseph", "Joestar", "jojo", "5ad0000a66aac644685638066d6c1beabd796dc33a5a827ce7121a94aa78552d", 0); 
insert into users (firstname, lastname, username, pass, position) values("Brandon", "Ung", "bung@calstatela.edu", "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", 3);

insert into tickets (id, username, userFirstName, userLastName, phone, email, Progress, unitId, details, startDate, lastUpdated, ticketLocation) 
values (1, "bung@calstatela.edu", "Brandon", "Ung", "626-202-6423", "bung@calstatela.edu", 0, 1, "The projector is broken in room A220.", "2016-10-13 00:00:01", "2016-10-13 00:00:01", "Engineering and Technology: A220");
insert into tickets (username, userFirstName, userLastName, phone, email, Progress, unitId, details, startDate, lastUpdated, ticketLocation) 
values ("bung@calstatela.edu", "Brandon", "Ung", "626-202-6423", "bung@calstatela.edu", 1, 1, "A computer broke and will not turn on.", "2016-10-13 03:14:07", "2016-10-13 00:00:01", "Engineering and Technology: B9");

insert into assignments (ticketId, technicianUser) values(2, "blee");

insert into units (id, unitName, phone, location, description) values(1, "TechIT Unit Test", "(555) 111-5555", "ET's Basement", "The best QA team you can get! (Not Really)");