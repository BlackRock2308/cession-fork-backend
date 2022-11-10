

ALTER TABLE public.convention
    ADD COLUMN utilisateur_id bigint;


ALTER TABLE public.convention
DROP COLUMN IF EXISTS id_decote;