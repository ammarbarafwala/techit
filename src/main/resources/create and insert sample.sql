use cs4961stu01;
drop table if exists users;
drop table if exists updates;
drop table if exists tickets;

create table if not exists users (id int auto_increment primary key not null,
firstname varchar(20) not null, 
lastname varchar(20) not null,
username varchar(20) not null,
pass varchar(255),
CIN int,
phone varchar(15),
email varchar(255),
position int not null default 2,
is_supervisor bool default 0,
supervisor_id int,
unit_id int);

create table if not exists updates (id int auto_increment primary key not null,
ticketId int,
modifier varchar(20),
updateDetails varchar(255),
modifiedDate DATE
);

create table if not exists tickets (id int auto_increment primary key not null,
usernameRequestor varchar(20) not null, 
technicianId varchar(20) not null,
phone varchar(12) not null,
mail varchar(50) not null,
Progress int,
details varchar(255),
startDate DATE,
endDate DATE,
ticketLocation varchar(50),
completeDetails varchar(255)
);

insert into users (id, firstname, lastname, username, pass, CIN, position) values(1, "John", "Smith", "jsmith", "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589", 123456789, 1);
insert into users (firstname, lastname, username, pass, CIN, position) values("Bob", "Lee", "blee", "7140cce4f5d226132b03b2942f5d0478e1823dc4f69d1eb30b03f1e195b5bb9c", 987654321, 2);
insert into users (firstname, lastname, username, pass, CIN, position) values("Joseph", "Joestar", "jojo", "5ad0000a66aac644685638066d6c1beabd796dc33a5a827ce7121a94aa78552d", 666, 0); 
insert into users (firstname, lastname, username, pass, CIN, position) values("Brandon", "Ung", "bung@calstatela.edu", "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", 302062168, 3);

insert into tickets (usernameRequestor, technicianId, phone, mail, Progress, details, startDate, ticketLocation) 
values ("Brandon", 2, "626-202-6423", "bung@calstatela.edu", 0, "The projector is broken in room A220.", "2016-10-13", "Engineering and Technology: A220");
insert into tickets (usernameRequestor, technicianId, phone, mail, Progress, details, startDate, ticketLocation) 
values ("Brandon", 2, "626-202-6423", "bung@calstatela.edu", 1, "A computer broke and will not turn on.", "2016-10-13", "Engineering and Technology: B9");