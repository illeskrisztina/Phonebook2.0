package application.main.service;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.enums.AddressType;
import application.main.model.exception.NoSuchAddressTypeException;
import application.main.service.interfaces.IAddressService;
import application.main.service.interfaces.IContactService;
import application.main.service.interfaces.IDispatcher;
import application.main.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Transactional
    @Override
    public Address createAddress(int personId, Address address) {
        Address created = addressService.createAddress(address);

        SimplePersonDTO simplePersonDTO = getPerson(personId);

        switch (address.getType()) {
            case AddressType.PERMANENT ->
                personService.updatePerson(new Person()
                        .setId(simplePersonDTO.getId())
                        .setName(simplePersonDTO.getName())
                        .setAge(simplePersonDTO.getAge())
                        .setPermanentAddress(address));
            case AddressType.TEMPORARY ->
                    personService.updatePerson(new Person()
                            .setId(simplePersonDTO.getId())
                            .setName(simplePersonDTO.getName())
                            .setAge(simplePersonDTO.getAge())
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

    @Override
    public Address deleteAddress(int id) {
        return addressService.deleteAddress(id);
    }

    @Transactional
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

        for (int i = 0; i < address.getContacts().size(); i++) {
            if (address.getContacts().get(i) != null) {
                contacts.add(address.getContacts().get(i));
            }
        }

        return contacts;
    }

    @Override
    public ContactInfo deleteContact(String contact) {
        return contactService.deleteContact(contact);
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
        return personService.getAllPeople();
    }

    @Override
    public Person updatePerson(Person person) {
        return personService.updatePerson(person);
    }

    @Override
    public Person deletePerson(int id) {
        Person person = new Person();

        getAllAddress(id).forEach(address ->
        {
            addressService.deleteAddress(address.getId());

            getAllContacts(address.getId()).stream().map(contactInfo ->
            contactService.deleteContact(contactInfo.getContact())
            );

            switch (address.getType()){
                case AddressType.PERMANENT ->
                    person.setPermanentAddress(address);
                case AddressType.TEMPORARY ->
                    person.setTemporaryAddress(address);
                default ->
                    throw new NoSuchAddressTypeException("The address type " + address.getType() + " does not exist");
            }
        });

        Person deleted = personService.deletePerson(id);

        person.setName(deleted.getName())
                .setAge(deleted.getAge())
                .setId(deleted.getId());

        return person;
    }
}
