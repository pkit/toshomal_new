create table show (
	id_show integer generated always as identity,
	name varchar(150),
	malid integer,
	status varchar(20),
	type_show char(10),
	eps_number integer,
	image varchar(150)
);

create table eps (
	id_eps integer generated always as identity,
	id_show integer not null,
	num integer,
	sub integer
);

create table file (
	id_file integer generated always as identity,
	time timestamp,
	name varchar (256),
	url varchar (512),
	ext char (3),
	md5 char (8),
	ver integer,
	size varchar(10)
);

create table tag (
	id_tag integer generated always as identity,
	name varchar (50),
	type_tag integer
);

create table tagtype (
	type_tag integer not null primary key,
	name varchar (50)
);

create table tagsperfile (
	id_tag integer not null,
	id_file integer not null
);

create table epsperfile (
	id_eps integer not null,
	id_file integer not null
);

insert into tagtype values (0, 'other');
insert into tagtype values (1, 'fansub group');
insert into tagtype values (2, 'resolution');
insert into tagtype values (3, 'video');
insert into tagtype values (4, 'audio');
insert into tagtype values (5, 'language');
insert into tagtype values (6, 'subtitle');
insert into tagtype values (7, 'bcast type');
insert into tagtype values (8, 'codec params');

create table status (
    time timestamp,
    type integer
);

insert into status values (current_timestamp,0);
insert into status values (current_timestamp,1);
insert into status values (current_timestamp,2);
insert into status values (current_timestamp,3);

create trigger ts_update
after insert on file
referencing new as newfile
for each row
update status set time = newfile.time where status.type = 0 and time < newfile.time;

create table settings (id integer, ival integer, tval timestamp, sval varchar (500));
insert into settings values (0, null, null, null);
insert into settings values (1, null, null, null);
insert into settings values (2, null, null, null);
insert into settings values (3, null, null, null);
insert into settings values (4, null, null, null);
insert into settings values (5, null, null, null);
update settings set sval = 'p' where id = 0;
update settings set sval = 'z' where id = 1;
update settings set ival = 20 where id = 0;