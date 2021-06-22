/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     22/06/2021 12:38:21                          */
/*==============================================================*/


drop table if exists pmb_account;

drop table if exists pmb_transaction;

drop table if exists pmb_user;

drop table if exists pmb_user_contact;

/*==============================================================*/
/* Table: pmb_account                                           */
/*==============================================================*/
create table pmb_account
(
   id                   int not null,
   owner_id             int not null,
   accountNumber        varchar(10) not null,
   balance              BigDecimal not null,
   createdDate          DateTime not null,
   createdBy            varchar(50) not null,
   modifiedDate         DateTime,
   modifiedBy           varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: pmb_transaction                                       */
/*==============================================================*/
create table pmb_transaction
(
   id                   int not null,
   initiator            int not null,
   receiver             int,
   account_number       varchar(10) not null,
   reference            varchar(30) not null,
   transactionType      varchar(30) not null,
   commission           BigDecimal,
   amount               BigDecimal,
   description          varchar(100),
   createdDate          DateTime not null,
   createdBy            varchar(50) not null,
   modifiedDate         DateTime,
   modifiedBy           varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: pmb_user                                              */
/*==============================================================*/
create table pmb_user
(
   id                   int not null,
   username             varchar(50) not null,
   password             varchar(254) not null,
   role                 varchar(50) not null,
   createdDate          DateTime not null,
   createdBy            varchar(50) not null,
   modifiedDate         DateTime,
   modifiedBy           varchar(50),
   primary key (id),
   key AK_Identifier_1 (id)
);

/*==============================================================*/
/* Table: pmb_user_contact                                      */
/*==============================================================*/
create table pmb_user_contact
(
   user_owner           int not null,
   user_added           int not null,
   createdDate          DateTime,
   createdBy            varchar(50),
   modifiedDate         DateTime,
   modifiedBy           varchar(50),
   primary key (user_owner, user_added)
);

alter table pmb_account add constraint FK_Association_1 foreign key (owner_id)
      references pmb_user (id) on delete restrict on update restrict;

alter table pmb_transaction add constraint FK_Association_2 foreign key (account_number)
      references pmb_account (id) on delete restrict on update restrict;

alter table pmb_transaction add constraint FK_Association_3 foreign key (initiator)
      references pmb_user (id) on delete restrict on update restrict;

alter table pmb_transaction add constraint FK_Association_6 foreign key (receiver)
      references pmb_user (id) on delete restrict on update restrict;

alter table pmb_user_contact add constraint FK_Association_4 foreign key (user_owner)
      references pmb_user (id) on delete restrict on update restrict;

alter table pmb_user_contact add constraint FK_Association_5 foreign key (user_added)
      references pmb_user (id) on delete restrict on update restrict;

