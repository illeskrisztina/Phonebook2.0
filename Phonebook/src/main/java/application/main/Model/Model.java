package application.main.Model;

import application.main.Entities.Address;
import application.main.Entities.ContactInfo;
import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;
import application.main.Model.Interfaces.IModel;
import application.main.Services.AddressService;
import application.main.Services.ContactService;
import application.main.Services.Interfaces.IAddressService;
import application.main.Services.Interfaces.IContactService;
import application.main.Services.Interfaces.IPersonService;
import application.main.Services.PersonService;

import java.util.List;

public class Model implements IModel {
    private final IPersonService personService = new PersonService();
    private final IAddressService addressService = new AddressService();
    private final IContactService contactService = new ContactService();

    @Override
    public Address createAddress(int personId, Address address) {
        return null;
    }

    @Override
    public Address getAddress(int id) {
        return null;
    }

    @Override
    public List<Address> getAllAddress(Integer personId) {
        return List.of();
    }

    @Override
    public Address updateAddress(Address address) {
        return null;
    }

    @Override
    public Address deleteAddress(int id) {
        return null;
    }

    @Override
    public ContactInfo addContact(ContactInfo contact, int addressId) {
        return null;
    }

    @Override
    public ContactInfo getContact(String contact) {
        return null;
    }

    @Override
    public List<ContactInfo> getAllContacts(Integer addressId) {
        return List.of();
    }

    @Override
    public ContactInfo deleteContact(String contact, int addressId) {
        return null;
    }

    @Override
    public Person createPerson(Person person) {
        return null;
    }

    @Override
    public SimplePersonDTO getPerson(int Id) {
        return null;
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return List.of();
    }

    @Override
    public Person updatePerson(Person person) {
        return null;
    }

    @Override
    public Person deletePerson(int Id) {
        return null;
    }
}
