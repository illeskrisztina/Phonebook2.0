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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
