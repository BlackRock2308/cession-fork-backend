--utilisateur--
INSERT INTO public.utilisateur(
    adresse, codepin, prenom, nom,password, telephone,  email)
VALUES ('Mermoz/Dakar/Senegal','1021','Oumar',  'Ndiaye','passer',  '+221 77 381 83 40','sahabandiaye@gmail.com');

INSERT INTO public.utilisateur(
    adresse, codepin, prenom, nom,password, telephone,  email)
VALUES ('Mermoz/Dakar/Senegal','1232','Alioune',  'Ngom','pass',  '+221 77 661 83 40','test@gmail.com');
----

----
--Agent--

-- INSERT INTO public.agent(
--     prenom, nom, adresse, telephone, codepin, email)
-- VALUES ('Oumar', 'Ndiaye', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', '1313','sahabandiaye@gmail.com');

-- INSERT INTO public.agent(
--     prenom, nom, adresse, telephone, codepin, email)
-- VALUES ('Alioune',  'Ngom', 'Mermoz/Dakar/Senegal',  '+221 77 661 83 40', '1212','test@gmail.com');
-- ----

INSERT INTO public.pme(
    prenomrepresentant, nomrepresentant, rccm, adresse, telephone, centrefiscal, dateimmatriculation, ninea, raisonsocial, atd, nantissement, interdictionbancaire, identificationbudgetaire, urlimageprofil, email, codepin, urlimagesignature, representantlegal, datedemandeadhesion, nineaexistant, isactive, hasninea)
VALUES ('Oumar', 'Ndiaye', 'G-T1239877', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', 'Dakar', '2024-10-08','12765A767', 'KanGam', false, false, false, true, './', 'sahabandiaye@gmail.com', 1232, './', 'Oumar Sahaba Ndiaye', '2025-01-01', true, true,true);

INSERT INTO public.pme(
    prenomrepresentant, nomrepresentant, rccm, adresse, telephone, centrefiscal, dateimmatriculation, ninea, raisonsocial, atd, nantissement, interdictionbancaire, identificationbudgetaire, urlimageprofil, email, codepin, urlimagesignature, representantlegal, datedemandeadhesion, nineaexistant, isactive, hasninea)
VALUES ('Oumar', 'Ndiaye', 'G-T1239877', 'Mermoz/Dakar/Senegal', '+221 77 381 83 40', 'Dakar', '2024-10-08','12765A767', 'KanGam', false, false, false,true, './', 'sahabandiaye@gmail.com', 1232, './', 'Oumar Sahaba Ndiaye', '2025-01-01', true, true,true);

--BE--
INSERT INTO public.bonengagement(
    reference, natureprestation, objetdepense, datebonengagement, montantcreance, modereglement, nommarche)
VALUES ('B-aza1234', 'Service','Recrutement', '2024-10-01',200000000.00,'Virement' , 'Campagne de publicité Orange');

INSERT INTO public.bonengagement(
    reference, natureprestation, objetdepense, datebonengagement, montantcreance, modereglement, nommarche)
VALUES ('D-aza1234', 'Service','Achat', '2024-10-01',80000000.00,'Virement' , 'Fournitures de matériels de burreau');
----

--Statut--

INSERT INTO public.statut(
    code, libelle)
VALUES ('00', 'ADHESION_SOUMISE');
INSERT INTO public.statut(
    code, libelle)
VALUES ('01', 'ADHESION_ACCEPTEE');
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
VALUES ('03', 'REJETEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('04', 'NON_RISQUEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('04', 'RISQUEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('04', 'COMPLEMENT_REQUIS');

INSERT INTO public.statut(
    code, libelle)
VALUES ('04', 'COMPLETEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_GENEREE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_SIGNEE_PAR_PME');

INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_SIGNEE_PAR_DG');

INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_TRANSMISE');


INSERT INTO public.statut(
    code, libelle)
VALUES ('05', 'CONVENTION_ACCEPTEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'PME_EN_ATTENTE_DE_PAIEMENT');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'CDMP_EN_ATTENTE_DE_PAIEMENT');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'PME_PARTIELLEMENT_PAYEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'CDMP_PARTIELLEMENT_PAYEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'PME_TOTALEMENT_PAYEE');

INSERT INTO public.statut(
    code, libelle)
VALUES ('06', 'CDMP_TOTALEMENT_PAYEE');
----

--Demande--
-- INSERT INTO public.demande(
--     pmeid, statutid)
-- VALUES (1,1);
-- INSERT INTO public.demandeadhesion(
--     pmeid, statutid, datedemandeadhesion,demandeid)
-- VALUES (1,1,'2025-03-08',1);

-- INSERT INTO public.demande(
--     pmeid,  statutid)
-- VALUES (2,3);

-- INSERT INTO public.demandecession(
--     pmeid,  statutid, bonengagementid, datedemandecession,demandeid)
-- VALUES (2,3,1,'2025-03-08',2);

-- INSERT INTO public.demande(
--     pmeid,statutid)
-- VALUES (2,7);

-- INSERT INTO public.demandecession(
--     pmeid,statutid, bonengagementid, datedemandecession,demandeid)
-- VALUES (2,7,2,'2025-03-08',3);

-- INSERT INTO public.demandecession(
--     pmeid,statutid, bonengagementid, datedemandecession,demandeid)
-- VALUES (2,15,2,'2025-03-08',3);
-- ----
-- --Observation--
-- INSERT INTO public.observation(
--     libelle, dateobservation,utilisateurid, demandeid)
-- VALUES ('BE nanti', '2025-03-08',2,2);
-- ----

-- --convention--
-- INSERT INTO public.convention(
--     dateconvention, decote, modepaiement, utilisateurid, pmeid,demandeid)
-- VALUES ('2025-03-08', 10.00, 'VIREMENT',2, 1,3);
-- ----
-- --paiement--
-- INSERT INTO public.paiement(
--     demandeid, soldepme, montantrecucdmp)
-- VALUES (2,2000000.00, 00.00);

-- -----

-- INSERT INTO public.detailspaiement(
--     modepaiement, datepaiement, comptable, montant, typepaiement, paiementid)
-- VALUES ('VIREMENT','2025-03-08','Oumar', 12000000.00,'CDMP_PME', 1);

