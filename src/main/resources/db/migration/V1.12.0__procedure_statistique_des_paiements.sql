DROP FUNCTION public.cumlSoldePMEByMonth;
DROP FUNCTION public.cumlMontantCreanceByMonth;
DROP FUNCTION public.cumlDecoteByMonth;
DROP FUNCTION public.cumlMontantDetailsPaiementByMonth;
DROP FUNCTION public.getStatistiquePaiementPME;
DROP FUNCTION public.getStatistiquePaiementCDMP;
---Fontion qui récupère le cumul des montants detailsPaiement par mois---
CREATE FUNCTION public.cumlMontantDetailsPaiementByMonth(
    typeDP character varying,
    dateSignature timestamp without time zone
)
    RETURNS NUMERIC
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
cumulMontant  NUMERIC;
BEGIN

SELECT SUM(dP.montant)  FROM public.detailsPaiement AS dP  WHERE dP.typePaiement = typeDP AND
        dP.datePaiement < (dateSignature + interval '1 month') INTO STRICT cumulMontant;
RETURN cumulMontant;
END;
$BODY$;

---Fontion qui récupère les paiements du sic-cdmp---
CREATE  FUNCTION public.getStatistiquePaiementCDMP(
    dateSignature timestamp without time zone
)
    RETURNS public.StatistiquePaiementCDMP
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
idSS NUMERIC;
resultat public.StatistiquePaiementCDMP;
BEGIN
SELECT id FROM public.statut  WHERE code ='CONVENTION_ACCEPTEE' INTO STRICT idSS;
SELECT  SUM(bE.montantCreance), SUM(conv.valeur_decote_dg * bE.montantCreance)
FROM public.observation AS ob INNER JOIN public.demandeCession AS dC ON ob.demandeid=dC.id
                INNER JOIN public.convention AS conv ON  dC.id=conv.demandeid
                INNER JOIN  public.bonengagement AS bE ON  bE.id=dC.bonengagementid
                WHERE ob.statut = idSS AND ob.dateobservation < (dateSignature + interval '1 month')
                INTO STRICT resultat.montantCreance, resultat.decote;
SELECT public.cumlMontantDetailsPaiementByMonth('SICA_CDMP', dateSignature) INTO STRICT resultat.rembourse;
resultat.solde := resultat.montantCreance - resultat.rembourse;
RETURN resultat;
END;
$BODY$;

---Fontion qui récupère les paiements du cdmp-pme ---
CREATE FUNCTION public.getStatistiquePaiementPME(
    dateSignature timestamp without time zone
)
    RETURNS public.StatistiquePaiementPME
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
idSS NUMERIC;
resultat public.StatistiquePaiementPME;
BEGIN
SELECT id FROM public.statut  WHERE code ='CONVENTION_ACCEPTEE' INTO STRICT idSS;
SELECT  SUM(bE.montantCreance), SUM(conv.valeur_decote_dg * bE.montantCreance)
FROM public.observation AS ob INNER JOIN public.demandeCession AS dC ON ob.demandeid=dC.id
                              INNER JOIN public.convention AS conv ON  dC.id=conv.demandeid
                              INNER JOIN  public.bonengagement AS bE ON  bE.id=dC.bonengagementid
WHERE ob.statut = idSS AND ob.dateobservation < (dateSignature + interval '1 month')
    INTO STRICT resultat.montantCreance, resultat.decote;
SELECT public.cumlMontantDetailsPaiementByMonth('CDMP_PME', dateSignature) INTO STRICT resultat.debource;
resultat.montantCreance:= resultat.montantCreance - resultat.decote;
        resultat.solde:= resultat.montantCreance - resultat.debource;
RETURN resultat;
END;
$BODY$;


---Fontion qui récupère les paiements du cdmp-pme par mois en pme ---
DROP FUNCTION public.cumlDecoteByMonthAndPME;
DROP FUNCTION public.cumlMontantCreanceByMonthAndPME;
DROP FUNCTION public.cumlMontantDetailsPaiementByMonthAndPME;
DROP FUNCTION public.getStatistiquePaiementByPME;

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
    dP.datePaiement < (monthPaiement + interval '1 month')
    INTO STRICT cumulMontant;

RETURN cumulMontant;
END;
$BODY$;

CREATE FUNCTION public.getStatistiquePaiementByPME(
    idPME bigint,
    dateSignature timestamp without time zone
)
    RETURNS public.StatistiquePaiementPME
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE

AS $BODY$
DECLARE
idSS NUMERIC;
resultat public.StatistiquePaiementPME;
BEGIN
SELECT id FROM public.statut  WHERE code ='CONVENTION_ACCEPTEE' INTO STRICT idSS;
SELECT  SUM(bE.montantCreance), SUM(conv.valeur_decote_dg * bE.montantCreance)
FROM public.observation AS ob INNER JOIN public.demandeCession AS dC ON ob.demandeid=dC.id
                              INNER JOIN public.convention AS conv ON  dC.id=conv.demandeid
                              INNER JOIN  public.bonengagement AS bE ON  bE.id=dC.bonengagementid
WHERE ob.statut = idSS AND ob.dateobservation < (dateSignature + interval '1 month') AND dC.pmeid=idPME
    INTO STRICT resultat.montantCreance, resultat.decote;

SELECT public.cumlMontantDetailsPaiementByMonthAndPME(idPME, dateSignature) INTO STRICT resultat.debource;
resultat.montantCreance:= resultat.montantCreance - resultat.decote;
    resultat.solde:= resultat.montantCreance - resultat.debource;
RETURN resultat;
END;
$BODY$;


