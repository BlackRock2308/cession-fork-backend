ALTER TABLE public.utilisateur
ADD COLUMN IF NOT EXISTS update_codepin boolean;
