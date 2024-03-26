create table "team" (
    id                  serial primary key,
    name                varchar(128),
    hex_color            varchar(10)
);

insert into "team" (id,name,hex_color) values (10000,'Team 1', '#FF0000');
insert into "team" (id,name,hex_color) values (200000,'Team 2', '#FF0000');
insert into "team" (id,name,hex_color) values (300000,'Team 3', '#FF0000');
