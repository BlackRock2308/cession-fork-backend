-- Insertion script for parametrage_decote
INSERT INTO public.parametrage_decote(borne_inf, borne_sup,decote_value) VALUES ( 0, 1000000, 0.1);
INSERT INTO public.parametrage_decote(borne_inf, borne_sup,decote_value) VALUES (1000000 ,5000000, 0.15);
INSERT INTO public.parametrage_decote(borne_inf, borne_sup,decote_value) VALUES (5000000, 10000000, 0.2);
INSERT INTO public.parametrage_decote(borne_inf, borne_sup,decote_value) VALUES (10000000, 100000000, 0.3);
INSERT INTO public.parametrage_decote(borne_inf, borne_sup,decote_value) VALUES (100000000, 200000000, 0.35);
