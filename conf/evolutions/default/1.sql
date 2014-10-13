# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table usuario (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  nombre                    varchar(255),
  apellido                  varchar(255),
  dni                       varchar(255),
  empresa                   varchar(255),
  ruc                       varchar(255),
  tipo_empresa              varchar(255),
  email                     varchar(255),
  estado                    varchar(255),
  due_date                  timestamp,
  constraint uq_usuario_username unique (username),
  constraint pk_usuario primary key (id))
;

create sequence usuario_seq;




# --- !Downs

drop table if exists usuario cascade;

drop sequence if exists usuario_seq;

