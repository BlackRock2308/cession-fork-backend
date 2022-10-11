CREATE VIEW public.vw_BE_document
 AS
SELECT id, date_sauvegarde, nom, id_provenance, provenance, url_file, typedocument
	FROM public.document where provenance = 'BE';
	
COMMENT ON VIEW public.vw_BE_document
    IS 'view to be used to get document of a BE';
    
CREATE VIEW public.vw_PME_document
 AS
SELECT id, date_sauvegarde, nom, id_provenance, provenance, url_file, typedocument
	FROM public.document where provenance = 'PME';
	
COMMENT ON VIEW public.vw_PME_document
    IS 'view to be used to get document of a PME';

CREATE VIEW public.vw_convention_document
 AS
SELECT id, date_sauvegarde, nom, id_provenance, provenance, url_file, typedocument
	FROM public.document where provenance = 'CONVENTION';
	
COMMENT ON VIEW public.vw_convention_document
    IS 'view to be used to get document of a convention';

CREATE VIEW public.vw_DPaiement_document
 AS
SELECT id, date_sauvegarde, nom, id_provenance, provenance, url_file, typedocument
	FROM public.document where provenance = 'DETAIL_PAIEMENT';
	
COMMENT ON VIEW public.vw_DPaiement_document
    IS 'view to be used to get document of a DPaiement';
    
CREATE VIEW public.vw_demande_document
 AS
SELECT id, date_sauvegarde, nom, id_provenance, provenance, url_file, typedocument
	FROM public.document where provenance = 'DEMANDE';
	
COMMENT ON VIEW public.vw_demande_document
    IS 'view to be used to get document of a DEMANDE';
                   
CREATE SEQUENCE public.document_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.document ALTER COLUMN id DROP IDENTITY IF EXISTS;

UPDATE public.document SET id=nextval('public.document_sequence');
    