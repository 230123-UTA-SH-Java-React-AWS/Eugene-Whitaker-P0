alter sequence employee_ID_seq restart with 1;

drop table ticket;
drop table employee;
drop table manager;
drop table managerIDs;

create table employee (
	ID serial primary key,
	email varchar(255) not null unique,
	pass varchar(128) not null
);
insert into employee (email, pass) values ('test@test.com', 'test');

create table managerIDs (
	ID integer primary key
);
insert into managerids (ID) values (1),(2),(3);

create table manager ( 
	ID integer primary key references managerIDs(ID) not null unique,
	email varchar(255) not null unique,
	pass varchar(128) not null
);
insert into manager (ID, email, pass) values (1, 'admin@test.com', 'test');

create table ticket (
	ID serial unique,
	employeeID integer references employee(ID) not null,
	amount decimal not null,
	description varchar(255) not null,
	pending boolean not null,
	status varchar(8),
	primary key(ID, employeeID)
);
insert into ticket (employeeID, amount, description, pending, status) values (1, 100.00, 'test', true, 'pending');