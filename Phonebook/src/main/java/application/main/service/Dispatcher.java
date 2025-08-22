package application.main.service;

import application.main.model.Entity.Address;
import application.main.model.Entity.ContactInfo;
import application.main.model.Entity.DTOs.SimplePersonDTO;
import application.main.model.Entity.Person;
import application.main.service.interfaces.IDispatcher;
import application.main.service.interfaces.IAddressService;
import application.main.service.interfaces.IContactService;
import application.main.service.interfaces.IPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Dispatcher implements IDispatcher {
    private final IPersonService personService = new PersonService();
    private final IAddressService addressService = new AddressService();
    private final IContactService contactService = new ContactService();

    @Override
    public Address createAddress(int personId, Address address) {
        return addressService.createAddress(personId, address);
    }

    @Override
    public Address getAddress(int id) {
        return addressService.getAddress(id);
    }

    @Override
    public List<Address> getAllAddress(Integer personId) {
        return addressService.getAllAddress(personId);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressService.updateAddress(address);
    }

    @Override
    public Address deleteAddress(int id) {
        return addressService.deleteAddress(id);
    }

    @Override
    public ContactInfo addContact(ContactInfo contact, int addressId) {
        return contactService.addContact(contact, addressId);
    }

    @Override
    public ContactInfo getContact(String contact) {
        return null;
    }

    @Override
    public List<ContactInfo> getAllContacts(Integer addressId) {
        return contactService.getAllContacts(addressId);
    }

    @Override
    public ContactInfo deleteContact(String contact, int addressId) {
        return contactService.deleteContact(contact, addressId);
    }

    @Override
    public Person createPerson(Person person) {
        return personService.createPerson(person);
    }

    @Override
    public SimplePersonDTO getPerson(int Id) {
        return personService.getPerson(Id);
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }

    @Override
    public Person updatePerson(Person person) {
        return personService.updatePerson(person);
    }

    @Override
    public Person deletePerson(int Id) {
        Person person = new Person();

        List<Address> addresses = addressService.getAllAddress(Id).stream().map(address ->
        {
            addressService.deleteAddress(address.getAddressId());

            address.setContacts(new ArrayList<>(contactService.getAllContacts(address.getAddressId()).stream().map(contactInfo ->
            {
                return contactService.deleteContact(contactInfo.getContact(),  address.getAddressId());
            }).collect(Collectors.toList())));

            switch (address.getType()){
                case "permanent":
                    person.setPermanentAddress(address);
                    break;
                case "temporary":
                    person.setTemporaryAddress(address);
                    break;
                default:
                    throw new NoSuchElementException("The address type " + address.getType() + " does not exist");
            }

            return address;
        }).collect(Collectors.toList());

        Person deleted = personService.deletePerson(Id);

        person.setName(deleted.getName());
        person.setAge(deleted.getAge());
        person.setId(deleted.getId());

        return person;
    }
}
