insert into produced_country (id, name)
values (nextval('produced_country_sequence_generator'), 'Ukraine');

insert into produced_country (id, name)
values (nextval('produced_country_sequence_generator'), 'Italy');

insert into produced_country (id, name)
values (nextval('produced_country_sequence_generator'), 'Spain');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ukraine'), 'Kherson');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ukraine'), 'Odessa');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Italy'), 'Veneto');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Italy'), 'Tuscany');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Italy'), 'Piedmont');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Spain'), 'Rioja');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Spain'), 'Ribera del Duero');


-- insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
--                          bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
--                          grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
--                          tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
-- VALUES ('false', 'false', 'false', 456.22, 28, now(), 5,
--         (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
--         (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
--         'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
--         '4428', 'RED', 'some description', 'wine name', 'winemaking',0);
--
--
--
--
-- insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
--                          bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
--                          grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
--                          tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
-- VALUES ('true', 'true', 'false', 456.22, 28, now(),44,
--         (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
--         (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
--         'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
--         '1111', 'RED', 'some description', 'first wine', 'winemaking',0);
--
--
-- insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
--                          bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
--                          grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
--                          tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
-- VALUES ('true', 'false', 'false', 456.22, 28, now(), 33,
--         (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
--         (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
--         'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
--         '2222', 'RED', 'some description', 'second wine', 'winemaking',0);
--
-- insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
--                          bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
--                          grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
--                          tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
-- VALUES ('true', 'false', 'false', 456.22, 28, now(), 22,
--         (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
--         (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
--         'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
--         '3333', 'RED', 'some description', 'third wine', 'winemaking',0);
--
-- insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
--                          bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
--                          grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
--                          tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
-- VALUES ('false', 'true', 'false', 456.22, 28, now(), 11,
--         (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
--         (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
--         'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
--         '4444', 'RED', 'some description', 'fourth wine', 'winemaking',0);