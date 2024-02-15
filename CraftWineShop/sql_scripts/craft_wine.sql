insert into craft_wines (is_best_seller, is_new_collection, is_sale,original_price, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id,  alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion, rate, admin_discount )
VALUES ('false', 'false', 'false', 456.22,456.22, 28, now(), 0,
        (select id from produced_country where name like 'Slovenia'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'SloveniaRegion1'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7466.jpg',
        'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes', 'RED', 'some description', 'wine name', 'winemaking',false,0,3);




insert into craft_wines (is_best_seller, is_new_collection, is_sale, original_price,price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion,rate, admin_discount)
VALUES ('true', 'true', 'false', 85.7,85.7, 28, now(), 0,
        (select id from produced_country where name like 'Austria'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'AustriaRegion1'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7467.jpg', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'first wine', 'winemaking',false,0,15);


insert into craft_wines (is_best_seller, is_new_collection, is_sale,original_price, price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion ,rate, admin_discount)
VALUES ('true', 'false', 'false', 30,30, 28, now(), 0,
        (select id from produced_country where name like 'Italy'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Piedmont'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7468.jpg', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
         'RED', 'some description', 'second wine', 'winemaking',false,0,0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, original_price,price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion,rate, admin_discount)
VALUES ('true', 'false', 'false', 188,188, 28, now(), 0,
        (select id from produced_country where name like 'Ukraine'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Kherson'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7466.jpg', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
       'RED', 'some description', 'third wine', 'winemaking',false,0,0);

insert into craft_wines (is_best_seller, is_new_collection, is_sale, original_price,price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion,rate, admin_discount)
VALUES ('false', 'true', 'false', 6942.00,6942.00, 28, now(), 0,
        (select id from produced_country where name like 'Croatia'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'CroatiaRegion'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7467.jpg', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
         'RED', 'some description', 'fourth wine', 'winemaking',false,0,0);


insert into craft_wines (is_best_seller, is_new_collection, is_sale, original_price,price, quantity, added_date_time,
                         bottles_sold_counter, country_id, id, region_id, alcohol, bottle_capacity, food_pairing,
                         grape_varieties, image_url, reviews_and_awards, store_and_serve_advices, sugar_consistency,
                         tasting_notes,  wine_color, wine_description, wine_name, winemaking,is_wine_time_promotion,rate, admin_discount)
VALUES ('false', 'true', 'false', 6942.00,6942.00, 28, now(), 0,
        (select id from produced_country where name like 'Ireland'), nextval('wine_sequence_generator'),
        (select id from regions where name like 'Dublin'), '13', '0.7', 'some food pairing', 'some grape varieties',
        '/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-7467.jpg', 'some reviews and awards', 'store and serve advices', 'SWEET', 'some testing notes',
        'RED', 'some description', 'IrelandName', 'winemaking',false,0,3.5);
