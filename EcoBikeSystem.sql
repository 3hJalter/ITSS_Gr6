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
6	Bike 6	20	2	5	https://lectricebikes.com/cdn/shop/products/LectriceBikes-BXPremium-CargoPack_1445x.jpg?v=1690861096	406f0b1a-def5-40ea-a823-5984cf02f6c8
8	Bike 8	\N	1	6	https://5.imimg.com/data5/SELLER/Default/2021/9/QF/GK/HR/1715048/leader-knight-rider-for-mens-bicycle-500x500.jpg	177ea473-f21b-4a30-acbb-c3f48a790b62
11	Bike 11	\N	1	3	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFnr6VN4rlyZ-5-2QKhr9K570IBwx1qQTpwm_qXtIwHGKkKaXPG-uuYOmnSCoer-lt8bU&usqp=CAU	693b11fa-feb7-458f-870a-02f7cd8aebbd
14	Bike 14	\N	1	6	https://5.imimg.com/data5/SELLER/Default/2022/4/SI/AV/FG/53657037/pilot-ex-26t-s-bar-avon-cycle-250x250.jpg	bd990df4-5c03-47cf-a30e-76b538675dd1
17	Bike 17	\N	1	3	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUSfTP3u06r9y3cqe4Wc0ovVbL8hUVugmSvZ0vb-y4FQ-WWTRT3xwKxIdqeySyUQcOJCg&usqp=CAU	45fb7b78-8fd1-40aa-b327-172822ba47fe
19	Bike 19	\N	3	5	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbazYUrYerAAfFrk1jfJgJN-kA2rLnasQTloeUiujXBGJ2J_U69WK3qmwBWVFIcy8LAxA&usqp=CAU	bb07a2b5-0fe1-45e2-84e2-0d50c746f693
20	Bike 20	\N	1	6	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCQ90XgIT4tKrGoQtfULVe_XZSqXFiSmx6yj1dnwVIIpEdOxjKcaJFX-nwjpinKp7Rm0k&usqp=CAU	03ed9a53-908c-4ffb-b998-d734b244bdab
9	Bike 9	10	2	4	https://www.reidcycles.com.au/cdn/shop/products/reid-cycles-australia-adventure-ebike-charcoal-s-791.png?v=1620964071&width=1946	bb495160-bcdf-4e72-93a9-e8fde282ae20
7	Bike 7	\N	3	2	https://dolan-images.s3.eu-west-2.amazonaws.com/product-images/1000-1000/TDR-Alpina-Disc-Tandem-Build-4.jpg	053a1578-f589-47ef-854b-7acb91186586
18	Bike 18	75	2	1	https://www.pngfind.com/pngs/m/121-1213897_gazelle-e-bike-no1-gazelle-electric-bike-hd.png	f93dded4-7d42-461b-8dfd-cffcec28464d
12	Bike 12	95	2	\N	https://himiwaybike.com/cdn/shop/files/es1_1200x1200.png?v=1685525178	f8385a9f-b185-4c75-bc67-9b79f6964458
21	Bike 21	100	4	1	https://w7.pngwing.com/pngs/905/562/png-transparent-cannondale-drapac-tandem-bicycle-cannondale-bicycle-corporation-29er-tandem-bicycle-bicycle-frame-bicycle-racing-bicycle.png	f94832e4-ff3c-467b-9d84-39256f50b1b7
15	Bike 15	60	2	2	https://www.nicepng.com/png/detail/257-2572307_wallerang-m-01-electric-bike-wallerang-e-bikes.png	dddbf733-bae0-47d6-94dd-0348c31147b2
100	test_bike	100	1	4	https://w7.pngwing.com/pngs/778/812/png-transparent-giant-bicycles-mountain-bike-cross-country-cycling-bicycle-sport-bicycle-frame-bicycle.png	fc74422c-ea36-4565-aa0b-d78d810d7037
1	Bike 1	\N	1	1	https://www.sefiles.net/merchant/5282/images/zoom/SolarOrange2.png	aac26076-7c60-40a5-a2eb-dfd808dc2574
13	Bike 13	\N	3	5	https://d2gbn3pgimi594.cloudfront.net/image/product-image/978/289978~800x800.jpg	f67f5818-713c-4d24-86f2-7f7f74a83d5c
10	Bike 10	\N	3	1	https://cdn-amz.woka.io/images/I/81YFO5Wz16L.jpg	dc448dda-2327-4d2a-84e5-b4447a9d1837
16	Bike 16	\N	3	\N	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1mikVAh8F-vw2YjOM1WaGLlQxpDSRbrJm8A&usqp=CAU	ce76d1f3-9757-469d-9cd4-ee0d6196708a
2	Bike 2	\N	1	3	https://mediacloud.theweek.co.uk/image/private/s--sUP83Hex--/f_auto,t_slideshow-image-mobile@1/v1622541616/theweek/2021/05/VanMoof.jpg	087f4a61-0c56-456e-b1f7-51bd8b4f6c65
3	Bike 3	90	2	3	https://blixbike.com/cdn/shop/products/Blix_Dubbel_SlateGrey_WheelGuards_1200x.png?v=1680733368	1d559854-9382-425e-84ed-d6e770f9a470
4	Bike 4	\N	3	\N	https://w7.pngwing.com/pngs/687/765/png-transparent-electric-bicycle-tandem-bicycle-cycling-electricity-rise-flyer-bicycle-frame-bicycle-hybrid-bicycle.png	10f4af38-0c04-4418-8c65-c62fc9305b78
5	Bike 5	\N	1	\N	https://www.reidcycles.com.au/cdn/shop/products/reid-cycles-australia-adventure-ebike-charcoal-s-791.png?v=1620964071&width=1946	7bc4e9c6-03fe-46ef-858e-710bf0016d10
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
2	hiep
3	huy
\.


