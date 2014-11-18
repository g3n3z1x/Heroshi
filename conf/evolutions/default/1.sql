# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table evaluacion (
  id                        bigint not null,
  metricas_gcs_id           bigint,
  usuario_id                bigint,
  tipo                      varchar(255),
  respuestas                varchar(255),
  due_date                  timestamp,
  constraint pk_evaluacion primary key (id))
;

create table metrica_gcs (
  id                        bigint not null,
  evaluacion_id             bigint,
  nivel_documentacion       float,
  nivel_disponibilidad_repo float,
  accesibilidad             float,
  nivel_documentacion_mejor float,
  nivel_documentacion_todos float,
  periodicidad              float,
  responsable               float,
  total                     float,
  scm01                     integer,
  scm02                     integer,
  scm03                     integer,
  scm04                     integer,
  scm05                     integer,
  scm06                     integer,
  scm07                     integer,
  due_date                  timestamp,
  constraint pk_metrica_gcs primary key (id))
;

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

create sequence evaluacion_seq;

create sequence metrica_gcs_seq;

create sequence usuario_seq;

alter table evaluacion add constraint fk_evaluacion_metricas_gcs_1 foreign key (metricas_gcs_id) references metrica_gcs (id);
create index ix_evaluacion_metricas_gcs_1 on evaluacion (metricas_gcs_id);
alter table evaluacion add constraint fk_evaluacion_usuario_2 foreign key (usuario_id) references usuario (id);
create index ix_evaluacion_usuario_2 on evaluacion (usuario_id);
alter table metrica_gcs add constraint fk_metrica_gcs_evaluacion_3 foreign key (evaluacion_id) references evaluacion (id);
create index ix_metrica_gcs_evaluacion_3 on metrica_gcs (evaluacion_id);



# --- !Downs

drop table if exists evaluacion cascade;

drop table if exists metrica_gcs cascade;

drop table if exists usuario cascade;

drop sequence if exists evaluacion_seq;

drop sequence if exists metrica_gcs_seq;

drop sequence if exists usuario_seq;

