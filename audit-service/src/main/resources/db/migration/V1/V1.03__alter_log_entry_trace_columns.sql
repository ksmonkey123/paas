-- rename root id
alter table log_entry
    rename trace_root_id to root_trace_id;

-- new column for the parent. not a FK because it is possible that audit entries arrive out of order!
alter table log_entry
    add
        parent_trace_id text;

update log_entry
set parent_trace_id = regexp_replace(trace_id, '^(.*)\$[^$]+$', '\1')
where trace_id <> log_entry.root_trace_id;

create index idx_log_entry__parent_trace_id on log_entry (parent_trace_id);