    use gaspillagealimentaire;

    insert into adresse (numéro_municipal, rue, ville, province, code_postal, pays)
    values  ("1111", "Place des Chocolats", "Montreal", "QC", "H3A 0G4", "CA"),
            ("2222", "Longue Rue", "Montreal", "QC", "H1B 0N4", "CA"),
            ("3333", "Rue Addison", "Toronto", "ON", "M5H 2N2", "CA"),
            ("4444", "Rue Est", "Toronto", "ON", "M9B 3N4", "CA"),
            ("5555", "Rue West", "Toronto", "ON", "K1P 5Z9", "CA");

    insert into utilisateur (nom, prénom, courriel, adresse_id, téléphone, rôle, codeAuth)
    values  ("Montplaisir", "Samuel", "sammontplaisir@gmail.com", 1, "514 123-9895", "client", "auth0|656e22be4178aefc03438594"),
            ("Khakimov", "Alikhan", "akhakimov@gmail.com", 2, "514 676-9823", "épicerie", "auth0|656e22da34408e731c3b0153"),
            ("Tabti", "Lyazid", "lyatabti@gmail.com", 3, "514 894-8268", "client", "auth0|656e22ff1de2cf4321c09bf1"),
            ("Vienneau", "Joël", "joelvienneau@gmail.com", 4, "514 894-8268", "livreur", "auth0|656e232b4178aefc0343862a"),
            ("Lerouge", "Jean-Gabriel", "gabriellerouge@gmail.com", 5, "514 112-8391", "livreur", "auth0|656e234f1de2cf4321c09c44"),
            ("Ligtas", "Audric", "audricligtas@gmail.com", 5, "514 892-1903", "épicerie", "auth0|656e236b1de2cf4321c09c6e");


    insert into épicerie(adresse_id, utilisateur_code, nom, courriel, téléphone)
    values  (2, 2, "Metro", "metro@gmail.com", "514 231-6666"),
            (4, 6, "iga", "iga@gmail.com", "514 231-6666");

    insert into gabaritProduit(nom, description, catégorie, idÉpicerie)
    values  ("tomate", "tomate rouge ", "Fruit&Légumes", 1),
            ("boite de concerve de tomate", "tomate broyées avec herbes italienne", "Aliments en concerve", 1);

    insert into produits(nom, date_expiration, quantité, prix, idÉpicerie, idGabarit)
    values  ("tomate de la ferme Rosemont", "2023-11-20 23:59:59", 33, 1.2, 1, 1),
            ("tomate de la ferme Amarika", "2023-11-20 23:59:59", 33, 1.2, 1, 1),
            ("Boite de concerve de tomate broyées Casa Di Pomodoros", "2023-11-20 23:59:59", 33, 1.2, 1, 2);

    insert into commande (épicerie_id, utilisateur_code)
    values  (1, 1),
            (2, 1),
            (1, 1);

    insert into commande_produits(commande_code, produit_id, quantité)
    values  (1, 1, 2),
            (1, 3, 5),
            (2, 2, 3),
            (3, 1, 2);

    insert into livraison(commande_code, utilisateur_code, adresse_id)
    values  (1, 4, 4),
            (2, 5, 3),
            (3, 4, 4);

    insert into avis (livraison_code, avis, commentaire)
    values  (1, 4, 'Super! merci beaucoup'),
            (2, 4, 'Super! merci beaucoup'),
            (3, 4, 'Super! merci beaucoup');