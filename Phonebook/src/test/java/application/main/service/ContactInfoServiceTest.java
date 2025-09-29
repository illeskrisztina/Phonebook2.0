package application.main.service;

import application.main.model.database.dao.ContactInfoDAO;
import application.main.model.entity.ContactInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactInfoServiceTest {
    @Mock
    private ContactInfoDAO repo;
    @InjectMocks
    private ContactService service;
    private ContactInfo contactInfoTest;

    @BeforeEach
    void setUp() {
        contactInfoTest = new ContactInfo()
                .setContact("something@gmail.com")
                .setType("email");
    }

    @Test
    void adding_contact_calls_appropriate_repository_method() {
        when(repo.save(contactInfoTest)).thenReturn(contactInfoTest);

        ContactInfo saved = service.addContact(contactInfoTest);

        verify(repo, times(1)).save(contactInfoTest);
        Assertions.assertEquals(contactInfoTest, saved);
    }

    @Test
    void getting_contact_calls_appropriate_repository_method() {
        when(repo.findById(contactInfoTest.getContact())).thenReturn(Optional.of(contactInfoTest));

        ContactInfo contact = service.getContact(contactInfoTest.getContact());

        verify(repo, times(1)).findById(contactInfoTest.getContact());
        Assertions.assertEquals(contactInfoTest, contact);
    }

    @Test
    void getting_all_contacts_calls_appropriate_repository_method() {
        when(repo.findAll()).thenReturn(List.of(contactInfoTest));

        List<ContactInfo> contacts = service.getAllContacts();

        verify(repo, times(1)).findAll();
        Assertions.assertEquals(List.of(contactInfoTest), contacts);
    }

    @Test
    void deleting_contact_calls_appropriate_repository_method() {
        when(repo.existsById(contactInfoTest.getContact())).thenReturn(true);

        service.deleteContact(contactInfoTest.getContact());

        verify(repo, times(1)).existsById(contactInfoTest.getContact());
        verify(repo, times(1)).deleteById(contactInfoTest.getContact());
    }

    @Test
    void deleting_non_existent_entity_from_database_returns_null() {
        when(repo.existsById(contactInfoTest.getContact())).thenReturn(false);

        service.deleteContact(contactInfoTest.getContact());

        verify(repo, times(0)).deleteById(contactInfoTest.getContact());
    }
}
