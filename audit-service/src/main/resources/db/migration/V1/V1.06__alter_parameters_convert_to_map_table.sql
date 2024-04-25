alter table method_parameter
    drop constraint pk_method_parameter;

update method_parameter
set name = 'arg#' || position
where name is null;

alter table method_parameter
    alter column name set not null,
    drop constraint ck_method_parameter__position,
    drop constraint uq_method_parameter__position_per_method,
    drop constraint uq_method_parameter__name_per_method,
    add constraint pk_method_parameter primary key (log_entry_id, name),
    drop column position,
    drop column id;
