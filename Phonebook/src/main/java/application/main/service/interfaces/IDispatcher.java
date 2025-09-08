package application.main.service.interfaces;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDispatcher {
    public Address createAddress(int personId, Address address);

    public Address getAddress(int id);

    public List<Address> getAllAddress(Integer personId);

    public Address updateAddress(Address address);

    public Address deleteAddress(int id);

    public ContactInfo addContact(ContactInfo contact, Integer addressId);

    public ContactInfo getContact(String contact);

    public List<ContactInfo> getAllContacts(Integer addressId);

    public ContactInfo deleteContact(String contact);

    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int id);
}
