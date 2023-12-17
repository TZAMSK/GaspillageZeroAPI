drop database if exists gaspillagealimentaire;
create database  gaspillagealimentaire;

use gaspillagealimentaire;

create table adresse (
	id int primary key auto_increment,
	numéro_municipal varchar(255) not null,
	rue varchar(255) not null,
	ville varchar(255) not null,
	province char(2),
	code_postal varchar(255) not null,
	pays char(2) not null
);

create table utilisateur (
	code int primary key auto_increment,
	nom varchar(255) not null,
	prénom varchar(255) not null,
	courriel varchar(255) not null,
	adresse_id int not null,
	téléphone varchar(255) not null,
	rôle  set("client", "livreur", "épicerie") not null,
	codeAuth VARCHAR(255),
	constraint fk_utilisateur_adresse_adresse_id foreign key (adresse_id)
	references adresse(id)
);

create table rôle_utilisateur (
	utilisateur_code int not null,
	rôle set("client", "livreur", "épicerie") not null,
	horodatage timestamp not null,
	constraint pk_utilisateurCode_rôle primary key (utilisateur_code, rôle),
	constraint fk_rôleUtilisateur_utilisateur_utilisateurCode foreign key (utilisateur_code)
	references utilisateur(code)
);

create table épicerie(
	id int primary key auto_increment,
	adresse_id int not null,
	utilisateur_code int not null,
	nom varchar(255),
	courriel varchar(255) not null unique,
	téléphone varchar(255) not null,
	logo LONGTEXT,
	constraint fk_épicerie_adresse_adresseID foreign key (adresse_id)
	references adresse(id),
	constraint fk_épicerie_utilisateur_utilisateurCode foreign key (utilisateur_code)
	references utilisateur(code)
);

create table gabaritProduit(
	id int primary key auto_increment not null,
	nom varchar(255) not null,
	description varchar(255) not null,
	image LONGTEXT,
	catégorie varchar(255) not null,
	idÉpicerie int not null,
	constraint fk_gabaritProduit_épicerie_idÉpicerie foreign key (idÉpicerie)
	references épicerie(id)
);

create table produits (
	id int primary key auto_increment,
	nom varchar(255) not null,
	date_expiration timestamp not null,
	quantité int (11) not null,
	prix double not null,
	idÉpicerie int not null,
	idGabarit int not null
);

create table commande (
	code int primary key auto_increment not null,
	épicerie_id int not null,
	utilisateur_code int not null,
	constraint fk_commande_épicerie_épicerieId foreign key (épicerie_id)
	references épicerie(id),
	constraint fk_commande_utilisateur_utilisateurCode foreign key (utilisateur_code)
	references utilisateur(code)
);

create table commande_produits (
	commande_code int not null,
	produit_id int not null,
	quantité int not null,
	constraint primary key (commande_code, produit_id),
	constraint fk_commandeProduit_commande_commandeCode foreign key (commande_code)
	references commande(code),
	constraint fk_commandeProduit_produits_produitId foreign key (produit_id)
	references produits(id)
);

create table livraison (
	code int primary key auto_increment,
	commande_code int not null,
	utilisateur_code int not null,
	adresse_id int not null,
	constraint fk_livraison_commande_commandeCode foreign key (commande_code)
	references commande(code),
	constraint fk_livraison_utilisateur_utilisateurCode foreign key (utilisateur_code)
	references utilisateur(code),
	constraint fk_livraison_adresse_adresseID foreign key (adresse_id)
	references adresse(id)
);

create table avis (
	id int primary key auto_increment,
	livraison_code int not null,
	avis int not null,
	commentaire varchar(255),
	constraint fk_avis_livraison_livraisonCode foreign key (livraison_code)
	references livraison(code)
);
