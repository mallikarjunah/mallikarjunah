create table accounts (
account_number bigint not null,
opening_balance bigint,
overdraft bigint,
pin integer,
primary key (account_number));

create table atm_machine(
type varchar(255) not null,
count bigint not null,
value bigint not null,
primary key (type));
