--
-- Statistiques sur les demandes de cession
--
CREATE TABLE public.statistiqueDemande
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    statut character varying,
    datedemandecession DEFAULT current_date
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
BEGIN
    IF(New.statut.code = '03') THEN
        INSERT INTO public.statistiqueDemande(id, statut)
        VALUES (nextval('public.statistiqueDemande_sequence'), 'REJETE');
    END IF;
    IF(New.statut.code = '04')THEN
        INSERT INTO public.statistiqueDemande(id, statut)
        VALUES (nextval('public.statistiqueDemande_sequence'), 'ACCEPTE');
    END IF;

RETURN NEW;
END;
$BODY$;

ALTER FUNCTION public.trigger_statistiqueDemande()
    OWNER TO cdmp;

CREATE TRIGGER trigger_statistiqueDemande
    AFTER INSERT or UPDATE on public.demandeCession
        FOR EACH ROW EXECUTE FUNCTION public.trigger_statistiqueDemande();




CREATE FUNCTION public.statistiqueDemandeByStatutAndMoth(
    statut character varying,
    datedemandecession timestamp without time zone
)
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN return query SELECT COUNT(*)
    FROM
   public.statistiqueDemande AS sDC
WHERE
(
    sDC.statut = statut AND
    (sDC.datedemandecession BETWEEN datedemandecession AND datedemandecession + interval '1 month' )
);
END;
$BODY$;

