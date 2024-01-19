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

insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_color, wine_description, wine_name, winemaking, discount, rate)
VALUES ('false', 'false', 'false', 456.22, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7466.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes', 'RED', 'some description',
        'wine name', 'winemaking', 0, 0);



insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_color, wine_description, wine_name, winemaking, discount, rate)
VALUES ('true', 'true', 'false', 85.7, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7467.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'first wine', 'winemaking', 0, 0);


insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_color, wine_description, wine_name, winemaking, discount, rate)
VALUES ('true', 'false', 'false', 30, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7468.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'second wine', 'winemaking', 0, 0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_color, wine_description, wine_name, winemaking, discount, rate)
VALUES ('true', 'false', 'false', 188, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7466.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'third wine', 'winemaking', 0, 0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes, wine_color, wine_description, wine_name, winemaking, discount, rate)
VALUES ('false', 'true', 'false', 6942.00, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7467.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'fourth wine', 'winemaking', 0, 0);



insert into users (enabled, locked, id, last_time_reset_password, user_cart_id,
                   delivery_address, email, first_name,
                   last_name, password, phone_number, role)
VALUES ('true', 'false', nextval('users_sequence_generator'), null, null, null, 'email1@gmail.com', 'name1',
        'lastname1', '$2a$10$NkdTE8qVk/sIhD60v2Tf9.CARyjjh7dr2TutkDeAdBQ1x5gOTKwBK',
        '012345678902', 'USER');

insert into users (enabled, locked, id, last_time_reset_password, user_cart_id,
                   delivery_address, email, first_name, last_name, password, phone_number, role)
VALUES ('true', 'false', nextval('users_sequence_generator'), null, null, null,
        'email2@gmail.com', 'name2', 'lastname2',
        '$2a$10$NkdTE8qVk/sIhD60v2Tf9.CARyjjh7dr2TutkDeAdBQ1x5gOTKwBK', '012345678903', 'USER');

insert into users (enabled, locked, id, last_time_reset_password, user_cart_id,
                   delivery_address, email, first_name, last_name, password, phone_number, role)
VALUES ('true', 'false', nextval('users_sequence_generator'), null, null, null,
        'email3@gmail.com', 'name3', 'lastname3',
        '$2a$10$NkdTE8qVk/sIhD60v2Tf9.CARyjjh7dr2TutkDeAdBQ1x5gOTKwBK', '012345678904', 'USER');