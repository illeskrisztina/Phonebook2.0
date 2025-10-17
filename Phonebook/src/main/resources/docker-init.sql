create schema phonebook;

create table phonebook.address(
    id int identity(1,1) primary key,
    address varchar(200),
    person_id int,
    type varchar(50) check (type = 'PERMANENT' OR type = 'TEMPORARY') NOT NULL
);

create table phonebook.person(
    id INT identity(1,1) primary key,
    name varchar(120) NOT NULL,
    permanent_id int,
    temporary_id int,
    age int,
    FOREIGN KEY (permanent_id) REFERENCES phonebook.address(id),
    FOREIGN KEY (temporary_id) REFERENCES phonebook.address(id)
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