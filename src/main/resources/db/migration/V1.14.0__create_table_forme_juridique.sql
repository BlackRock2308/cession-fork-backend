CREATE TABLE public.formeJuridique (
                                           id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
                                           code character varying(25) UNIQUE,
                                           libelle character varying(250)
);

ALTER TABLE ONLY public.formeJuridique
    ADD CONSTRAINT formejuridique_pkey PRIMARY KEY (id);

ALTER TABLE public.pme
    ADD COLUMN IF NOT EXISTS formejuridiqueid bigint;

ALTER TABLE ONLY public.pme
    ADD CONSTRAINT fk_pme_formejuridique FOREIGN KEY (formejuridiqueid) REFERENCES public.formeJuridique(id);

ALTER TABLE public.pme
DROP COLUMN IF EXISTS formejuridique;