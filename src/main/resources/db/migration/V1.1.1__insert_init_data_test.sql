--utilisateur--
INSERT INTO public.utilisateur(
    adresse, codepin, prenom, nom,motdepasse, telephone,  email)
VALUES ('Mermoz/Dakar/Senegal',1023,'Oumar',  'Ndiaye','passer',  '+221 77 381 83 40','sahabandiaye@gmail.com');

INSERT INTO public.utilisateur(
    adresse, codepin, prenom, nom,motdepasse, telephone,  email)
VALUES ('Mermoz/Dakar/Senegal',1003,'Alioune',  'Ngom','pass',  '+221 77 661 83 40','test@gmail.com');
----

--Role--
INSERT INTO public.role(
    libelle)
VALUES ('cgr');
INSERT INTO public.role(
    libelle)
VALUES ('dg');
INSERT INTO public.role(
    libelle)
VALUES ('pme');
INSERT INTO public.role(
    libelle)
VALUES ('pme');
INSERT INTO public.role(
    libelle)
VALUES ('comptable');
INSERT INTO public.role(
    libelle)
VALUES ('ordonnateur');
----
--Agent--

INSERT INTO public.agent(
    prenom, nom, adresse, telephone, codepin, email)
VALUES ('Oumar', 'Ndiaye', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', 1023,'sahabandiaye@gmail.com');

INSERT INTO public.agent(
    prenom, nom, adresse, telephone, codepin, email)
VALUES ('Alioune',  'Ngom', 'Mermoz/Dakar/Senegal',  '+221 77 661 83 40', 1003,'test@gmail.com');
----

INSERT INTO public.pme(
    prenomrepresentant, nomrepresentant, rccm, adresse, telephone, centrefiscal, dateimmatriculation, ninea, raisonsocial, atd, nantissement, interdictionbancaire, identificationbudgetaire, urlimageprofil, email, codepin, urlimagesignature, representantlegal, datedemandeadhesion, nineaexistant, isactive, hasninea, agentid)
VALUES ('Oumar', 'Ndiaye', 'G-T1239877', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', 'Dakar', '2024-10-08','12765A767', 'KanGam', 'Aucun ATD', 'Pas Nanti', 'Aucune Interdiction Bancaire',true, './', 'sahabandiaye@gmail.com', 1232, './', 'Oumar Sahaba Ndiaye', '2025-01-01', true, true,true, 1);

INSERT INTO public.pme(
    prenomrepresentant, nomrepresentant, rccm, adresse, telephone, centrefiscal, dateimmatriculation, ninea, raisonsocial, atd, nantissement, interdictionbancaire, identificationbudgetaire, urlimageprofil, email, codepin, urlimagesignature, representantlegal, datedemandeadhesion, nineaexistant, isactive, hasninea, agentid)
VALUES ('Oumar', 'Ndiaye', 'G-T1239877', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', 'Dakar', '2024-10-08','12765A767', 'KanGam', 'Aucun ATD', 'Pas Nanti', 'Aucune Interdiction Bancaire',true, './', 'sahabandiaye@gmail.com', 1232, './', 'Oumar Sahaba Ndiaye', '2025-01-01', true, true,true, 1);

--BE--
INSERT INTO public.bonengagement(
    reference, natureprestation, objetdepense, datebonengagement, montantcreance, modereglement, nommarche)
VALUES ('B-aza1234', 'Service','Recrutement', '2024-10-01',200000000,'Virement' , 'Campagne de publicité Orange');

INSERT INTO public.bonengagement(
    reference, natureprestation, objetdepense, datebonengagement, montantcreance, modereglement, nommarche)
VALUES ('D-aza1234', 'Service','Achat', '2024-10-01',80000000,'Virement' , 'Fournitures de matériels de burreau');
----

--Statut--


INSERT INTO public.statut(
    code, libelle)
VALUES ('01', 'ADHESION_REJETEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('02', 'SOUMISE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('03', 'RECEVABLE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('04', 'NON_RISQUEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_SIGNEE_PAR_DG');

INSERT INTO public.statut(
    code, libelle)
VALUES ('07', 'CONVENTION ACCPTEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('08', 'PME PARTIELLEMENT PAYEE');
----

--Demande--
INSERT INTO public.demandeadhesion(
    pmeid, statutid, datedemandeadhesion)
VALUES (1,1,'2025-03-08');

INSERT INTO public.demandecession(
    pmeid,  statutid, bonengagementid, datedemandecession)
VALUES (2,3,1,'2025-03-08');

INSERT INTO public.demandecession(
    pmeid,statutid, bonengagementid, datedemandecession)
VALUES (2,5,2,'2025-03-08');
----
--Observation--
INSERT INTO public.observation(
    libelle, dateobservation, agentid, demandeid)
VALUES ('BE nanti', '2025-03-08',2,2);
----

--convention--
INSERT INTO public.convention(
    dateconvention, decote, modepaiement, agentid, pmeid)
VALUES ('2025-03-08', '1O%', 'VIREMENT',2, 1);
----
--paiement--
INSERT INTO public.paiement(
    demandeid, soldepme, montantrecucdmp, montant, datepaiement)
VALUES (2,2000000, 0, 4000000,'2025-03-08');

-----

INSERT INTO public.detailspaiement(
    modepaiement, datepaiement, comptable, montant, typepaiement, paiementid)
VALUES ('VIREMENT','2025-03-08','Oumar', 12000000,'CDMP_PME', 1);

