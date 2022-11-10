SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT
   pg_catalog.set_config

   (
      'search_path',
      '',
      false
   );
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
SET default_tablespace = '';
   --SET default_table_access_method = heap;
   --
   -- Name: pme; Type: TABLE; Schema: public; Owner: -
   --
CREATE TABLE public.pme
   (
      id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
      prenomrepresentant character varying (100),
      nomrepresentant character varying (50),
      rccm character varying (100),
      adresse character varying (100),
      telephone character varying (50),
      centrefiscal character varying (100),
      dateimmatriculation timestamp without time zone,
      ninea character varying (15),
      raisonsocial character varying (100),
      atd boolean,
      nantissement boolean,
      interdictionbancaire boolean,
      identificationbudgetaire boolean,
      urlimageprofil character varying (250),
      email character varying (50),
      codepin integer,
      urlimagesignature character varying (250),
      representantLegal character varying (250),
      enseigne character varying (100),
      formejuridique character varying (250),
      numCNIRepresentant integer,
      localite character varying (250),
      controle int,
      activiteprincipale character varying (250),
      autorisationMinisterielle character varying (250),
      registreCommerce character varying (250),
      dateCreation timestamp without time zone,
      capitalSocial character varying (250),
      chiffresdaffaires bigint,
      effectifPermanent integer,
      nombreEtablissementSecondaires integer,
      dateDemandeAdhesion timestamp without time zone,
      nineaExistant boolean,
      isActive boolean,
      hasninea boolean,
      utilisateurid bigint
   );
CREATE TABLE public.role
(
    id      bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    libelle character varying(100)
);
--
-- Name: bonengagement; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.utilisateur (
    idutilisateur bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    adresse character varying(250),
    codepin character varying(250),
    prenom character varying(250),
    nom character varying(250),
    password character varying(250),
    update_password boolean,
    urlimagesignature character varying(250),
    telephone character varying(50),
    urlimageprofil character varying(250),
    email character varying(250)


);

CREATE TABLE public.utilisateur_roles(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    roles_id bigint,
    Utilisateur_idutilisateur bigint
);

CREATE TABLE public.bonengagement (
             id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
             reference character varying(50),
             natureprestation character varying(100),
             naturedepense character varying(100),
             objetdepense character varying(100),
             imputation character varying(100),
             datebonengagement timestamp without time zone,
             montantCreance FLOAT(40),
             identificationcomptable character varying(100),
             date_demande timestamp without time zone,
             exercice character varying(250),
             designationBeneficiaire character varying(250),
             actionDestination character varying(250),
             activiteDestination character varying(250),
             typeDepense character varying(250),
             modeReglement character varying(100),
             dateSoumissionServiceDepensier timestamp without time zone,
             nomMarche character varying(250),
             typeMarche character varying(250)

);

--
-- Name: convention; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.convention (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    dateconvention timestamp without time zone,
    valeurDecote double precision,
    decoteid bigint,
    modepaiement character varying(50),
    utilisateurid bigint,
    pmeid bigint ,
    active_convention boolean,
    urlimagesignaturedg character varying (250),
    urlimagesignaturepme character varying (250),
    demandeid bigint

);

--
-- Name: demande; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.demande (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    pmeid bigint NOT NULL,
   	statutid bigint NOT NULL,
       numeroDemande character varying (100)

);

CREATE TABLE public.demandeadhesion (
                                id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
                                pmeid bigint NOT NULL,
                                statutid bigint NOT NULL,
                                datedemandeadhesion timestamp without time zone,
                                demandeid bigint NOT NULL,
                                numeroDemande character varying (100)
);

CREATE TABLE public.demandeCession (
                                id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
                                pmeid bigint NOT NULL,
                                statutid bigint NOT NULL,
                                bonengagementid bigint,
                                datedemandecession timestamp without time zone,
                                demandeid bigint not null,
                                paiementid bigint,
                                numeroDemande character varying (100)
);

--
-- Name: paiement; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.paiement (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
     demandeCessionid bigint ,
      soldePme FLOAT (40),
      montantCreance FLOAT (40),
      statutCDMPid bigint,
      statutPmeid bigint,
      montantRecuCdmp FLOAT (40)
);


CREATE TABLE public.detailsPaiement (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    modePaiement character varying(250) ,
    datePaiement timestamp without time zone,
    comptable character varying(100),
    montant FLOAT(40),
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
    utilisateurid bigint NOT NULL,
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
    code character varying(100),
    libelle character varying(100)
);

