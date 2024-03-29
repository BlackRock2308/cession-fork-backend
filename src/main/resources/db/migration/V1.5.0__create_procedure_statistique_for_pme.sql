---Fontion qui récupère le cumul des decotes par mois: decode = convention.valeurDecote*bonengagement.montantCreance---
CREATE  FUNCTION public.cumlDecoteByMonthAndPME(
    idPME bigint,
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

SELECT SUM(conv.valeur_decote_dg * bE.montantCreance)  FROM public.convention AS conv
    INNER JOIN  public.demandeCession AS dC ON  dC.id=conv.demandeid
    INNER JOIN  public.bonengagement AS bE ON  bE.id=dC.bonengagementid WHERE dC.paiementid IS NOT NULL AND
        dC.pmeid=idPME AND conv.dateconvention BETWEEN monthConvention AND (monthConvention + interval '1 month')
    INTO STRICT cumulDecode;

RETURN cumulDecode;
END;
$BODY$;


---Fontion qui récupère le cumul des montants d'engagement par mois---
CREATE FUNCTION public.cumlMontantCreanceByMonthAndPME(
    idPME bigint,
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

SELECT SUM(bE.montantCreance)  FROM public.demandeCession AS dC
    INNER JOIN public.bonengagement AS bE ON bE.id=dC.bonengagementid WHERE
    dC.pmeid=idPME AND dC.paiementid IS NOT NULL AND  bE.datebonengagement
    BETWEEN monthEngagement AND (monthEngagement + interval '1 month')
    INTO STRICT cumulmontantCreance;
RETURN cumulmontantCreance;
END;
$BODY$;


---Fontion qui récupère le cumul des montants detailsPaiement par mois---
CREATE FUNCTION public.cumlMontantDetailsPaiementByMonthAndPME(
    idPME bigint,
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

SELECT SUM(dP.montant)  FROM public.demandeCession AS dC INNER JOIN
    public.detailsPaiement AS dP ON dC.paiementid=dP.paiementid
    WHERE dP.typePaiement='CDMP_PME' AND dC.pmeid=idPME AND
    dP.datePaiement BETWEEN monthPaiement AND (monthPaiement + interval '1 month')
    INTO STRICT cumulMontant;

RETURN cumulMontant;
END;
$BODY$;

---Fontion qui récupère les statistiques des paiements CDMP-PME par mois et PME---

CREATE FUNCTION public.getStatistiquePaiementByPME(
    idPME bigint,
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
    SELECT public.cumlDecoteByMonthAndPME(idPME,monthData) INTO STRICT resultat.decote;
    SELECT public.cumlMontantCreanceByMonthAndPME(idPME,monthData) INTO STRICT resultat.montantCreance;
    SELECT public.cumlMontantDetailsPaiementByMonthAndPME(idPME, monthData) INTO STRICT resultat.debource;
    resultat.montantCreance:= resultat.montantCreance - resultat.decote;
    resultat.solde:= resultat.montantCreance - resultat.debource;
    RETURN resultat;
END;
$BODY$;
