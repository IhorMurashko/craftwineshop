insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Slovenia'), 'SloveniaRegion1');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Slovenia'), 'SloveniaRegion2');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Austria'), 'AustriaRegion1');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Austria'), 'AustriaRegion2');
insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Italy'), 'Piedmont');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Spain'), 'Rioja');

insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Croatia'), 'CroatiaRegion');


insert into regions (id, produced_country_id, name)
VALUES (nextval('country_region_sequence_generator'),
        (select id from produced_country where produced_country.name like 'Ireland'), 'Dublin');