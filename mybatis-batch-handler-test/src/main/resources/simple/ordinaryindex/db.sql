create table simple_ordinary_index
(
    id    int          not null primary key auto_increment,
    code1 varchar(100) not null default '',
    code2 varchar(100) not null default '',
    name1 varchar(100) not null default '',
    name2 varchar(100) not null default ''
);

alter table simple_ordinary_index
    add index idx_code (code1, code2);