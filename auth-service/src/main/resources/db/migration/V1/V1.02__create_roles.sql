create table role
(
    id          bigint  not null generated always as identity,
    description varchar(50),
    enabled     boolean not null,
    constraint pk_role primary key (id)
);

create table account_role
(
    account_id bigint not null,
    role_id    bigint not null,
    constraint pk_account_role primary key (account_id, role_id)
);