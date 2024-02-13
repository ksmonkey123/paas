Auth Service Considerations
===========================

## Introduction

The auth service is designed as a central authentication and authorisation hub. Both real users and background tasks
from business services receive their authorisation configuration (grants) from this service.

## Account Types

There are multiple account types. The auth service does not generally treat them differently.

### User Accounts

User accounts represent _real_ users.

### Service Account

Service accounts are used by services for their authentication own background task authorisation.

## Authorities

Authorities represent the smallest granularity of permission management possible. A single authority may manage as
little as permissions for a single use-case or webservice endpoint.

Authorities are not directly assigned to _accounts_, instead they are grouped into _roles_.

### Special Authorities

There are a few special authorities that are not manually assignable to any roles but are managed dynamically:

* `user`        - automatically added to any _user account_
* `service`     - automatically added to any _service account_
* `impersonate` - automatically added to any account with the `IMPERSONATION` role

## Roles

A role is basically just a group of authorities. Roles can be assigned to _accounts_. Whenever a role is assigned to an
account, that account receives all authorities listed in the profile. An account can have an unbounded number of roles.

### Special Roles

There are a few special roles and authorities:

* `ADMIN`         - possesses all authorities except for the special authority `impersonate`. Only assignable to _user
  accounts_. Not stored as a normal _role mapping_, but in the field `user_configuration.admin` on the account.
* `IMPERSONATION` - possesses the authority `impersonate`. Only assignable to _service accounts_. Not stored as a normal
  _role mapping_, but in the field `service_configuration.impersonation` on the account.

