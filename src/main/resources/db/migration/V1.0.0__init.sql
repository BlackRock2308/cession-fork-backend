SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: pme; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pme (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    prenomrepresentant character varying(100),
    nomrepresentant character varying(50),
    rccm character varying(100),
    adresse character varying(100),
    telephone character varying(50),
    centrefiscal character varying(100),
    dateimmatriculation timestamp without time zone,
    ninea character varying(15),
    raisonsocial character varying(100),
    atd character varying(200),
    nantissement character varying(200),
    interdictionbancaire character varying(200),
    identificationbudgetaire boolean ,
    formejuridique character varying(50),
    urlimageprofil character varying(250),
    email character varying(50),
    codepin integer,
    urlimagesignature character varying(250),
    representantLegal character varying(250),
    enseigne character varying(100),
    formeJuridique character varying(250),
    representantLegal character varying(250),
    numCNIRepresentant integer ,
    localite character varying(250),
    controle integer ,
    activitePrincipal character varying(250),
    autorisationMinisterielle character varying(250),
    registreCommerce character varying(250),
    dateCreation  timestamp without time zone,
    capitalSocial character varying(250),
    chiffresDaffaires integer,
    effectifPermanent integer,
    nombreEtablissementSecondaires integer,
    dateDemandeAdhesion timestamp without time zone,
    nineaExistant boolean,
    pmeActive boolean


);

--
-- Name: bonengagement; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.bonengagement (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    reference character varying(50),
    natureprestation character varying(100),
    naturedepense character varying(100),
    objetdepense character varying(100),
    imputation character varying(100),
    datebonengagement timestamp without time zone,
    montantCreance double precision,
    identificationcomptable character varying(100),
    date_demande timestamp without time zone,
    exercice character varying(250),
    designatinonBeneficiaire character varying(250),
    actionDestination character varying(250),
    activiteDestination character varying(250),
    typeDepense character varying(250),
    modeReglement character varying(100),
    dateSoumissionServiceDepensier timestamp without time zone





);

--
-- Name: convention; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.convention (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    dateconvention timestamp without time zone,
    decote character varying(255),
    modepaiement character varying(50), 
    agentid bigint,
    pmeid bigint 
);

--
-- Name: demande; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.demande (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    datedemande timestamp without time zone,
    pmeid bigint NOT NULL,
    conventionid bigint,
   	statutid bigint NOT NULL,
   	bonengagementid bigint
);

--
-- Name: paiement; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.paiement (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    demandeid bigint NOT NULL,
    soldePme double precision,
    montantRecuCdmp double precision,
    statutid bigint NOT NULL
);


CREATE TABLE public.detailsPaiement (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    modePaiement character varying(250) ,
    datePaiement timestamp without time zone,
    comptable character varying(100),
    montant double precision,
    reference character varying(250),
    typePaiement character varying(150),
    paiementid bigint NOT NULL
);
--
-- Name: observation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.observation (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    libelle character varying(100),
    dateobservation timestamp without time zone,
    agentid bigint NOT NULL,
    demandeid bigint NOT NULL
);

--
-- Name: parametrage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.parametrage (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    code character varying(25),
    valeur character varying(100)
);

--
-- Name: statut; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.statut (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    code character varying(25),
    libelle character varying(100)
);

--
-- Name: agent; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.agent (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    prenom character varying(100),
    nom character varying(50),
    adresse character varying(100),
    telephone character varying(50),
    codepin integer,
    urlimageprofil character varying(250),
    email character varying(50),
    urlimagesignature character varying(250)
);

--
-- Name: agent agent_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.agent
    ADD CONSTRAINT agent_pkey PRIMARY KEY (id);

--
-- Name: agent paiement_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.detailsPaiement
    ADD CONSTRAINT detailsPaiement._pkey PRIMARY KEY (id);

--
-- Name: parametrage parametrage_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.parametrage
    ADD CONSTRAINT parametrage_pkey PRIMARY KEY (id);

--
-- Name: statut statut_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.statut
    ADD CONSTRAINT statut_pkey PRIMARY KEY (id);

--
-- Name: convention convention_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT convention_pkey PRIMARY KEY (id);

--
-- Name: observation observation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.observation
    ADD CONSTRAINT observation_pkey PRIMARY KEY (id);

--
-- Name: paiement paiement_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT paiement_pkey PRIMARY KEY (id);

--
-- Name: demande demande_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demande
    ADD CONSTRAINT demande_pkey PRIMARY KEY (id);

--
-- Name: pme pme_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pme
    ADD CONSTRAINT pme_pkey PRIMARY KEY (id);

--
-- Name: bonengagement bonengagement_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.bonengagement
    ADD CONSTRAINT bonengagement_pkey PRIMARY KEY (id);


--
-- Name: demande fk_demande_pme; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demande
    ADD CONSTRAINT fk_demande_pme FOREIGN KEY (pmeid) REFERENCES public.pme(id);

--
-- Name: demande fk_demande_convention; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demande
    ADD CONSTRAINT fk_demande_convention FOREIGN KEY (conventionid) REFERENCES public.convention(id);

--
-- Name: demande fk_demande_bonengagement; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demande
    ADD CONSTRAINT fk_demande_bonengagement FOREIGN KEY (bonengagementid) REFERENCES public.bonengagement(id);

--
-- Name: detailsPaiement fk_detailsPaiement_paiement; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.detailsPaiement
    ADD CONSTRAINT fk_detailsPaiement_paiement FOREIGN KEY (paiementid) REFERENCES public.paiement(id);
--
-- Name: demande fk_demande_statut; Type: FK CONSTRAINT; Schema: public; Owner: -
--
ALTER TABLE ONLY public.demande
    ADD CONSTRAINT fk_demande_statut FOREIGN KEY (statutid) REFERENCES public.statut(id);

--
-- Name: convention fk_convention_pme; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_convention_pme FOREIGN KEY (pmeid) REFERENCES public.pme(id);

--
-- Name: convention fk_convention_agent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_convention_agent FOREIGN KEY (agentid) REFERENCES public.agent(id);

--
-- Name: paiement fk_paiement_demande; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT fk_paiement_demande FOREIGN KEY (demandeid) REFERENCES public.demande(id);


ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT fk_paiement_statut FOREIGN KEY (statutid) REFERENCES public.statut(id);
--
-- Name: observation fk_observation_agent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.observation
    ADD CONSTRAINT fk_observation_agent FOREIGN KEY (agentid) REFERENCES public.agent(id);

--
-- Name: observation fk_observation_demande; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.observation
    ADD CONSTRAINT fk_observation_demande FOREIGN KEY (demandeid) REFERENCES public.demande(id);
