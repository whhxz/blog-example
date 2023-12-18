create table task
(
    id          bigint       not null auto_increment primary key,
    type        varchar(50)  not null default '' comment '任务类型',
    `key`       varchar(50)  not null default '' comment '关键key',
    content     varchar(255) not null default '' comment '内容',
    state       varchar(20)  not null default '' comment '状态 CREATE、ING、DONE、FAIL',
    create_id   varchar(50)  not null default '',
    create_time datetime     not null default current_timestamp,
    update_id   varchar(50)  not null default '',
    update_time datetime     not null default current_timestamp
) comment '任务处理';
alter table task
    add unique index uk_type_key (type, `key`);