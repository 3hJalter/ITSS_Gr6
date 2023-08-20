--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

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
-- Name: credit_card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_card (
    credit_card_id integer NOT NULL,
    cardholder_name character varying(255),
    card_number character varying(16),
    issuing_bank character varying(100),
    balance numeric(15,2),
    expiration_date timestamp without time zone,
    security_code character varying(4)
);


ALTER TABLE public.credit_card OWNER TO postgres;

--
-- Name: credit_card_credit_card_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_card_credit_card_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.credit_card_credit_card_id_seq OWNER TO postgres;

--
-- Name: credit_card_credit_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_card_credit_card_id_seq OWNED BY public.credit_card.credit_card_id;


--
-- Name: credit_card credit_card_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_card ALTER COLUMN credit_card_id SET DEFAULT nextval('public.credit_card_credit_card_id_seq'::regclass);


--
-- Data for Name: credit_card; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit_card (credit_card_id, cardholder_name, card_number, issuing_bank, balance, expiration_date, security_code) FROM stdin;
3	Alice Johnson	5678901234567890	Bank C	999999999.25	2023-09-01 00:00:00	789
2	Jane Smith	9876543210987654	Bank B	999579999.50	2024-06-01 00:00:00	456
1	John Doe	1234567890123456	Bank A	996689999.00	2023-12-01 00:00:00	123
\.


--
-- Name: credit_card_credit_card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_card_credit_card_id_seq', 3, true);


--
-- Name: credit_card credit_card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_card
    ADD CONSTRAINT credit_card_pkey PRIMARY KEY (credit_card_id);


--
-- PostgreSQL database dump complete
--

