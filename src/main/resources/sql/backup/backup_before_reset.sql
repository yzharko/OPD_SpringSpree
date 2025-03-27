--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- Name: buy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.buy (
    id integer NOT NULL,
    buy_step_id integer NOT NULL,
    customer_id integer NOT NULL,
    description character varying(100)
);


ALTER TABLE public.buy OWNER TO postgres;

--
-- Name: buy_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.buy_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.buy_id_seq OWNER TO postgres;

--
-- Name: buy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.buy_id_seq OWNED BY public.buy.id;


--
-- Name: buy_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.buy_product (
    id integer NOT NULL,
    product_id integer NOT NULL,
    buy_id integer NOT NULL,
    amount integer
);


ALTER TABLE public.buy_product OWNER TO postgres;

--
-- Name: buy_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.buy_product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.buy_product_id_seq OWNER TO postgres;

--
-- Name: buy_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.buy_product_id_seq OWNED BY public.buy_product.id;


--
-- Name: buy_step; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.buy_step (
    id integer NOT NULL,
    step_id integer NOT NULL,
    date_start date,
    date_end date
);


ALTER TABLE public.buy_step OWNER TO postgres;

--
-- Name: buy_step_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.buy_step_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.buy_step_id_seq OWNER TO postgres;

--
-- Name: buy_step_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.buy_step_id_seq OWNED BY public.buy_step.id;


--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(50),
    hazard character varying(50),
    rarity character varying(50)
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_id_seq OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- Name: city; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.city (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    delivery_time time without time zone
);


ALTER TABLE public.city OWNER TO postgres;

--
-- Name: city_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.city_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.city_id_seq OWNER TO postgres;

--
-- Name: city_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.city_id_seq OWNED BY public.city.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    city_id integer NOT NULL,
    name character varying(50) NOT NULL,
    email character varying(50)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_id_seq OWNER TO postgres;

--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- Name: mythology; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mythology (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE public.mythology OWNER TO postgres;

--
-- Name: mythology_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mythology_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mythology_id_seq OWNER TO postgres;

--
-- Name: mythology_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mythology_id_seq OWNED BY public.mythology.id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id integer NOT NULL,
    category_id integer NOT NULL,
    mythology_id integer NOT NULL,
    name character varying(50),
    price numeric(8,2),
    description character varying(100),
    pic character varying(150)
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_id_seq OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: step; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.step (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(100)
);


ALTER TABLE public.step OWNER TO postgres;

--
-- Name: step_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.step_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.step_id_seq OWNER TO postgres;

--
-- Name: step_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.step_id_seq OWNED BY public.step.id;


--
-- Name: buy id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy ALTER COLUMN id SET DEFAULT nextval('public.buy_id_seq'::regclass);


--
-- Name: buy_product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_product ALTER COLUMN id SET DEFAULT nextval('public.buy_product_id_seq'::regclass);


--
-- Name: buy_step id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_step ALTER COLUMN id SET DEFAULT nextval('public.buy_step_id_seq'::regclass);


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- Name: city id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.city ALTER COLUMN id SET DEFAULT nextval('public.city_id_seq'::regclass);


--
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- Name: mythology id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mythology ALTER COLUMN id SET DEFAULT nextval('public.mythology_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Name: step id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.step ALTER COLUMN id SET DEFAULT nextval('public.step_id_seq'::regclass);


--
-- Data for Name: buy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.buy (id, buy_step_id, customer_id, description) FROM stdin;
1	1	1	Online Buy
\.


--
-- Data for Name: buy_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.buy_product (id, product_id, buy_id, amount) FROM stdin;
1	1	1	2
2	2	1	1
\.


--
-- Data for Name: buy_step; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.buy_step (id, step_id, date_start, date_end) FROM stdin;
1	1	2023-10-26	2023-10-26
2	2	2023-10-26	2023-10-26
3	3	2023-10-27	2024-01-02
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, name, hazard, rarity) FROM stdin;
1	Potion	Low	Common
2	Weapon	High	Rare
3	Artifact	Medium	Uncommon
\.


--
-- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.city (id, name, delivery_time) FROM stdin;
1	New York	01:30:00
2	London	02:00:00
3	Tokyo	03:00:00
4	Miami	06:06:06
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (id, city_id, name, email) FROM stdin;
1	1	John Doe	john.doe@example.com
2	2	Jane Smith	jane.smith@example.com
3	3	Taro Tanaka	taro.tanaka@example.jp
\.


--
-- Data for Name: mythology; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mythology (id, name) FROM stdin;
1	Greek
2	Norse
3	Egyptian
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id, category_id, mythology_id, name, price, description, pic) FROM stdin;
1	1	1	Healing Potion	10.00	Restores 50 HP	healing_potion.jpg
2	2	2	Thor Hammer	1000.00	Legendary hammer	thors_hammer.jpg
3	3	3	Amulet of Anubis	500.00	Protects from evil	anubis_amulet.jpg
\.


--
-- Data for Name: step; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.step (id, name, description) FROM stdin;
1	Buy Placed	Customer submitted the buy
2	Payment Received	Payment has been confirmed
3	Sent	Gargoyle has been sent
4	Delivered	Buy has been delivered
\.


--
-- Name: buy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.buy_id_seq', 1, true);


--
-- Name: buy_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.buy_product_id_seq', 2, true);


--
-- Name: buy_step_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.buy_step_id_seq', 3, true);


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 3, true);


--
-- Name: city_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.city_id_seq', 4, true);


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_id_seq', 3, true);


--
-- Name: mythology_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mythology_id_seq', 3, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 3, true);


--
-- Name: step_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.step_id_seq', 4, true);


--
-- Name: buy buy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy
    ADD CONSTRAINT buy_pkey PRIMARY KEY (id);


--
-- Name: buy_product buy_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_product
    ADD CONSTRAINT buy_product_pkey PRIMARY KEY (id);


--
-- Name: buy_step buy_step_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_step
    ADD CONSTRAINT buy_step_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: city city_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: mythology mythology_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mythology
    ADD CONSTRAINT mythology_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: step step_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.step
    ADD CONSTRAINT step_pkey PRIMARY KEY (id);


--
-- Name: buy buy_buy_step_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy
    ADD CONSTRAINT buy_buy_step_id_fkey FOREIGN KEY (buy_step_id) REFERENCES public.buy_step(id);


--
-- Name: buy buy_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy
    ADD CONSTRAINT buy_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- Name: buy_product buy_product_buy_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_product
    ADD CONSTRAINT buy_product_buy_id_fkey FOREIGN KEY (buy_id) REFERENCES public.buy(id);


--
-- Name: buy_product buy_product_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_product
    ADD CONSTRAINT buy_product_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: buy_step buy_step_step_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.buy_step
    ADD CONSTRAINT buy_step_step_id_fkey FOREIGN KEY (step_id) REFERENCES public.step(id);


--
-- Name: customer customer_city_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_city_id_fkey FOREIGN KEY (city_id) REFERENCES public.city(id);


--
-- Name: product product_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(id) ON DELETE CASCADE;


--
-- Name: product product_mythology_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_mythology_id_fkey FOREIGN KEY (mythology_id) REFERENCES public.mythology(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

