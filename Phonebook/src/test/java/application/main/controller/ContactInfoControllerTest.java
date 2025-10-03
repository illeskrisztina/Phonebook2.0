package application.main.controller;

import application.main.model.entity.dto.AddressDTO;
import application.main.model.entity.dto.ContactInfoDTO;
import application.main.model.entity.dto.ContactInfoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ContactInfoControllerTest {
    ContactInfoDTO contactTest;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @BeforeEach
    void setUp() {
        contactTest = new ContactInfoDTO()
                .setType("mobile phone")
                .setContact("+36 1 234 5678");
    }

    @Test
    void adding_contact_info_returns_created_status_code() {
        ResponseEntity<ContactInfoDTO> response = restTemplate.postForEntity("/api/addresses/1/contacts", contactTest, ContactInfoDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void adding_contact_info_creates_it_in_database() {
        restTemplate.postForEntity("/api/addresses/1/contacts", contactTest, ContactInfoDTO.class);

        ResponseEntity<ContactInfoDTO> response = restTemplate.getForEntity("/api/contacts/" + contactTest.getContact(), ContactInfoDTO.class);

        Assertions.assertEquals("mobile phone", response.getBody().getType());
        Assertions.assertEquals("+36 1 234 5678", response.getBody().getContact());
    }

    @Test
    void adding_contact_info_adds_it_to_correct_address() {
        restTemplate.postForEntity("/api/addresses/1/contacts", contactTest, ContactInfoDTO.class);

        ResponseEntity<AddressDTO> response =  restTemplate.getForEntity("/api/addresses/1", AddressDTO.class);

        Assertions.assertNotNull(response.getBody().getContacts());
        Assertions.assertTrue(response.getBody().getContacts().contains(contactInfoMapper.contactInfoDTOToContactInfo(contactTest)));
    }

    @Test
    void getting_contact_returns_ok_if_successful() {
        ResponseEntity<ContactInfoDTO> response = restTemplate.getForEntity("/api/contacts/+36 20 234 5678", ContactInfoDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_contact_returns_correct_saved_contact() {
        ResponseEntity<ContactInfoDTO> response = restTemplate.getForEntity("/api/contacts/+36 20 234 5678", ContactInfoDTO.class);

        Assertions.assertEquals("+36 20 234 5678",  response.getBody().getContact());
        Assertions.assertEquals("mobile phone", response.getBody().getType());
    }

    @Test
    void getting_all_contacts_returns_ok_status_code()
    {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contacts", List.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_all_contacts_returns_correct_saved_contacts() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contacts", List.class);

        Assertions.assertEquals(4, response.getBody().size());
    }

    @Test
    void getting_all_contacts_for_address_returns_only_ones_belonging_to_address() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contacts?addressId=1", List.class);

        Assertions.assertEquals(1,  response.getBody().size());
    }

    @Test
    void getting_all_contacts_for_address_returns_correct_list() {
        restTemplate.postForEntity("/api/addresses/1/contacts", contactTest, ContactInfoDTO.class);
        ResponseEntity<ArrayList> response = restTemplate.getForEntity("/api/contacts?addressId=1", ArrayList.class);

        Assertions.assertEquals("{contact=+36 20 234 5678, type=mobile phone}", response.getBody().get(0).toString());
        Assertions.assertEquals("{contact=+36 1 234 5678, type=mobile phone}", response.getBody().get(1).toString());
    }

    @Test
    void deleting_contact_entity_from_database_returns_no_content_status_code() {
        ResponseEntity<ContactInfoDTO> response = restTemplate.exchange("/api/contacts/davy_blue@gmail.com", HttpMethod.DELETE, null, ContactInfoDTO.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleting_contact_entity_deletes_it_from_the_database() {
        restTemplate.exchange("/api/contacts/davy_blue@gmail.com", HttpMethod.DELETE, null, ContactInfoDTO.class);
        ResponseEntity<ContactInfoDTO> response = restTemplate.getForEntity("/api/contacts/davy_blue@gmail.com", ContactInfoDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
