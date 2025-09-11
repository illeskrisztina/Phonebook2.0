create schema if not exists phonebook;

create table if not exists phonebook.address(
    id int primary key AUTO_INCREMENT,
    address varchar(200),
    type varchar(50) check (type = 'permanent' OR type = 'temporary') NOT NULL
);

create table if not exists phonebook.person(
    id INT primary key AUTO_INCREMENT,
    name varchar(120) NOT NULL,
    permanent_id int,
    temporary_id int,
    age int,
    FOREIGN KEY (permanent_id) REFERENCES phonebook.address(id),
    FOREIGN KEY (temporary_id) REFERENCES phonebook.address(id)
);



create table if not exists phonebook.contact_info(
    type varchar(120) NOT NULL,
    contact varchar(200) PRIMARY KEY
);

create table if not exists phonebook.address_contacts(
    contact varchar(200),
    address_id int NOT NULL,
    PRIMARY KEY (contact, address_id),
    FOREIGN KEY (contact) references phonebook.contact_info(contact),
    FOREIGN KEY (address_id) references phonebook.address(id)
);