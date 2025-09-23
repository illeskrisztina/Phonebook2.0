package application.main.service.interfaces;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.AddressDTO;
import application.main.model.entity.dto.ContactInfoDTO;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDispatcher {
    AddressDTO createAddress(int personId, Address address);

    AddressDTO getAddress(int id);

    List<AddressDTO> getAllAddress(Integer personId);

    AddressDTO updateAddress(Address address);

    void deleteAddress(int id);

    ContactInfoDTO addContact(ContactInfo contact, Integer addressId);

    ContactInfoDTO getContact(String contact);

    List<ContactInfoDTO> getAllContacts(Integer addressId);

    void deleteContact(String contact);

    Person createPerson(Person person);

    SimplePersonDTO getPerson(int id);

    List<SimplePersonDTO> getAllPeople();

    Person updatePerson(Person person);

    void deletePerson(int id);
}
