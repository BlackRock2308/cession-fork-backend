--ALTER TABLE public.convention
    --ADD COLUMN valeurDecote FLOAT(40);

ALTER TABLE public.convention
    ADD COLUMN utilisateur_id bigint;


ALTER TABLE public.convention
DROP COLUMN IF EXISTS id_decote;