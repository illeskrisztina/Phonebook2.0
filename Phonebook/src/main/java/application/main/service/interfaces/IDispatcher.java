package application.main.service.interfaces;

import application.main.model.Entity.Address;
import application.main.model.Entity.ContactInfo;
import application.main.model.Entity.DTOs.SimplePersonDTO;
import application.main.model.Entity.Person;

import java.util.List;

public interface IDispatcher {
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
