create schema phonebook;

create table phonebook.person(
    id identity primary key,
    name varchar(120) NOT NULL,
    permanent_id int,
    temporary_id int,
    age int,
    FOREIGN KEY (permanent_id) REFERENCES phonebook.address(id),
    FOREIGN KEY (address_id) REFERENCES phonebook.address(id)
);

create table phonebook.address(
    id identity primary key,
    address varchar(200) NOT NULL,
    person_id int NOT NULL,
    type varchar(50) check (type = 'permanent' OR type = 'temporary') NOT NULL,
    FOREIGN KEY (person_id) references phonebook.person(id)
);

create table phonebook.contact_info(
    type varchar(120) NOT NULL,
    contact varchar(200) PRIMARY KEY
);

create table phonebook.address_contacts(
    contact varchar(200),
    address_id int NOT NULL,
    PRIMARY KEY (contact, address_id),
    FOREIGN KEY (contact) references phonebook.contact_info(contact),
    FOREIGN KEY (address_id) references phonebook.address(id)
);