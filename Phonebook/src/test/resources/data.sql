insert into phonebook.address(address, type) values ('Hungary, Budapest, 1194, Some street 13.', 'PERMANENT');
insert into phonebook.address(address, type) values ('Jozefa Kozaceka 2068/29, 960 01 Zvolen, Slovakia', 'PERMANENT');
insert into phonebook.address(address, type) values ('Hungary, Balatonboglar, Rozsa u. 37, 8630', 'TEMPORARY');
insert into phonebook.address(address, type) values ('Aby Baekgardsvej 14, 8230 Aarhus, Denmark', 'PERMANENT');

insert into phonebook.person(name, age, permanent_id) values ('Jane Doe', 24, 1);
insert into phonebook.person(name, age, permanent_id, temporary_id) values ('Mary Hopkins', 67, 2, 3);
insert into phonebook.person(name, age, permanent_id) values ('David Jones', 16, 4);


insert into phonebook.contact_info(type, contact) values ('mobile phone', '+36 20 234 5678');
insert into phonebook.contact_info(type, contact) values ('mobile phone', '+36 30 555 6666');
insert into phonebook.contact_info(type, contact) values ('e-mail', 'davy_blue@gmail.com');


insert into phonebook.address_contacts(contact, address_id) values ('+36 20 234 5678', 1);
insert into phonebook.address_contacts(contact, address_id) values ('+36 30 555 6666', 2);
insert into phonebook.address_contacts(contact, address_id) values ('davy_blue@gmail.com', 4);
