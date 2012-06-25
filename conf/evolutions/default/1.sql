# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table TestCaseTable (
  id                        varchar(255) not null,
  name                      varchar(255),
  bitmap$init$0             integer,
  constraint pk_TestCaseTable primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table TestCaseTable;

SET FOREIGN_KEY_CHECKS=1;

