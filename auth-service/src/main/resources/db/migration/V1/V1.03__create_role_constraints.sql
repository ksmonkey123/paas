alter table account_role
    add constraint fk_account_role__account_id foreign key (account_id) references account (id),
    add constraint fk_account_role__role_id foreign key (role_id) references role (id);

create index idx_account_role__account_id on account_role (account_id);
create index idx_account_role__role_id on account_role (role_id);