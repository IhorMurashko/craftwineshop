insert  into produced_country (id, name)
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
