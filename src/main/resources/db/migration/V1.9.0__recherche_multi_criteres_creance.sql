

CREATE FUNCTION public.recherche_creance(raison_social character varying ,
                                            montant_creance double precision,
                                            nom_marche character varying ,
                                            statut_libelle character varying,
                                            decote double precision,
                                            start_date_d timestamp without time zone,
                                            end_date_d timestamp without time zone,
                                            start_date_m timestamp without time zone,
                                            end_date_m timestamp without time zone) RETURNS TABLE(
                                id bigint ,
                                pmeid bigint ,
                                statutid bigint ,
                                bonengagementid bigint,
                                datedemandecession timestamp without time zone,
                                demandeid bigint ,
                                paiementid bigint,
                                numeroDemande character varying(100))

LANGUAGE 'plpgsql' AS $BODY$

BEGIN
IF start_date_d IS NOT NULL AND end_date_d IS NOT NULL AND start_date_m IS NOT NULL AND end_date_m IS NOT NULL  THEN
RETURN query
select
    dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
                                INNER JOIN public.convention as cv ON cv.demandeid=dc.id
                                INNER JOIN public.pme as p ON p.id = dc.pmeid
                                 INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
    (dc.datedemandecession BETWEEN start_date_d AND end_date_d) AND
    (be.datebonengagement BETWEEN start_date_m AND end_date_m) AND
    (st.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')) AND
    (montant_creance IS NULL OR be.montantCreance=montant_creance  OR montant_creance=0 ) AND
    (be.nomMarche=nom_marche OR nom_marche='') AND
   (decote IS NULL OR cv.valeur_decote_dg=decote OR decote=0) AND
    (p.raisonsocial=raison_social OR raison_social='') AND
    (st.libelle=statut_libelle OR statut_libelle='');

ELSE
IF start_date_d IS NOT NULL AND end_date_d IS NOT NULL THEN
RETURN query
select
    dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
                                 INNER JOIN public.convention as cv ON cv.demandeid=dc.id
                                 INNER JOIN public.pme as p ON p.id = dc.pmeid
                                 INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
    (dc.datedemandecession BETWEEN start_date_d AND end_date_d) AND
    (st.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')) AND
    (be.montantCreance=montant_creance OR montant_creance IS NULL OR montant_creance=0 ) AND
    (be.nomMarche=nom_marche OR nom_marche='') AND
    (cv.valeur_decote_dg=decote OR decote IS NULL OR decote=0) AND
    (p.raisonsocial=raison_social OR raison_social='') AND
    (st.libelle=statut_libelle OR statut_libelle='');

ELSE
IF start_date_m IS NOT NULL AND end_date_m IS NOT NULL THEN
RETURN query
select
    dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
                                 INNER JOIN public.convention as cv ON cv.demandeid=dc.id
                                 INNER JOIN public.pme as p ON p.id = dc.pmeid
                                 INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
    (be.datebonengagement BETWEEN start_date_m AND end_date_m) AND
    (st.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')) AND
    (be.montantCreance=montant_creance OR montant_creance IS NULL OR montant_creance=0 ) AND
    (be.nomMarche=nom_marche OR nom_marche='') AND
    (cv.valeur_decote_dg=decote OR decote IS NULL OR decote=0) AND
    (p.raisonsocial=raison_social OR raison_social='') AND
    (st.libelle=statut_libelle OR statut_libelle='');

ELSE
    RETURN query
    select
        dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
    FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
                                     INNER JOIN public.convention as cv ON cv.demandeid=dc.id
                                     INNER JOIN public.pme as p ON p.id = dc.pmeid
                                     INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
        (st.libelle NOT IN ('SOUMISE','RECEVABLE','COMPLEMENT_REQUIS','REJETEE','COMPLETEE')) AND
     (be.montantCreance=montant_creance OR montant_creance IS NULL OR montant_creance=0 ) AND
    --(be.nomMarche=nom_marche OR nom_marche='') AND
     (cv.valeur_decote_dg=decote OR decote IS NULL OR decote=0) AND
       (p.raisonsocial=raison_social OR raison_social='') AND
   (st.libelle=statut_libelle OR statut_libelle='');

END IF;

END IF;


END IF;



END;

$BODY$


