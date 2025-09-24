package application.main.controller;

import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.ContactInfoDTO;
import application.main.service.interfaces.IDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactInfoControllerUnitTest {
    @Mock
    private IDispatcher dispatcher;
    @InjectMocks
    private ContactInfoController controller;
    private ContactInfo contactInfoTest;

    @BeforeEach
    void setUp() {
        contactInfoTest = new ContactInfo()
                .setContact("some@email.com")
                .setType("email");
    }

    @Test
    void if_creating_contact_info_entity_returns_null_not_found_status_code_is_returned() {
        when(dispatcher.addContact(contactInfoTest, null)).thenReturn(null);

        ResponseEntity<ContactInfoDTO> saved = controller.addContactInfo(null, contactInfoTest);

        verify(dispatcher).addContact(contactInfoTest, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, saved.getStatusCode());
    }
}
