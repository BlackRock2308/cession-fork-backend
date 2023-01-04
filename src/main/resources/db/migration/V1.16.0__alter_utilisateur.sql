ALTER TABLE public.utilisateur
    ADD COLUMN IF NOT EXISTS active boolean DEFAULT TRUE;