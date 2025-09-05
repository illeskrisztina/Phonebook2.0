package application.main.service;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.Person;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.service.interfaces.IAddressService;
import application.main.service.interfaces.IContactService;
import application.main.service.interfaces.IDispatcher;
import application.main.service.interfaces.IPersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
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
    public ContactInfo addContact(ContactInfo contact, Integer addressId) {
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
    public SimplePersonDTO getPerson(int id) {
        return personService.getPerson(id);
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
    public Person deletePerson(int id) {
        Person person = new Person();

        addressService.getAllAddress(id).forEach(address ->
        {
            addressService.deleteAddress(address.getAddressId());

            address.setContacts(new ArrayList<>(contactService.getAllContacts(address.getAddressId()).stream().map(contactInfo ->
            contactService.deleteContact(contactInfo.getContact(),  address.getAddressId())
            ).toList()));

            switch (address.getType()){
                case "permanent":
                    person.setPermanentAddressId(address.getAddressId());
                    break;
                case "temporary":
                    person.setTemporaryAddressId(address.getAddressId());
                    break;
                default:
                    throw new NoSuchElementException("The address type " + address.getType() + " does not exist");
            }
        });

        Person deleted = personService.deletePerson(id);

        person.setName(deleted.getName())
                .setAge(deleted.getAge())
                .setId(deleted.getId());

        return person;
    }
}
