package application.main.service;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.Person;
import application.main.model.entity.dto.*;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DispatcherTest {
    @Mock
    private PersonService personService;
    @Mock
    private ContactService contactService;
    @Mock
    private AddressService addressService;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private ContactInfoMapper contactInfoMapper;
    @InjectMocks
    private Dispatcher dispatcher;
    private Person personTest;
    private Address permanentAddressTest;
    private AddressDTO permanentAddressDTOTest;
    private Address temporaryAddressTest;
    private AddressDTO temporaryAddressDTOTest;
    private ContactInfo contactInfoTest;
    private ContactInfoDTO contactInfoDTOTest;

    @BeforeEach
    void setUp() {
        personTest = new Person()
                .setAge(25)
                .setName("Melanie");
        permanentAddressTest = new Address()
                .setResidence("Budapest, 3. District")
                .setType(AddressType.PERMANENT)
                .setId(1);
        permanentAddressDTOTest = new AddressDTO()
                .setResidence("Budapest, 3. District")
                .setType(AddressType.PERMANENT)
                .setId(1);
        temporaryAddressTest = new Address()
                .setResidence("Denmark, Aarhus")
                .setType(AddressType.TEMPORARY)
                .setId(2);
        temporaryAddressDTOTest = new AddressDTO()
                .setResidence("Denmark, Aarhus")
                .setType(AddressType.TEMPORARY)
                .setId(2);
        contactInfoTest = new ContactInfo()
                .setContact("some contact")
                .setType("mail");
        contactInfoDTOTest = new ContactInfoDTO()
                .setContact("some contact")
                .setType("mail");
    }

    @Test
    void createPerson_calls_appropriate_personService_method() {
        when(personService.createPerson(any(Person.class))).thenReturn(personTest);

        dispatcher.createPerson(new Person());

        verify(personService, times(1)).createPerson(any(Person.class));
    }

    @Test
    void getPerson_calls_appropriate_personService_method() {
        when(personService.getPerson(1)).thenReturn(personTest);

        dispatcher.getPerson(1);

        verify(personService, times(1)).getPerson(1);
    }

    @Test
    void getAllPeople_calls_appropriate_personService_method() {
        when(personService.getAllPeople()).thenReturn(List.of(personTest));

        dispatcher.getAllPeople();

        verify(personService, times(1)).getAllPeople();
    }

    @Test
    void updatePerson_calls_appropriate_personService_method() {
        when(personService.updatePerson(personTest)).thenReturn(personTest);

        dispatcher.updatePerson(personTest);

        verify(personService, times(1)).updatePerson(personTest);
    }

    @Test
    void deletePerson_calls_all_service_delete_methods() {
        //Ensure they form a cohesive entity
        permanentAddressTest.addContact(contactInfoTest);
        temporaryAddressTest.addContact(contactInfoTest);
        personTest.setPermanentAddress(permanentAddressTest);
        personTest.setTemporaryAddress(temporaryAddressTest);

        lenient().when(addressService.getAllAddress()).thenReturn(List.of(permanentAddressTest, temporaryAddressTest));
        lenient().when(personService.getPerson(1)).thenReturn(personTest);
        lenient().when(contactService.getAllContacts()).thenReturn(List.of(contactInfoTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);
        lenient().when(addressMapper.addressToAddressDTO(permanentAddressTest)).thenReturn(permanentAddressDTOTest);
        lenient().when(addressMapper.addressToAddressDTO(temporaryAddressTest)).thenReturn(temporaryAddressDTOTest);
        lenient().when(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest)).thenReturn(contactInfoDTOTest);

        dispatcher.deletePerson(1);

        verify(personService, times(1)).deletePerson(1);
        verify(addressService).deleteAddress(1);
        verify(addressService).deleteAddress(2);
        verify(contactService, times(2)).deleteContact("some contact");
    }

    @Test
    void creating_address_sets_person_address_() {
        personTest.setId(1);

        lenient().when(personService.getPerson(1)).thenReturn(personTest);
        lenient().when(addressService.createAddress(permanentAddressTest)).thenReturn(permanentAddressTest.setId(1));
        lenient().when(addressService.createAddress(temporaryAddressTest)).thenReturn(temporaryAddressTest.setId(2));
        lenient().when(personMapper.personToSimplePersonDTO(personTest)).thenReturn(
                new SimplePersonDTO()
                        .setId(1)
                        .setName(personTest.getName())
                        .setAge(personTest.getAge())
        );

        dispatcher.createAddress(1, permanentAddressTest);
        dispatcher.createAddress(1, temporaryAddressTest);

        verify(personService, times(2)).updatePerson(personTest);
    }

    @Test
    void getAddress_calls_appropriate_service_method() {
        when(addressService.getAddress(1)).thenReturn(permanentAddressTest);

        dispatcher.getAddress(1);

        verify(addressService, times(1)).getAddress(1);
    }

    @Test
    void if_no_id_is_given_all_addresses_are_called() {
        when(addressService.getAllAddress()).thenReturn(List.of(permanentAddressTest, temporaryAddressTest));

        dispatcher.getAllAddress(null);

        verify(addressService, times(1)).getAllAddress();
    }

    @Test
    void person_with_both_addresses_returns_list_of_size_two() {
        when(personService.getPerson(1))
                .thenReturn(personTest
                        .setTemporaryAddress(temporaryAddressTest)
                        .setPermanentAddress(permanentAddressTest));

        List<AddressDTO> addresses = dispatcher.getAllAddress(1);

        Assertions.assertEquals(2, addresses.size());
    }

    @Test
    void updateAddress_calls_appropriate_service_method() {
        when(addressService.updateAddress(temporaryAddressTest)).thenReturn(temporaryAddressTest);

        dispatcher.updateAddress(temporaryAddressTest);

        verify(addressService, times(1)).updateAddress(temporaryAddressTest);
    }

    @Test
    void deleting_address_updates_person_addresses() {
        personTest.setTemporaryAddress(temporaryAddressTest);
        personTest.setPermanentAddress(permanentAddressTest);

        lenient().when(personService.getAllPeople()).thenReturn(List.of(personTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);

        dispatcher.deleteAddress(1);
        dispatcher.deleteAddress(2);

        verify(personService, times(2)).updatePerson(personTest);
    }

    @Test
    void deleting_address_modifies_right_person() {
        personTest.setTemporaryAddress(temporaryAddressTest);
        personTest.setPermanentAddress(permanentAddressTest);
        Person personTest2 = new Person()
                .setName("Melinoe")
                .setAge(35)
                .setId(2);

        lenient().when(personService.getAllPeople()).thenReturn(List.of(personTest2, personTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);

        dispatcher.deleteAddress(1);
        dispatcher.deleteAddress(2);

        verify(personService, times(2)).updatePerson(personTest);
        verify(personService, times(0)).updatePerson(personTest2);
    }

    @Test
    void if_addressId_is_not_null_contact_gets_added_to_specific_address() {
        permanentAddressTest.addContact(contactInfoTest);
        when(contactService.addContact(contactInfoTest)).thenReturn(contactInfoTest);
        when(addressService.getAddress(1)).thenReturn(permanentAddressTest);

        ContactInfoDTO saved = dispatcher.addContact(contactInfoTest, 1);

        verify(contactService,  times(1)).addContact(contactInfoTest);
        verify(addressService, times(1)).updateAddress(permanentAddressTest);
        Assertions.assertEquals(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest), saved);
    }

    @Test
    void null_addressId_only_adds_contact_to_database() {
        when(contactService.addContact(contactInfoTest)).thenReturn(contactInfoTest);

        ContactInfoDTO saved = dispatcher.addContact(contactInfoTest, null);

        verify(contactService,  times(1)).addContact(contactInfoTest);
        verify(addressService, times(0)).updateAddress(permanentAddressTest);
        Assertions.assertEquals(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest), saved);
    }

    @Test
    void getContact_calls_appropriate_service_method() {
        when(contactService.getContact(contactInfoTest.getContact())).thenReturn(contactInfoTest);
        when(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest))
                .thenReturn(new ContactInfoDTO()
                        .setContact(contactInfoTest.getContact())
                        .setType(contactInfoTest.getType()));

        ContactInfoDTO contact = dispatcher.getContact(contactInfoTest.getContact());

        verify(contactService, times(1)).getContact(contactInfoTest.getContact());
        Assertions.assertEquals(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest), contact);
    }

    @Test
    void getting_all_contacts_for_no_specific_address_returns_all_contacts() {
        when(contactService.getAllContacts()).thenReturn(List.of(contactInfoTest));
        when(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest)).thenReturn(contactInfoDTOTest);

        List<ContactInfoDTO> contacts = dispatcher.getAllContacts(null);

        verify(contactService, times(1)).getAllContacts();
        Assertions.assertEquals(List.of(contactInfoDTOTest), contacts);
    }

    @Test
    void getting_all_contacts_gets_specific_address_first() {
        permanentAddressTest.addContact(contactInfoTest);
        when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        when(contactInfoMapper.contactInfoToContactInfoDTO(contactInfoTest)).thenReturn(contactInfoDTOTest);

        List<ContactInfoDTO> contacts = dispatcher.getAllContacts(1);

        verify(addressService, times(1)).getAddress(1);
        verify(contactService, times(0)).getAllContacts();
        Assertions.assertEquals(List.of(contactInfoDTOTest), contacts);
    }

    @Test
    void deleteContact_calls_appropriate_service_method() {
        dispatcher.deleteContact(contactInfoTest.getContact());

        verify(contactService, times(1)).deleteContact(contactInfoTest.getContact());
    }
}
