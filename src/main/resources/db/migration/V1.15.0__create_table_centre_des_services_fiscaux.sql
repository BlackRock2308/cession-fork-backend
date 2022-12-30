CREATE TABLE public.centreDesServicesFiscaux (
                                       id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
                                       code character varying(25) UNIQUE,
                                       libelle character varying(250)
);

ALTER TABLE ONLY public.centreDesServicesFiscaux
    ADD CONSTRAINT centre_des_servicesFiscaux_pkey PRIMARY KEY (id);

--ALTER TABLE public.pme
    --ADD COLUMN IF NOT EXISTS formejuridiqueid bigint;

--ALTER TABLE ONLY public.pme
   -- ADD CONSTRAINT fk_pme_centre_des_servicesFiscaux FOREIGN KEY (formejuridiqueid) REFERENCES public.formeJuridique(id);
