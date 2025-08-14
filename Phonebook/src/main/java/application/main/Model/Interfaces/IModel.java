package application.main.Model.Interfaces;

import application.main.Entities.Address;
import application.main.Entities.ContactInfo;
import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;

import java.util.List;

public interface IModel {
    public Address createAddress(int personId, Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress(Integer personId);

    public Address updateAddress(Address address);

    public Address deleteAddress(int id);

    public ContactInfo addContact(ContactInfo contact, int addressId);

    public ContactInfo getContact(String contact);

    public List<ContactInfo> getAllContacts(Integer addressId);

    public ContactInfo deleteContact(String contact, int addressId);

    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int Id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int Id);
}
