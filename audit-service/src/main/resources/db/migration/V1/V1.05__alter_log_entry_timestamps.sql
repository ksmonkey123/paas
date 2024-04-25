alter table log_entry
    rename column timestamp to timestamp_start;

alter table log_entry
    add column timestamp_end timestamp;

update log_entry
set timestamp_end = timestamp_start;

alter table log_entry
    alter column timestamp_end set not null;

