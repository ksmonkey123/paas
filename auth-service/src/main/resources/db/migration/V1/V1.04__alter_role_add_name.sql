truncate role cascade;

alter table role
    add name varchar(20) not null,
    add constraint uq_role__name unique (name);