CREATE TABLE public.text_convention (
            id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
            var1 character varying(255),
            var2 character varying(255),
            var3 character varying(255),
            var4 character varying(255),
            var5 character varying(255),
            var6 character varying(255)
);

ALTER TABLE ONLY public.text_Convention
    ADD CONSTRAINT text_convention_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_text_convention FOREIGN KEY (id) REFERENCES public.text_Convention(id);

ALTER TABLE public.convention
DROP COLUMN IF EXISTS remarqueJuriste;

ALTER TABLE public.convention
    ADD COLUMN IF NOT EXISTS textConventionid bigint;