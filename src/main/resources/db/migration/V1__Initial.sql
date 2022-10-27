
create table profile (
    id varchar(50) not null, 
    code varchar(255) not null, 
    description varchar(255) not null, 
    libelle varchar(255) not null, 
    primary key (id)
)

create table user (
    id varchar(50) not null, 
    active int not null, 
    password varchar(255) not null, 
    token varchar(255), 
    username varchar(255) not null, 
    profile_id varchar(50) not null, 
    primary key (id)
)

create table menu (
    id varchar(255) not null, 
    libelle varchar(255) not null, 
    primary key (id)
)

create table permission (
    id varchar(50) not null, 
    code varchar(255) not null, 
    libelle varchar(255) not null, 
    menu_id varchar(255) not null, 
    primary key (id)
)

create table profile_permission (
    profile_id varchar(50) not null, 
    permission_id varchar(50) not null, 
    primary key (profile_id, permission_id)
)

create table minister (
    id varchar(50) not null, 
    code varchar(255) not null, 
    description varchar(255) not null, 
    libelle varchar(255) not null, 
    primary key (id)
)

create table member (
    id varchar(50) not null, 
    adresse varchar(255) not null, 
    date_de_naissance varchar(255) not null, 
    nom varchar(255) not null, 
    photo varchar(255), 
    prenom varchar(255) not null, 
    sexe varchar(255) not null, 
    telephone varchar(255) not null, 
    primary key (id)
)

insert into profile(code, description, libelle) values ('ADMIN', 'Administrateur du system', 'ADMIN')
insert into profile(code, description, libelle) values ('MANAGER', 'Manager du system', 'MANAGER')

insert into menu (libelle) values ('Habilitation')
insert into menu (libelle) values ('Utilisateur')
insert into menu (libelle) values ('Profile')
insert into menu (libelle) values ('Ministére')
insert into menu (libelle) values ('Membre')

insert into permission (code, libelle, menu_id, id) values (?, ?, ?)