create table simple
(
    id   int          not null primary key auto_increment,
    code varchar(200) not null default '',
    name varchar(200) not null default ''
);

update simple t,
        (select 1 as id, 1 as code) v
set t.code = v.code
where t.id = v.id