--
-- Name: document; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.document (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    date_sauvegarde timestamp without time zone,
    nom character varying(255),
    id_provenance bigint,
    provenance character varying(50),
    url_file character varying(255),
    typeDocument character varying(50) NOT NULL
);

CREATE TABLE public.parametrage_decote (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    bordeInf bigint,
    borneSup bigint,
    decoteValue double precision
);

--
-- Name: agent agent_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--
ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (idutilisateur);

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

--
-- Name: agent paiement_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.detailsPaiement
    ADD CONSTRAINT detailsPaiement_pkey PRIMARY KEY (id);

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
-- Name: document document_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.document
    ADD CONSTRAINT document_pkey PRIMARY KEY (id);

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

ALTER TABLE ONLY public.demandeadhesion
    ADD CONSTRAINT demandeadhesion_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.demandecession
    ADD CONSTRAINT demandecession_pkey PRIMARY KEY (id);

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

ALTER TABLE ONLY public.demandeadhesion
    ADD CONSTRAINT fk_demande_pme FOREIGN KEY (pmeid) REFERENCES public.pme(id);

ALTER TABLE ONLY public.demandecession
    ADD CONSTRAINT fk_demande_pme FOREIGN KEY (pmeid) REFERENCES public.pme(id);

--
-- Name: pme fk_pme_agent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pme
    ADD CONSTRAINT fk_pme_utilisateur FOREIGN KEY (utilisateurid) REFERENCES public.utilisateur (idutilisateur);

--
-- Name: demande fk_demande_convention; Type: FK CONSTRAINT; Schema: public; Owner: -

-- Name: demande fk_demande_bonengagement; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demandecession
    ADD CONSTRAINT fk_demandecession_bonengagement FOREIGN KEY (bonengagementid) REFERENCES public.bonengagement(id);
ALTER TABLE ONLY public.demandecession ADD CONSTRAINT fk_demandecession_paiement FOREIGN KEY (paiementid) REFERENCES public.paiement (id);

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

ALTER TABLE ONLY public.demandeadhesion
    ADD CONSTRAINT fk_demandeadhesion_statut FOREIGN KEY (statutid) REFERENCES public.statut(id);

ALTER TABLE ONLY public.demandeadhesion
    ADD CONSTRAINT fk_demandecession_statut FOREIGN KEY (statutid) REFERENCES public.statut(id);

ALTER TABLE ONLY public.demandeadhesion
    ADD CONSTRAINT fk_demandeadhesion_demande FOREIGN KEY (demandeid) REFERENCES public.demande(id);

ALTER TABLE ONLY public.demandecession
    ADD CONSTRAINT fk_demandecession_demande FOREIGN KEY (demandeid) REFERENCES public.demande(id);

--
-- Name: convention fk_convention_pme; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_convention_pme FOREIGN KEY (pmeid) REFERENCES public.pme(id);

--
-- Name: convention fk_convention_agent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_convention_utilisateur FOREIGN KEY (utilisateurid) REFERENCES public.utilisateur (idutilisateur);

--

--

ALTER TABLE ONLY public.convention
    ADD CONSTRAINT fk_convention_demande FOREIGN KEY (demandeid) REFERENCES public.demandecession(id);

--
-- Name: paiement fk_paiement_demande; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT fk_paiement_demandecession FOREIGN KEY (demandeCessionid) REFERENCES public.demandecession(id);



--
-- Name: observation fk_observation_agent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.observation
    ADD CONSTRAINT fk_observation_utilisateur FOREIGN KEY (utilisateurid) REFERENCES public.utilisateur (idutilisateur);

--
-- Name: observation fk_observation_demande; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.observation
    ADD CONSTRAINT fk_observation_demandecession FOREIGN KEY (demandeid) REFERENCES public.demandecession(id);



ALTER TABLE ONLY public.utilisateur_roles
    ADD CONSTRAINT fk_utilisateur_roles_utilisateur FOREIGN KEY (Utilisateur_idutilisateur) REFERENCES public.utilisateur(idutilisateur);



ALTER TABLE ONLY public.utilisateur_roles
    ADD CONSTRAINT fk_utilisateur_roles_role FOREIGN KEY (roles_id) REFERENCES public.role(id);

CREATE TABLE public.statistiqueDemande
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    statut character varying,
    datedemandecession Date DEFAULT current_date
);

CREATE SEQUENCE public.statistiqueDemande_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.statistiqueDemande ALTER COLUMN id DROP IDENTITY IF EXISTS;

UPDATE public.statistiqueDemande SET id=nextval('public.statistiqueDemande_sequence');


