insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id,  alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
VALUES ('false', 'false', 'false', 456.22, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '4428', 'RED', 'some description', 'wine name', 'winemaking',0);




insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
VALUES ('true', 'true', 'false', 85.7, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '1111', 'RED', 'some description', 'first wine', 'winemaking',0);


insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
VALUES ('true', 'false', 'false', 30, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '2222', 'RED', 'some description', 'second wine', 'winemaking',0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
VALUES ('true', 'false', 'false', 188, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '3333', 'RED', 'some description', 'third wine', 'winemaking',0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking,discount)
VALUES ('false', 'true', 'false', 6942.00, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '4444', 'RED', 'some description', 'fourth wine', 'winemaking',0);