--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

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

--
-- Name: bstatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.bstatus AS ENUM (
    'rented',
    'unrented'
);


ALTER TYPE public.bstatus OWNER TO postgres;

--
-- Name: tstatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tstatus AS ENUM (
    'active',
    'inactive',
    'paused'
);


ALTER TYPE public.tstatus OWNER TO postgres;

--
-- Name: ttype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.ttype AS ENUM (
    'normal',
    '24h'
);


ALTER TYPE public.ttype OWNER TO postgres;

--
-- Name: update_battery_based_on_category(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_battery_based_on_category() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.category_id IS NOT NULL THEN
        IF (SELECT category_name FROM category WHERE category_id = NEW.category_id) = 'standard_ebike' THEN
            IF NEW.battery BETWEEN 0 AND 99 THEN
                RETURN NEW;
            ELSE
                RAISE EXCEPTION 'Battery value must be between 0 and 99 for category ''standard_ebike''';
            END IF;
        ELSE
            NEW.battery = NULL;
            RETURN NEW;
        END IF;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_battery_based_on_category() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bike; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bike (
    bike_id integer NOT NULL,
    bike_name character varying(50) NOT NULL,
    battery integer,
    category_id integer NOT NULL,
    dock_id integer,
    image character varying,
    barcode uuid
);


ALTER TABLE public.bike OWNER TO postgres;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    category_id integer NOT NULL,
    category_name character varying(50) NOT NULL,
    rent_price bigint NOT NULL,
    price_multiple double precision NOT NULL,
    bike_price bigint NOT NULL,
    deposit_rate double precision NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    username character varying(50) NOT NULL
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: dock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dock (
    dock_id integer NOT NULL,
    dock_name character varying(50) NOT NULL,
    address character varying(50) NOT NULL,
    image character varying NOT NULL
);


ALTER TABLE public.dock OWNER TO postgres;

--
-- Name: invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice (
    invoice_id integer NOT NULL,
    customer_id integer,
    transaction_id integer,
    start_rent timestamp without time zone,
    end_rent timestamp without time zone,
    price bigint,
    bike_id integer
);


ALTER TABLE public.invoice OWNER TO postgres;

--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.invoice_invoice_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoice_invoice_id_seq OWNER TO postgres;

--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.invoice_invoice_id_seq OWNED BY public.invoice.invoice_id;


--
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    transaction_id integer NOT NULL,
    customer_id integer,
    create_at timestamp without time zone,
    status public.tstatus,
    deposit bigint,
    bike_id integer,
    transaction_type public.ttype,
    minute_used integer,
    last_pause timestamp without time zone
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_transaction_id_seq OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_transaction_id_seq OWNED BY public.transaction.transaction_id;


--
-- Name: invoice invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice ALTER COLUMN invoice_id SET DEFAULT nextval('public.invoice_invoice_id_seq'::regclass);


--
-- Name: transaction transaction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction ALTER COLUMN transaction_id SET DEFAULT nextval('public.transaction_transaction_id_seq'::regclass);


--
-- Data for Name: bike; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bike (bike_id, bike_name, battery, category_id, dock_id, image, barcode) FROM stdin;
9	Bike 9	10	2	3	https://www.reidcycles.com.au/cdn/shop/products/reid-cycles-australia-adventure-ebike-charcoal-s-791.png?v=1620964071&width=1946	bb495160-bcdf-4e72-93a9-e8fde282ae20
3	Bike 3	90	2	3	https://m.media-amazon.com/images/I/71bDli2q5LL._AC_UF894,1000_QL80_.jpg	1d559854-9382-425e-84ed-d6e770f9a470
5	Bike 5	\N	1	4	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9027MRLtqV8jiImK_vIpOW94qjAA0TId9MA&usqp=CAU	7bc4e9c6-03fe-46ef-858e-710bf0016d10
6	Bike 6	20	2	5	https://lectricebikes.com/cdn/shop/products/LectriceBikes-BXPremium-CargoPack_1445x.jpg?v=1690861096	406f0b1a-def5-40ea-a823-5984cf02f6c8
7	Bike 7	\N	3	3	data:image/jpeg;base64,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	053a1578-f589-47ef-854b-7acb91186586
8	Bike 8	\N	1	6	https://5.imimg.com/data5/SELLER/Default/2021/9/QF/GK/HR/1715048/leader-knight-rider-for-mens-bicycle-500x500.jpg	177ea473-f21b-4a30-acbb-c3f48a790b62
1	Bike 1	\N	1	3	https://www.sefiles.net/merchant/5282/images/zoom/SolarOrange2.png	aac26076-7c60-40a5-a2eb-dfd808dc2574
10	Bike 10	\N	3	2	https://cdn-amz.woka.io/images/I/81YFO5Wz16L.jpg	dc448dda-2327-4d2a-84e5-b4447a9d1837
11	Bike 11	\N	1	3	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFnr6VN4rlyZ-5-2QKhr9K570IBwx1qQTpwm_qXtIwHGKkKaXPG-uuYOmnSCoer-lt8bU&usqp=CAU	693b11fa-feb7-458f-870a-02f7cd8aebbd
12	Bike 12	95	2	4	https://himiwaybike.com/cdn/shop/files/es1_1200x1200.png?v=1685525178	f8385a9f-b185-4c75-bc67-9b79f6964458
13	Bike 13	\N	3	5	data:image/jpeg;base64,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	f67f5818-713c-4d24-86f2-7f7f74a83d5c
14	Bike 14	\N	1	6	https://5.imimg.com/data5/SELLER/Default/2022/4/SI/AV/FG/53657037/pilot-ex-26t-s-bar-avon-cycle-250x250.jpg	bd990df4-5c03-47cf-a30e-76b538675dd1
16	Bike 16	\N	3	2	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1mikVAh8F-vw2YjOM1WaGLlQxpDSRbrJm8A&usqp=CAU	ce76d1f3-9757-469d-9cd4-ee0d6196708a
17	Bike 17	\N	1	3	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUSfTP3u06r9y3cqe4Wc0ovVbL8hUVugmSvZ0vb-y4FQ-WWTRT3xwKxIdqeySyUQcOJCg&usqp=CAU	45fb7b78-8fd1-40aa-b327-172822ba47fe
18	Bike 18	75	2	4	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaHKrpRDw1mieTg-RoyH4d10V3Zbl4MgAG3w&usqp=CAU	f93dded4-7d42-461b-8dfd-cffcec28464d
19	Bike 19	\N	3	5	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbazYUrYerAAfFrk1jfJgJN-kA2rLnasQTloeUiujXBGJ2J_U69WK3qmwBWVFIcy8LAxA&usqp=CAU	bb07a2b5-0fe1-45e2-84e2-0d50c746f693
20	Bike 20	\N	1	6	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCQ90XgIT4tKrGoQtfULVe_XZSqXFiSmx6yj1dnwVIIpEdOxjKcaJFX-nwjpinKp7Rm0k&usqp=CAU	03ed9a53-908c-4ffb-b998-d734b244bdab
2	Bike 2	\N	1	3	https://xedapmartin107.vn/images/products/large/xe-dap-mini-martin-mt-6001-bac.jpg	087f4a61-0c56-456e-b1f7-51bd8b4f6c65
15	Bike 15	60	2	3	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTo4Otgl8zHnqL_j8s9d00_VcQZZ6BdzV2uGw&usqp=CAU	dddbf733-bae0-47d6-94dd-0348c31147b2
21	Bike 21	100	4	3	https://www.vanraam.com/getmedia/881b70d4-671a-4084-98a6-eef68dedd052/Duofiets-20met-20trapondersteuning-20-20Van-20Raam.jpg?width=550	f94832e4-ff3c-467b-9d84-39256f50b1b7
4	Bike 4	\N	3	\N	https://co-motion.com/cdn/shop/products/2017_Carrera_1600_2048x.jpg?v=1600191735	10f4af38-0c04-4418-8c65-c62fc9305b78
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (category_id, category_name, rent_price, price_multiple, bike_price, deposit_rate) FROM stdin;
1	standard_bike	3000	1	400000	0.4
2	standard_ebike	3000	1.5	700000	0.4
3	twin_bike	3000	1.5	550000	0.4
4	twin_ebike	3000	2	550000	0.4
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (customer_id, username) FROM stdin;
1	admin
\.


--
-- Data for Name: dock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dock (dock_id, dock_name, address, image) FROM stdin;
1	Ecobike 1	1 Dai Co Viet	https://www.wdef.com/content/uploads/2023/07/h/r/ebike.jpeg
2	Ecobike 2	3 Tran Dai Nghia	https://c8.alamy.com/comp/TWPXF8/los-angeles-california-june-18-2019-a-metro-bike-station-in-pershing-square-in-downtown-los-angeles-administered-by-the-los-angeles-county-metro-TWPXF8.jpg
3	Ecobike 3	5 Nguyen Van Cu	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9-QuGnumnmMzto0Zo1f_tWDzGzQ_Zr2VXadHJn6aQAMeBA7dc1yLimiShj4__wGgycYU&usqp=CAU
4	Ecobike 4	7 Hoang Hoa Tham	https://i0.wp.com/news.utk.edu/wp-content/uploads/sites/3/ebike.jpg?ssl=1
5	Ecobike 5	9 Le Duan	https://comparativegeometrics.files.wordpress.com/2013/10/bikes-sch-30-for-blog.jpg?w=584
6	Ecobike 6	11 Tran Hung Dao	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-BUID1lcLi3nGT-6etr7kHt3fH99Gk8ZDKHY7gAkD0lKskMEtbHZxHEeMU_ZoumJzvBI&usqp=CAU
\.


--
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.invoice (invoice_id, customer_id, transaction_id, start_rent, end_rent, price, bike_id) FROM stdin;
1	1	2	2023-08-12 11:27:47.525987	2023-08-12 11:59:37.519733	13000	2
2	1	3	2023-08-12 12:07:30.161219	2023-08-12 12:09:47.318188	0	2
3	1	4	2023-08-12 12:14:43.014517	2023-08-12 12:14:55.343538	0	2
4	1	5	2023-08-12 17:43:21.027347	2023-08-12 18:04:22.345403	10000	1
5	1	6	2023-08-12 18:04:35.770402	2023-08-12 18:04:49.665462	0	2
6	1	7	2023-08-12 19:29:06.368716	2023-08-12 19:33:52.734552	0	9
7	1	8	2023-08-12 19:36:18.445474	2023-08-12 19:36:41.202922	0	9
8	1	9	2023-08-12 19:36:51.597093	2023-08-12 19:47:05.840747	0	9
9	1	10	2023-08-12 19:47:16.524914	2023-08-12 23:03:11.284059	505000	1
10	1	11	2023-08-12 23:08:04.473775	2023-08-12 23:11:00.277134	80167	1
11	1	12	2023-08-13 15:57:16.648444	2023-08-13 15:59:54.924597	0	15
12	1	13	2023-08-13 16:01:20.259114	2023-08-13 16:01:30.726307	0	21
13	1	14	2023-08-13 16:01:49.872625	2023-08-13 16:02:40.142498	80000	21
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (transaction_id, customer_id, create_at, status, deposit, bike_id, transaction_type, minute_used, last_pause) FROM stdin;
2	1	2023-08-12 11:27:47.525987	inactive	160000	2	normal	0	2023-08-12 11:27:47.525987
3	1	2023-08-12 12:07:30.161219	inactive	160000	2	normal	0	2023-08-12 12:07:30.161219
4	1	2023-08-12 12:14:43.014517	inactive	160000	2	normal	0	2023-08-12 12:14:43.014517
5	1	2023-08-12 17:43:21.027347	inactive	160000	1	normal	0	2023-08-12 17:43:21.027347
6	1	2023-08-12 18:04:35.770402	inactive	160000	2	normal	0	2023-08-12 18:04:35.770402
7	1	2023-08-12 19:29:06.368716	inactive	280000	9	normal	0	2023-08-12 19:29:06.368716
8	1	2023-08-12 19:36:18.445474	inactive	280000	9	normal	0	2023-08-12 19:36:18.445474
9	1	2023-08-12 19:36:51.597093	inactive	280000	9	normal	0	2023-08-12 19:36:51.597093
1	1	2023-08-12 10:27:58.142517	inactive	160000	2	normal	0	2023-08-12 10:27:58.142517
10	1	2023-08-12 19:47:16.524914	inactive	160000	1	normal	0	2023-08-12 19:47:16.524914
11	1	2023-08-12 23:08:04.473775	inactive	160000	1	24h	1	2023-08-12 23:10:00.494343
12	1	2023-08-13 15:57:16.648444	inactive	280000	15	normal	0	2023-08-13 15:57:47.42539
13	1	2023-08-13 16:01:20.259114	inactive	220000	21	normal	0	2023-08-13 16:01:20.259114
14	1	2023-08-13 16:01:49.872625	inactive	220000	21	24h	0	2023-08-13 16:01:49.872625
15	1	2023-08-13 16:05:35.56955	active	220000	4	normal	0	2023-08-13 16:05:35.56955
\.


--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.invoice_invoice_id_seq', 13, true);


--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_transaction_id_seq', 15, true);


--
-- Name: bike bike_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bike
    ADD CONSTRAINT bike_pkey PRIMARY KEY (bike_id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (category_id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- Name: dock dock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dock
    ADD CONSTRAINT dock_pkey PRIMARY KEY (dock_id);


--
-- Name: invoice invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (invoice_id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id);


--
-- Name: bike bike_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bike
    ADD CONSTRAINT bike_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(category_id);


--
-- Name: bike bike_dock_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bike
    ADD CONSTRAINT bike_dock_id_fkey FOREIGN KEY (dock_id) REFERENCES public.dock(dock_id);


--
-- Name: invoice invoice_bike_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_bike_id_fkey FOREIGN KEY (bike_id) REFERENCES public.bike(bike_id);


--
-- Name: invoice invoice_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice
    ADD CONSTRAINT invoice_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: transaction transaction_bike_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_bike_id_fkey FOREIGN KEY (bike_id) REFERENCES public.bike(bike_id);


--
-- Name: transaction transaction_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- PostgreSQL database dump complete
--

