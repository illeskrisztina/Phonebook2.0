package application.main;

import application.main.model.entity.Address;
import application.main.model.entity.ContactInfo;
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
class ContactInfoTest {
    ContactInfo contactTest;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        contactTest = new ContactInfo()
                .setType("mobile phone")
                .setContact("+36 1 234 5678");
    }

    @Test
    void created_object_not_null() {
        Assertions.assertNotNull(contactTest);
    }

    @Test
    void created_object_has_correct_contact_type() {
        Assertions.assertEquals("mobile phone", contactTest.getType());
    }

    @Test
    void created_object_has_correct_contact() {
        Assertions.assertEquals("+36 1 234 5678", contactTest.getContact());
    }

    @Test
    void created_object_type_can_be_set() {
        contactTest.setType("email");

        Assertions.assertEquals("email", contactTest.getType());
    }

    @Test
    void created_object_contact_can_be_set() {
        contactTest.setContact("hi@gmail.com");

        Assertions.assertEquals("hi@gmail.com", contactTest.getContact());
    }

    @Test
    void different_objects_same_attributes_are_equal() {
        ContactInfo other = new ContactInfo()
                .setType("mobile phone")
                .setContact("+36 1 234 5678");

        Assertions.assertEquals(true, other.equals(contactTest));
    }

    @Test
    void different_objects_different_attributes_not_equal() {
        ContactInfo other = new ContactInfo()
                .setType("email")
                .setContact("hi@gmail.com");

        Assertions.assertEquals(false, other.equals(contactTest));
    }

    @Test
    void adding_contact_info_returns_created_status_code() {
        ResponseEntity<ContactInfo> response = restTemplate.postForEntity("/api/address/1/contact", contactTest, ContactInfo.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void adding_contact_info_creates_it_in_database() {
        restTemplate.postForEntity("/api/address/1/contact", contactTest, ContactInfo.class);

        ResponseEntity<ContactInfo> response = restTemplate.getForEntity("/api/contact/" + contactTest.getContact(), ContactInfo.class);

        Assertions.assertEquals("mobile phone", response.getBody().getType());
        Assertions.assertEquals("+36 1 234 5678", response.getBody().getContact());
    }

    @Test
    void adding_contact_info_adds_it_to_correct_address() {
        restTemplate.postForEntity("/api/address/1/contact", contactTest, ContactInfo.class);

        ResponseEntity<Address> response =  restTemplate.getForEntity("/api/addresses/1", Address.class);

        Assertions.assertNotNull(response.getBody().getContacts());
        Assertions.assertTrue(response.getBody().getContacts().contains(contactTest));
    }

    @Test
    void getting_contact_returns_ok_if_successful() {
        ResponseEntity<ContactInfo> response = restTemplate.getForEntity("/api/contact/+36 20 234 5678" + contactTest.getContact(), ContactInfo.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_contact_returns_correct_saved_contact() {
        ResponseEntity<ContactInfo> response = restTemplate.getForEntity("/api/contact/+36 20 234 5678", ContactInfo.class);

        Assertions.assertEquals("+36 20 234 5678",  response.getBody().getContact());
        Assertions.assertEquals("mobile phone", response.getBody().getType());
    }

    @Test
    void getting_all_contacts_returns_ok_status_code()
    {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contact", List.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_all_contacts_returns_correct_saved_contacts() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contact", List.class);

        Assertions.assertEquals(3, response.getBody().size());
    }

    @Test
    void getting_all_contacts_for_address_returns_only_ones_belonging_to_address() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/contact?addressId=1", List.class);

        Assertions.assertEquals(1,  response.getBody().size());
    }

    @Test
    void getting_all_contacts_for_address_returns_correct_list() {
        restTemplate.postForEntity("/api/address/1/contact", contactTest, ContactInfo.class);
        ResponseEntity<ArrayList> response = restTemplate.getForEntity("/api/contact?addressId=1", ArrayList.class);

        Assertions.assertEquals("{contact=+36 20 234 5678, type=mobile phone}", response.getBody().get(0).toString());
        Assertions.assertEquals("{contact=+36 1 234 5678, type=mobile phone}", response.getBody().get(1).toString());
    }

    @Test
    void deleting_contact_entity_from_database_returns_ok_status_code() {
        ResponseEntity<ContactInfo> response = restTemplate.exchange("/api/contact/davy_blue@gmail.com", HttpMethod.DELETE, null, ContactInfo.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleting_contact_entity_deletes_it_from_the_database() {
        restTemplate.exchange("/api/contact/davy_blue@gmail.com", HttpMethod.DELETE, null, ContactInfo.class);
        ResponseEntity<ContactInfo> response = restTemplate.getForEntity("/api/contact/davy_blue@gmail.com", ContactInfo.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
