--
-- Statistiques sur les demandes de cession
--
CREATE TABLE public.statistiqueDemande
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    statut character varying,
    datedemandecession Date DEFAULT current_date,
    idDemandeCession bigint
);

CREATE SEQUENCE public.statistiqueDemande_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.statistiqueDemande ALTER COLUMN id DROP IDENTITY IF EXISTS;

UPDATE public.statistiqueDemande SET id=nextval('public.statistiqueDemande_sequence');




CREATE OR REPLACE FUNCTION  public.trigger_statistiqueDemande()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
DECLARE
    idSR NUMERIC;
    idSA NUMERIC;
BEGIN
    SELECT id FROM public.statut  WHERE code ='RISQUEE' INTO STRICT idSR;
   SELECT id FROM public.statut  WHERE code ='NON_RISQUEE' INTO STRICT idSA;
    IF(New.statutid = idSR) THEN
        INSERT INTO public.statistiqueDemande(id, statut, idDemandeCession)
        VALUES (nextval('public.statistiqueDemande_sequence'), 'REJETE', New.id);
    END IF;
    IF(New.statutid = idSA)THEN
        INSERT INTO public.statistiqueDemande(id, statut,idDemandeCession)
        VALUES (nextval('public.statistiqueDemande_sequence'), 'ACCEPTE', New.id);
    END IF;

RETURN NEW;
END;
$BODY$;

--ALTER OR REPLACE FUNCTION public.trigger_statistiqueDemande()
    --OWNER TO cdmp;

CREATE TRIGGER trigger_statistiqueDemande
    AFTER UPDATE on public.demandeCession
        FOR EACH ROW EXECUTE FUNCTION public.trigger_statistiqueDemande();




CREATE FUNCTION public.statistiqueDemandeByStatutAndMoth(
    statutDemande character varying,
    dateDemande DATE
)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
nombre NUMERIC;
BEGIN
SELECT COUNT(*) FROM
    public.statistiqueDemande WHERE
    (
                statut = statutDemande AND
                (datedemandecession BETWEEN dateDemande AND dateDemande + interval '1 month' )) INTO STRICT nombre;
RETURN nombre;
END;
$BODY$;
