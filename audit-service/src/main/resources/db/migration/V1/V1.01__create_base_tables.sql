create table log_entry
(
    id            bigint      not null generated always as identity,
    timestamp     timestamp   not null,
    trace_root_id text        not null,
    trace_id      text        not null,
    service_name  text        not null,
    account       varchar(30) not null,
    -- rest info
    verb          varchar(7),
    path          text,
    -- method info
    component     text        not null,
    method        text        not null,

    -- constraints
    constraint pk_log_entry primary key (id),
    constraint ck_log_entry__rest check ((verb is null) = (path is null)),
    constraint uq_log_entry__trace_id unique (trace_id)
);

create index idx_log_entry__trace_id on log_entry (trace_id);

create table method_parameter
(
    id           bigint  not null generated always as identity,
    log_entry_id bigint  not null,
    position     integer not null,
    name         text,
    value        text,

    -- constraints
    constraint pk_method_parameter primary key (id),
    constraint ck_method_parameter__position check ( position >= 0 ),
    constraint fk_method_parameter__log_entry_id foreign key (log_entry_id) references log_entry (id),
    constraint uq_method_parameter__position_per_method unique (log_entry_id, position),
    constraint uq_method_parameter__name_per_method unique (log_entry_id, name)
);

create index idx_method_parameter__log_entry_id on method_parameter (log_entry_id);