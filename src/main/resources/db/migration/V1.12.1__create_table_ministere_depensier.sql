CREATE TABLE public.ministeredepensier (
            id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
            code character varying(25),
            libelle character varying(150)
);

ALTER TABLE ONLY public.ministeredepensier
    ADD CONSTRAINT ministeredepensier_pkey PRIMARY KEY (id);
    
ALTER TABLE public.demandeCession
    ADD COLUMN IF NOT EXISTS ministeredepid bigint;

ALTER TABLE ONLY public.demandecession
    ADD CONSTRAINT fk_demandecession_ministeredepensier FOREIGN KEY (ministeredepid) REFERENCES public.ministeredepensier(id);

ALTER TABLE public.utilisateur
    ADD COLUMN IF NOT EXISTS ministeredepid bigint;

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT fk_utilisateur_ministeredepensier FOREIGN KEY (ministeredepid) REFERENCES public.ministeredepensier(id);
  