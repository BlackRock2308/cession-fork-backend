ALTER TABLE public.convention
    ADD COLUMN IF NOT EXISTS valeurDecote FLOAT(40);

ALTER TABLE public.convention
    ADD COLUMN IF NOT EXISTS utilisateur_id bigint;


ALTER TABLE public.convention
DROP COLUMN IF EXISTS id_decote;