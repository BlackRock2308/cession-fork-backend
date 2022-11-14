---Fontion qui récupère le cumul des decotes par mois: decode = convention.valeurDecote*bonengagement.montantCreance---
CREATE OR REPLACE FUNCTION public.cumlDecoteByMonth(
    monthConvention timestamp without time zone
)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
cumulDecode NUMERIC;
BEGIN

SELECT SUM(COALESCE(conv.valeurDecote,0) * COALESCE(bE.montantCreance,0))  FROM
    public.convention AS conv INNER JOIN  public.demandeCession AS dC ON  dC.id=conv.demandeid
                              INNER JOIN  public.bonengagement AS bE ON  bE.id=dC.bonengagementid WHERE conv.dateconvention
                                                                                                            BETWEEN monthConvention AND (monthConvention + interval '1 month')
    INTO STRICT cumulDecode;

RETURN cumulDecode;
END;
$BODY$;


---Fontion qui récupère le cumul des montants d'engagement par mois---
CREATE FUNCTION public.cumlMontantCreanceByMonth(
    monthEngagement timestamp without time zone
)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
cumulmontantCreance  NUMERIC;
BEGIN

SELECT SUM(bE.montantCreance)  FROM
    public.bonengagement AS bE WHERE bE.datebonengagement
    BETWEEN monthEngagement AND (monthEngagement + interval '1 month')
    INTO STRICT cumulmontantCreance;

RETURN cumulmontantCreance;
END;
$BODY$;


---Fontion qui récupère le cumul des montants detailsPaiement par mois---
CREATE FUNCTION public.cumlMontantDetailsPaiementByMonth(
    typeDP character varying,
    monthPaiement timestamp without time zone
)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
cumulMontant  NUMERIC;
BEGIN

SELECT SUM(dP.montant)  FROM
    public.detailsPaiement AS dP  WHERE dP.typePaiement = typeDP AND dP.datePaiement
    BETWEEN monthPaiement AND (monthPaiement + interval '1 month')
    INTO STRICT cumulMontant;

RETURN cumulMontant;
END;
$BODY$;

---Fontion qui récupère le cumul des soldes PME par mois---
CREATE FUNCTION public.cumlSoldePMEByMonth(
    monthSolde timestamp without time zone,
    cumulMontantCreance double precision,
    cumulDebourse double precision

)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
cumulDecode NUMERIC;
BEGIN
    SELECT public.cumlDecoteByMonth(monthSolde) INTO STRICT cumulDecode;
    RETURN cumulMontantCreance - cumulDecode - cumulDebourse ;
    END;
$BODY$;

---Fontion qui récupère les statistiques des paiements SICA-CDMP par mois---
CREATE TYPE public.StatistiquePaiementCDMP AS(
    decote numeric, montantCreance numeric, rembourse numeric, solde numeric);

CREATE FUNCTION public.getStatistiquePaiementCDMP(
    monthData timestamp without time zone
)
    RETURNS public.StatistiquePaiementCDMP
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
resultat public.StatistiquePaiementCDMP;
BEGIN
    SELECT public.cumlDecoteByMonth(monthData) INTO STRICT resultat.decote;
    SELECT public.cumlMontantCreanceByMonth(monthData) INTO STRICT resultat.montantCreance;
    SELECT public.cumlMontantDetailsPaiementByMonth('SICA_CDMP', monthData) INTO STRICT resultat.rembourse;
    resultat.solde := resultat.montantCreance - resultat.rembourse;
    RETURN resultat;
END;
$BODY$;

---Fontion qui récupère les statistiques des paiements CDMP-PME par mois---
CREATE TYPE public.StatistiquePaiementPME AS(
    solde numeric, montantCreance numeric, debource numeric);

CREATE FUNCTION public.getStatistiquePaiementPME(
    monthData timestamp without time zone
)
    RETURNS public.StatistiquePaiementPME
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
resultat public.StatistiquePaiementPME;
BEGIN
    SELECT public.cumlMontantCreanceByMonth(monthData) INTO STRICT resultat.montantCreance;
    SELECT public.cumlMontantDetailsPaiementByMonth('CDMP_PME', monthData) INTO STRICT resultat.debource;
    SELECT public.cumlSoldePMEByMonth(monthData, resultat.montantCreance, resultat.debource) INTO STRICT resultat.solde;
    RETURN resultat;
END;
$BODY$;





