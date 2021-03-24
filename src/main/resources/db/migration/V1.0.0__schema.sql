create table if not exists category
(
    id               bigint   not null
        primary key,
    created_at       datetime not null,
    updated_at       datetime not null,
    categ_name       varchar(255) null,
    created_by       varchar(255) null,
    last_modified_by varchar(255) null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists permission
(
    id               bigint   not null
        primary key,
    description      varchar(255) null,
    name             varchar(255) null
);

create table if not exists meta_data
(
    id          bigint       not null
    primary key,
    image_link  varchar(255) null,
    title       varchar(255) null,
    url         varchar(255) null
    );

create table if not exists role
(
    id               bigint   not null
        primary key,
    created_at       datetime not null,
    updated_at       datetime not null,
    is_delete        bit      not null,
    name             varchar(255) null,
    created_by       varchar(255) null,
    last_modified_by varchar(255) null
);

create table if not exists role_permission
(
    role_id       bigint not null,
    permission_id bigint not null,
    constraint FKa6jx8n8xkesmjmv6jqug6bg68
        foreign key (role_id) references role (id),
    constraint FKf8yllw1ecvwqy3ehyxawqa1qp
        foreign key (permission_id) references permission (id)
);

create table if not exists subscriber
(
    id     bigint not null
        primary key,
    email  varchar(255) null,
    name   varchar(255) null,
    reason varchar(255) null,
    status varchar(255) null
);

create table if not exists user
(
    id                 bigint   not null
        primary key,
    created_at         datetime not null,
    updated_at         datetime not null,
    account_non_locked bit null,
    active             bit      not null,
    failed_attempt     int null,
    github             varchar(255) null,
    email              varchar(255) null,
    is_delete          bit      not null,
    lock_time          datetime null,
    name               varchar(255) null,
    password           varchar(255) null,
    role_id            bigint null,
    created_by         varchar(255) null,
    last_modified_by   varchar(255) null,
    constraint FKn82ha3ccdebhokx3a8fgdqeyy
        foreign key (role_id) references role (id)
);

create table if not exists bookmark
(
    post_id               bigint   not null
        primary key,
    created_at       datetime not null,
    updated_at       datetime not null,
    post_status      varchar(255) null,
    version          bigint null,
    meta_data_id     bigint       null,
    user_id          bigint   not null,
    created_by       varchar(255) null,
    last_modified_by varchar(255) null,
    constraint FK72mt33dhhs48hf9gcqrq4fxte
        foreign key (user_id) references user (id)
);


