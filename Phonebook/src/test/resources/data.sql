insert into phonebook.person(name, age) values ('Jane Doe', 24);
insert into phonebook.person(name, age) values ('Mary Hopkins', 67);
insert into phonebook.person(name, age) values ('David Jones', 16);


insert into phonebook.address(address, person_id, type) values ('Hungary, Budapest, 1194, Some street 13.', 1, 'permanent');
insert into phonebook.address(address, person_id, type) values ('Jozefa Kozaceka 2068/29, 960 01 Zvolen, Slovakia', 2, 'permanent');
insert into phonebook.address(address, person_id, type) values ('Hungary, Balatonboglar, Rozsa u. 37, 8630', 2, 'temporary');
insert into phonebook.address(address, person_id, type) values ('Aby Baekgardsvej 14, 8230 Aarhus, Denmark', 3, 'permanent');


insert into phonebook.contact_info(type, contact) values ('mobile phone', '+36 20 234 5678')
insert into phonebook.contact_info(type, contact) values ('mobile phone', '+36 30 555 6666')
insert into phonebook.contact_info(type, contact) values ('e-mail', 'davy_blue@gmail.com')


insert into phonebook.address_contacts(contact, address_id) values ('+36 20 234 5678', 1);
insert into phonebook.address_contacts(contact, address_id) values ('+36 30 555 6666', 2);
insert into phonebook.address_contacts(contact, address_id) values ('davy_blue@gmail.com', 4);
