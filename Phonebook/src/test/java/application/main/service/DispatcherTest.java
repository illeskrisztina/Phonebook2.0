package application.main.service;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.enums.AddressType;
import application.main.model.exception.NoSuchAddressTypeException;
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
    @InjectMocks
    private Dispatcher dispatcher;
    private Person personTest;
    private Address permanentAddressTest;
    private Address temporaryAddressTest;
    private ContactInfo contactInfoTest;

    @BeforeEach
    void setUp() {
        personTest = new Person()
                .setAge(25)
                .setName("Melanie");
        permanentAddressTest = new Address()
                .setResidence("Budapest, 3. District")
                .setType(AddressType.PERMANENT)
                .setId(1);
        temporaryAddressTest = new Address()
                .setResidence("Denmark, Aarhus")
                .setType(AddressType.TEMPORARY)
                .setId(2);
        contactInfoTest = new ContactInfo()
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
        //Method calls in order
        lenient().when(addressService.getAllAddress()).thenReturn(List.of(permanentAddressTest, temporaryAddressTest));
        lenient().when(personService.getPerson(1)).thenReturn(personTest);
        lenient().when(personService.deletePerson(1)).thenReturn(personTest.setId(1));
        lenient().when(contactService.getAllContacts()).thenReturn(List.of(contactInfoTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);

        dispatcher.deletePerson(1);

        verify(personService, times(1)).deletePerson(1);
        verify(addressService).deleteAddress(1);
        verify(addressService).deleteAddress(2);
        verify(contactService, times(2)).deleteContact("some contact");
    }

    @Test
    void person_deleted_is_the_person_returned() {
        //Ensure they form a cohesive entity
        permanentAddressTest.addContact(contactInfoTest);
        temporaryAddressTest.addContact(contactInfoTest);
        personTest.setPermanentAddress(permanentAddressTest);
        personTest.setTemporaryAddress(temporaryAddressTest);
        //Method calls in order
        lenient().when(addressService.getAllAddress()).thenReturn(List.of(permanentAddressTest, temporaryAddressTest));
        lenient().when(personService.getPerson(1)).thenReturn(personTest);
        lenient().when(personService.deletePerson(1)).thenReturn(personTest.setId(1));
        lenient().when(contactService.getAllContacts()).thenReturn(List.of(contactInfoTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);

        Person deleted = dispatcher.deletePerson(1);

        Assertions.assertEquals(permanentAddressTest, deleted.getPermanentAddress());
        Assertions.assertEquals(temporaryAddressTest, deleted.getTemporaryAddress());
        Assertions.assertEquals(personTest.getName(), deleted.getName());
        Assertions.assertEquals(personTest.getAge(), deleted.getAge());
        Assertions.assertEquals(personTest.getId(), deleted.getId());
    }

    @Test
    void invalid_address_type_throws_exception() {
        temporaryAddressTest.setType("invalid");

        //Ensure they form a cohesive entity
        permanentAddressTest.addContact(contactInfoTest);
        temporaryAddressTest.addContact(contactInfoTest);
        personTest.setPermanentAddress(permanentAddressTest);
        personTest.setTemporaryAddress(temporaryAddressTest);
        //Method calls in order
        lenient().when(addressService.getAllAddress()).thenReturn(List.of(permanentAddressTest, temporaryAddressTest));
        lenient().when(personService.getPerson(1)).thenReturn(personTest);
        lenient().when(personService.deletePerson(1)).thenReturn(personTest.setId(1));
        lenient().when(contactService.getAllContacts()).thenReturn(List.of(contactInfoTest));
        lenient().when(addressService.getAddress(1)).thenReturn(permanentAddressTest);
        lenient().when(addressService.getAddress(2)).thenReturn(temporaryAddressTest);

        Assertions.assertThrows(NoSuchAddressTypeException.class, () -> dispatcher.deletePerson(1));
    }

    @Test
    void creating_address_sets_person_address_appropriate_to_type() {
        personTest.setId(1);

        when(personService.getPerson(1)).thenReturn(personTest);
        when(addressService.createAddress(permanentAddressTest)).thenReturn(permanentAddressTest.setId(1));
        when(addressService.createAddress(temporaryAddressTest)).thenReturn(temporaryAddressTest.setId(2));
        when(personMapper.personToSimplePersonDTO(personTest)).thenReturn(
                new SimplePersonDTO()
                        .setId(1)
                        .setName(personTest.getName())
                        .setAge(personTest.getAge())
        );

        dispatcher.createAddress(1, permanentAddressTest);
        dispatcher.createAddress(1, temporaryAddressTest);

        verify(personService, times(1)).updatePerson(personTest.setTemporaryAddress(temporaryAddressTest));

        personTest.setTemporaryAddress(null);

        verify(personService, times(1)).updatePerson(personTest.setPermanentAddress(permanentAddressTest));
    }

    @Test
    void creating_address_with_invalid_address_type_throws_error() {
        personTest.setId(1);

        when(personService.getPerson(1)).thenReturn(personTest);
        when(addressService.createAddress(permanentAddressTest.setType("invalid"))).thenReturn(permanentAddressTest.setId(1));
        when(personMapper.personToSimplePersonDTO(personTest)).thenReturn(
                new SimplePersonDTO()
                        .setId(1)
                        .setName(personTest.getName())
                        .setAge(personTest.getAge())
        );

        Assertions.assertThrows(NoSuchAddressTypeException.class, () -> dispatcher.createAddress(1, permanentAddressTest));
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

        List<Address> addresses = dispatcher.getAllAddress(1);

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


}
