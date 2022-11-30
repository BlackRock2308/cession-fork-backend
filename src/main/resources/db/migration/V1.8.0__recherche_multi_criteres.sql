
CREATE OR REPLACE FUNCTION public.recherche_demande_de_cession(reference_be character varying ,
                                           numero_demande character varying ,
                                            nom_marche character varying ,
                                          statut_libelle character varying,
                                            start_date timestamp without time zone,
                                            end_date timestamp without time zone) RETURNS TABLE(
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
IF start_date IS NOT NULL AND end_date IS NOT NULL THEN
RETURN query
select
    dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
    FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
    INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
            (dc.datedemandecession BETWEEN start_date AND end_date) AND
            (dc.numeroDemande=numero_demande OR numero_demande='') AND
            (be.nomMarche=nom_marche OR nom_marche='') AND
            (be.reference=reference_be OR reference_be='') AND
            (st.libelle=statut_libelle OR statut_libelle='');

ELSE
RETURN query
select
    dc.id, dc.pmeid, dc.statutid,dc.bonengagementid, dc.datedemandecession, dc.demandeid, dc.paiementid, dc.numeroDemande
FROM public.demandeCession as dc INNER JOIN public.bonengagement AS be ON be.id = dc.bonengagementid
                                 INNER JOIN public.statut AS st ON st.id = dc.statutid WHERE
    (dc.numeroDemande=numero_demande OR numero_demande='') AND
    (be.nomMarche=nom_marche OR nom_marche='') AND
    (be.reference=reference_be OR reference_be='') AND
    (st.libelle=statut_libelle OR statut_libelle='');

END IF;

END;

$BODY$



