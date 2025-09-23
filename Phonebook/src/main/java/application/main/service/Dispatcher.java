package application.main.service;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.exception.NoSuchAddressTypeException;
import application.main.service.interfaces.IAddressService;
import application.main.service.interfaces.IContactService;
import application.main.service.interfaces.IDispatcher;
import application.main.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Dispatcher implements IDispatcher {
    private final IPersonService personService;
    private final IAddressService addressService;
    private final IContactService contactService;

    private final PersonMapper personMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Address createAddress(int personId, Address address) {
        Address created = addressService.createAddress(address);

        switch (address.getType()) {
            case PERMANENT ->
                personService.updatePerson(
                        personService.getPerson(personId)
                                .setPermanentAddress(address));
            case TEMPORARY ->
                    personService.updatePerson(
                            personService.getPerson(personId)
                                    .setTemporaryAddress(address));
            default -> throw new NoSuchAddressTypeException(address.getType() + " does not exist.");
        }

        return created;
    }

    @Override
    public Address getAddress(int id) {
        return addressService.getAddress(id);
    }

    @Override
    public List<Address> getAllAddress(Integer personId) {
        if(personId == null) {
            return addressService.getAllAddress();
        }

        Person person = personService.getPerson(personId);

        List<Address> addresses = new ArrayList<>();

        if(person.getPermanentAddress() != null) {
            addresses.add(person.getPermanentAddress());
        }

        if(person.getTemporaryAddress() != null) {
            addresses.add(person.getTemporaryAddress());
        }

        return addresses;
    }

    @Override
    public Address updateAddress(Address address) {
        return addressService.updateAddress(address);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddress(int id) {
        getAllContacts(id).forEach(contact ->
            contactService.deleteContact(contact.getContact())
        );

        personService.getAllPeople().stream()
                .filter(person ->
                        (person.getTemporaryAddress() != null && person.getTemporaryAddress().getId() == id)
                                || (person.getPermanentAddress() != null && person.getPermanentAddress().getId() == id))
                .forEach(person -> {
                    person.setPermanentAddress(null);
                    person.setTemporaryAddress(null);
                    personService.updatePerson(person);
                });
        addressService.deleteAddress(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ContactInfo addContact(ContactInfo contact, Integer addressId) {
        ContactInfo created = contactService.addContact(contact);

        if(addressId != null) {
            Address address = addressService.getAddress(addressId);
            address.addContact(contact);

            addressService.updateAddress(address);
        }
        return created;
    }

    @Override
    public ContactInfo getContact(String contact) {
        return contactService.getContact(contact);
    }



    @Override
    public List<ContactInfo> getAllContacts(Integer addressId) {
        if(addressId == null) {
            return contactService.getAllContacts();
        }

        Address address = addressService.getAddress(addressId);

        List<ContactInfo> contacts = new ArrayList<>();

        address.getContacts().forEach(contact -> {
            if(contact != null) {
                contacts.add(contact);
            }
        });



        return contacts;
    }

    @Override
    public void deleteContact(String contact) {
        contactService.deleteContact(contact);
    }

    @Override
    public Person createPerson(Person person) {
        return personService.createPerson(person);
    }

    @Override
    public SimplePersonDTO getPerson(int id) {
        return personMapper.personToSimplePersonDTO(personService.getPerson(id));
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return personService.getAllPeople().stream().map(personMapper::personToSimplePersonDTO).toList();
    }

    @Override
    public Person updatePerson(Person person) {
        return personService.updatePerson(person);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deletePerson(int id) {
        getAllAddress(id).forEach(address ->
        {

            getAllContacts(address.getId()).forEach(contactInfo ->
            contactService.deleteContact(contactInfo.getContact())
            );

            addressService.deleteAddress(address.getId());
        });

        personService.deletePerson(id);
    }
}
