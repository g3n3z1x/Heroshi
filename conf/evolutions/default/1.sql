# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aux_last (
  id                        bigint not null,
  usuario_id                bigint,
  gcs_le                    bigint,
  gns_le                    bigint,
  gin_le                    bigint,
  due_date                  timestamp,
  constraint pk_aux_last primary key (id))
;

create table evaluacion (
  id                        bigint not null,
  metricas_gcs_id           bigint,
  metricas_gns_id           bigint,
  metricas_gin_id           bigint,
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

create table metrica_gin (
  id                        bigint not null,
  evaluacion_id             bigint,
  procedimientos            float,
  herramienta_gin           float,
  clasificacion             float,
  detalle_registro          float,
  reportes                  float,
  periodicidad              float,
  responsable               float,
  total                     float,
  im01                      integer,
  im02                      integer,
  im03                      integer,
  im04                      integer,
  im05                      integer,
  im06                      integer,
  im07                      integer,
  due_date                  timestamp,
  constraint pk_metrica_gin primary key (id))
;

create table metrica_gns (
  id                        bigint not null,
  evaluacion_id             bigint,
  nivel_documentacion       float,
  nivel_disponibilidad_repo float,
  nivel_documentacion_mejor float,
  nivel_documentacion_todos float,
  periodicidad              float,
  condiciones               float,
  evaluacion_reportes       float,
  requisitos_nivel_servicio float,
  responsable_nivel_servicio float,
  total                     float,
  slm01                     integer,
  slm02                     integer,
  slm03                     integer,
  slm04                     integer,
  slm05                     integer,
  slm06                     integer,
  slm07                     integer,
  slm08                     integer,
  slm09                     integer,
  due_date                  timestamp,
  constraint pk_metrica_gns primary key (id))
;

create table usuario (
  id                        bigint not null,
  aux_last_id               bigint,
  username                  varchar(255),
  password                  varchar(255),
  nombre                    varchar(255),
  apellido                  varchar(255),
  dni                       varchar(255),
  empresa                   varchar(255),
  ruc                       varchar(255),
  tipo_empresa              varchar(255),
  tipo_servicio_1           varchar(255),
  tipo_servicio_2           varchar(255),
  email                     varchar(255),
  estado                    varchar(255),
  due_date                  timestamp,
  constraint uq_usuario_username unique (username),
  constraint pk_usuario primary key (id))
;

create sequence aux_last_seq;

create sequence evaluacion_seq;

create sequence metrica_gcs_seq;

create sequence metrica_gin_seq;

create sequence metrica_gns_seq;

create sequence usuario_seq;

alter table aux_last add constraint fk_aux_last_usuario_1 foreign key (usuario_id) references usuario (id);
create index ix_aux_last_usuario_1 on aux_last (usuario_id);
alter table evaluacion add constraint fk_evaluacion_metricas_gcs_2 foreign key (metricas_gcs_id) references metrica_gcs (id);
create index ix_evaluacion_metricas_gcs_2 on evaluacion (metricas_gcs_id);
alter table evaluacion add constraint fk_evaluacion_metricas_gns_3 foreign key (metricas_gns_id) references metrica_gns (id);
create index ix_evaluacion_metricas_gns_3 on evaluacion (metricas_gns_id);
alter table evaluacion add constraint fk_evaluacion_metricas_gin_4 foreign key (metricas_gin_id) references metrica_gin (id);
create index ix_evaluacion_metricas_gin_4 on evaluacion (metricas_gin_id);
alter table evaluacion add constraint fk_evaluacion_usuario_5 foreign key (usuario_id) references usuario (id);
create index ix_evaluacion_usuario_5 on evaluacion (usuario_id);
alter table metrica_gcs add constraint fk_metrica_gcs_evaluacion_6 foreign key (evaluacion_id) references evaluacion (id);
create index ix_metrica_gcs_evaluacion_6 on metrica_gcs (evaluacion_id);
alter table metrica_gin add constraint fk_metrica_gin_evaluacion_7 foreign key (evaluacion_id) references evaluacion (id);
create index ix_metrica_gin_evaluacion_7 on metrica_gin (evaluacion_id);
alter table metrica_gns add constraint fk_metrica_gns_evaluacion_8 foreign key (evaluacion_id) references evaluacion (id);
create index ix_metrica_gns_evaluacion_8 on metrica_gns (evaluacion_id);
alter table usuario add constraint fk_usuario_aux_last_9 foreign key (aux_last_id) references aux_last (id);
create index ix_usuario_aux_last_9 on usuario (aux_last_id);



# --- !Downs

drop table if exists aux_last cascade;

drop table if exists evaluacion cascade;

drop table if exists metrica_gcs cascade;

drop table if exists metrica_gin cascade;

drop table if exists metrica_gns cascade;

drop table if exists usuario cascade;

drop sequence if exists aux_last_seq;

drop sequence if exists evaluacion_seq;

drop sequence if exists metrica_gcs_seq;

drop sequence if exists metrica_gin_seq;

drop sequence if exists metrica_gns_seq;

drop sequence if exists usuario_seq;

