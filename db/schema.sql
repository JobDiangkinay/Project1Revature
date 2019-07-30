drop table if exists Reimbursements;
drop table if exists Persons;
drop table if exists AccountCredentials;
create table AccountCredentials (
	credID serial primary key,
	UserName varchar unique not null,
	Password bytea not null,
	SaltPass bytea not null,
	UserType text not null
);
create table Persons (
	pID serial primary key,
	FirstName text not null,
	LastName text not null,
	PhoneNumber text,
	EmailAdd text,
	UserName varchar not null
);
create table Reimbursements(
	pID serial primary key,
	UserName varchar not null,
	Amount numeric(10,2),
	ReimbursementType text not null,
	ReimbursementStatus varchar not null,
	ReimbursementDesc text,
	ReimBase64 text,
	ReimAdmin text,
	CreationDate varchar
);

insert into Persons(FirstName, LastName, PhoneNumber, UserName, EmailAdd) values ('Juan', 'Diangkinay', '9494909895', 'empjob', 'jobdiangkinay@gmail.com');
insert into Reimbursements(UserName, Amount, ReimbursementType, ReimbursementStatus, ReimbursementDesc,CreationDate, ReimAdmin) values ('empjob', 250, 'Relocation', 'Approved','Gas and Hotel','11/05/2018','adminjob');
insert into Reimbursements(UserName, Amount, ReimbursementType, ReimbursementStatus, ReimbursementDesc,CreationDate) values ('empjob', 200, 'Certification', 'Submitted','Java OCA','05/14/2019');


insert into Persons(FirstName, LastName, PhoneNumber, UserName, EmailAdd) values ('Kenny', 'Bui', '2252525494', 'empkenny', 'kennybui@gmail.com');
insert into Reimbursements(UserName, Amount, ReimbursementType, ReimbursementStatus, ReimbursementDesc,CreationDate) values ('empkenny', 350, 'Relocation', 'Submitted','Gas and Food','06/24/2019');
insert into Reimbursements(UserName, Amount, ReimbursementType, ReimbursementStatus, ReimbursementDesc,CreationDate) values ('empkenny', 230, 'Certification', 'Submitted','Java OCA','06/18/2019');


insert into Persons(FirstName, LastName, PhoneNumber, UserName, EmailAdd) values ('Job', 'Diangkinay', '9494909895', 'adminjob', 'admin@gmail.com');

/*insert into AccountCredentials(UserName, Password, UserType) values ('empjob', 'passjob', 'EMPLOYEE');*/
/*insert into AccountCredentials(UserName, Password, UserType) values ('empkenny', 'passjob', 'EMPLOYEE');*/
/*insert into AccountCredentials(UserName, Password, UserType) values ('adminjob', 'passjob', 'ADMIN');*/