--
-- Data for Name: dock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dock (dock_id, dock_name, address, image) FROM stdin;
1	DCV	1 Dai Co Viet	https://upload.wikimedia.org/wikipedia/commons/4/4a/K1_Bike_Dock_by_Park_A_Bike.jpg
2	TDN	3 Tran Dai Nghia	https://air-fom.com/wp-content/uploads/2019/07/Electric-Bike-Share-Systems_1920.jpg
3	NVC	5 Nguyen Van Cu	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9-QuGnumnmMzto0Zo1f_tWDzGzQ_Zr2VXadHJn6aQAMeBA7dc1yLimiShj4__wGgycYU&usqp=CAU
4	HHT	7 Hoang Hoa Tham	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHsYZE5dGYOHtqvGkiTWl8GXs7iIg-04mKtIbArgEKCmUgu26SZOiInH4IWQxE0xI3RmU&usqp=CAU
6	THD	11 Tran Hung Dao	https://images.ctfassets.net/vz6nkkbc6q75/5TxuychD5WxkvcBtMxLP6W/70391165eac5b3ce22ac97ae9334bf68/Pillar_PosterImage_01.jpg?h=1000
5	LD	9 Le Duan	https://media.istockphoto.com/id/1220766692/photo/santander-cycles-bikes-in-a-row-at-docking-station-of-aquatic-centre-queen-elizabeth-olympic.jpg?s=1024x1024&w=is&k=20&c=_yUFBS6Xf3Dd23BCKVSNzXZkPLFWkfk6V06HLeEPNPs=
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
14	3	19	2023-08-13 18:12:24.647789	2023-08-13 22:18:51.185761	658000	100
15	3	20	2023-08-13 22:19:12.624122	2023-08-13 23:20:12.735341	136500	18
16	3	21	2023-08-13 23:58:24.079219	2023-08-13 23:58:39.663454	0	10
17	3	22	2023-08-14 00:00:46.051599	2023-08-14 12:42:28.81265	325500	9
18	3	23	2023-08-14 12:43:35.526453	2023-08-14 13:16:04.730214	24000	18
19	3	24	2023-08-14 13:19:54.630376	2023-08-14 13:20:09.210354	80000	10
20	3	25	2023-08-14 13:37:18.242144	2023-08-14 13:40:59.590784	80334	10
21	3	26	2023-08-14 13:47:14.132896	2023-08-14 14:16:41.898141	15000	9
22	3	27	2023-08-14 14:46:56.551477	2023-08-14 14:47:01.303897	0	10
23	3	28	2023-08-14 14:50:23.740718	2023-08-14 14:50:31.141177	80000	7
24	3	30	2023-08-19 22:39:14.902501	2023-08-19 22:45:56.88559	0	9
25	3	31	2023-08-19 23:07:08.239176	2023-08-19 23:07:42.309549	80000	100
26	3	33	2023-08-19 23:53:16.18628	2023-08-20 00:04:32.764618	10000	1
27	3	34	2023-08-20 00:05:20.017481	2023-08-20 00:28:19.656262	10000	1
28	3	35	2023-08-20 11:28:30.362919	2023-08-20 12:44:08.607922	217500	15
29	3	36	2023-08-20 12:46:16.700938	2023-08-20 13:15:16.575942	20000	21
30	3	37	2023-08-20 13:16:37.444989	2023-08-20 13:17:28.410231	0	18
31	3	38	2023-08-20 13:24:49.431127	2023-08-20 13:25:10.40878	0	15
32	3	39	2023-08-20 13:26:20.999564	2023-08-20 13:26:46.044912	0	7
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
30	3	2023-08-19 22:39:14.902501	inactive	280000	9	normal	0	2023-08-19 22:39:14.902501
31	3	2023-08-19 23:07:08.239176	inactive	160000	100	24h	0	2023-08-19 23:07:08.239176
33	3	2023-08-19 23:53:16.18628	inactive	160000	1	normal	0	2023-08-19 23:53:16.18628
15	1	2023-08-13 16:05:35.56955	paused	220000	4	normal	93	2023-08-13 17:39:11.058626
34	3	2023-08-20 00:05:20.017481	inactive	160000	1	normal	0	2023-08-20 00:05:20.017481
19	3	2023-08-13 18:12:24.647789	inactive	160000	100	normal	0	2023-08-13 18:12:24.647789
20	3	2023-08-13 22:19:12.624122	inactive	280000	18	normal	57	2023-08-13 23:16:40.661288
21	3	2023-08-13 23:58:24.079219	inactive	220000	10	normal	0	2023-08-13 23:58:24.079219
35	3	2023-08-20 11:28:30.362919	inactive	280000	15	normal	18	2023-08-20 11:46:34.832852
36	3	2023-08-20 12:46:16.700938	inactive	220000	21	normal	0	2023-08-20 12:46:16.700938
37	3	2023-08-20 13:16:37.444989	inactive	280000	18	normal	0	2023-08-20 13:16:37.444989
38	3	2023-08-20 13:24:49.431127	inactive	280000	15	normal	0	2023-08-20 13:24:49.431127
39	3	2023-08-20 13:26:20.999564	inactive	220000	7	normal	0	2023-08-20 13:26:29.00036
25	3	2023-08-14 13:37:18.242144	inactive	220000	10	24h	2	2023-08-14 13:40:49.264632
22	3	2023-08-14 00:00:46.051599	inactive	280000	9	normal	99	2023-08-14 12:39:28.510432
23	3	2023-08-14 12:43:35.526453	inactive	280000	18	normal	0	2023-08-14 12:43:35.526453
24	3	2023-08-14 13:19:54.630376	inactive	220000	10	24h	0	2023-08-14 13:19:54.630376
26	3	2023-08-14 13:47:14.132896	inactive	280000	9	normal	29	2023-08-14 13:47:14.132896
27	3	2023-08-14 14:46:56.551477	inactive	220000	10	normal	0	2023-08-14 14:46:56.551477
28	3	2023-08-14 14:50:23.740718	inactive	220000	7	24h	0	2023-08-14 14:50:27.524149
\.


--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.invoice_invoice_id_seq', 32, true);


--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_transaction_id_seq', 39, true);


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

