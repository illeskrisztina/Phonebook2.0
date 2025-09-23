package application.main.service.interfaces;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDispatcher {
    Address createAddress(int personId, Address address);

    Address getAddress(int id);

    List<Address> getAllAddress(Integer personId);

    Address updateAddress(Address address);

    void deleteAddress(int id);

    ContactInfo addContact(ContactInfo contact, Integer addressId);

    ContactInfo getContact(String contact);

    List<ContactInfo> getAllContacts(Integer addressId);

    void deleteContact(String contact);

    Person createPerson(Person person);

    SimplePersonDTO getPerson(int id);

    List<SimplePersonDTO> getAllPeople();

    Person updatePerson(Person person);

    void deletePerson(int id);
}
