insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Ukraine', 50.43, 30.52, false);

insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Italy', 41.9, 12.48, false);

insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Spain', 40.4, -3.68, false);

insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Georgia', 41.68, 44.83, false);

insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Austria', 48.2, 16.37, false);

insert into produced_country (id, name, capitallat, capitallng, is_promotion_time)
values (nextval('produced_country_sequence_generator'), 'Poland', 52.25, 21.0, false);

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ukraine'), 'Odessa');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ukraine'), 'Khersone');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ukraine'), 'Zakarpattia');



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
        (select id from produced_country where produced_country.name like 'Spain'), 'Catalonia');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Spain'), 'Balearics');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Spain'), 'Levante');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Georgia'), 'Kakheti');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Georgia'), 'Kartli');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Austria'), 'Weinland');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Austria'), 'Steirerland');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Poland'), 'Zielona Góra ');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Ukraine'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Khersone'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'khersone wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Ukraine'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Odessa'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Odessa wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Italy'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Veneto'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Veneto wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Georgia'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Kartli'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Kartli wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Georgia'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Kakheti'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Kakheti wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Austria'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Weinland'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Weinland wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Austria'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Steirerland'), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Steirerland wine', 'wine making');

insert into craft_wines(admin_discount_percentage, evaluation, is_best_seller, is_new_collection, is_sale,
                        is_wine_time_promotion, original_price, price, quantity, added_date_time, bottles_sold_counter,
                        country_id, id, region_id, alcohol, bottle_capacity, food_pairing, grape_varieties, image_url,
                        reviews_and_awards, store_and_serve_advices, sugar_consistency, tasting_notes, wine_color,
                        wine_description, wine_name, winemaking)
VALUES (0, 0, 'false', 'false', 'false', 'false', 100.15, 100.15, 12, now(), 0,
        (select id from produced_country where name like 'Poland'),
        nextval('wine_sequence_generator'), (select id from regions where name like 'Zielona Góra '), '12', '0.7',
        'food_pairing',
        'grape varieties', 'image url', 'reviews and awards', 'store and serve advices', 'DRY', 'testing notes', 'RED',
        'wine description', 'Zielona Góra  wine', 'wine making');



WITH inserted_user_cart AS (
    INSERT INTO user_cart (id, user_id)
        VALUES (nextval('user_cart_sequence_generator'), 1)
        RETURNING id)
INSERT
INTO users (enabled, locked, id, last_time_reset_password, user_cart_id, delivery_address, email,
            first_name, last_name, password, phone_number, role)
SELECT 'true',
       'false',
       nextval('users_sequence_generator'),
       null,
       ic.id,
       null,
       'this@email.com',
       'this first name',
       'this last name',
       '$2a$10$/yND19BQfv3C5bhAkAd9POhg.i0yEF8AIfCMGYFMWvl11ZFEj3e2S',
       '012345678902',
       'USER'
FROM inserted_user_cart AS ic;


WITH inserted_user_cart AS (
    INSERT INTO user_cart (id, user_id)
        VALUES (nextval('user_cart_sequence_generator'), 2)
        RETURNING id)
INSERT
INTO users (enabled, locked, id, last_time_reset_password, user_cart_id, delivery_address, email,
            first_name, last_name, password, phone_number, role)
SELECT 'true',
       'false',
       nextval('users_sequence_generator'),
       null,
       ic.id,
       null,
       'that@email.com',
       'that first name',
       'that last name',
       '$2a$10$reg2u8YPcvv5GPaAE6nfLemJS0bKkko4udfb.n2hZyMKmS3CvVVX6',
       '012345678901',
       'USER'
FROM inserted_user_cart AS ic;

insert into confirmation_token (confirmed_at, created_at, expires_at, id, user_id, token)
values ('2024-02-25 20:20:30.350012', '2024-02-25 20:18:30.350012', '2024-02-26 20:18:30.350030',
        nextval('confirmation_token_sequence'), 1, 'd0e2cf6d-b4c7-4dd3-ae95-5678ea6de34e');

insert into confirmation_token (confirmed_at, created_at, expires_at, id, user_id, token)
values ('2024-02-25 20:20:30.350012', '2024-02-25 20:19:30.350012', '2024-02-26 20:19:30.350030',
        nextval('confirmation_token_sequence'), 2, 'd0e2cf6d-b4c7-4dd3-ae95-5678ea6de35e');
