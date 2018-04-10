create table meal (
  id bigint not null,
  calories integer check (calories>=0 AND calories<=10000),
  date date,
  description varchar(200),
  time time,
  user_id bigint not null,
  primary key (id)
);

create table user (
  id bigint not null,
  calories integer check (calories>=0 AND calories<=50000),
  name varchar(100),
  password_hash varchar(60) not null,
  role varchar(255),
  username varchar(50) not null,
  primary key (id)
);

alter table user
  add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table meal
  add constraint FKckykxviti3jwd6vkcs55btrxa
foreign key (user_id)
references user;
