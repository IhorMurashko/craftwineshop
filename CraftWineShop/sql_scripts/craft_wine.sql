insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_article, wine_color, wine_description, wine_name, winemaking)
VALUES ('false', 'false', 'false', 456.22, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        'image URL', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        '4428', 'RED', 'some description', 'wine name', 'winemaking');