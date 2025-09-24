package application.main;

import application.main.model.entity.ContactInfo;
import application.main.model.entity.dto.AddressDTO;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private AddressDTO addressTest;

    @BeforeEach
     void setUp() {
        addressTest = new AddressDTO()
                .setResidence("Hungary, Budapest, XIX. district")
                .setType(AddressType.TEMPORARY);
    }

  @Test
  void created_object_has_correct_address()
  {
    Assertions.assertEquals("Hungary, Budapest, XIX. district", addressTest.getResidence());
  }

    @Test
    void created_object_has_empty_list() {
        Assertions.assertEquals(0, addressTest.getContacts().size());
    }

    @Test
    void adding_contact_increases_list_size_by_one() {
        Assertions.assertEquals(0, addressTest.getContacts().size());
        ContactInfo contact = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        addressTest.addContact(contact);

        Assertions.assertEquals(1, addressTest.getContacts().size());
    }

    @Test
    void address_can_be_set() {
        addressTest.setResidence("new address");

        Assertions.assertEquals("new address", addressTest.getResidence());
    }

    @Test
    void contact_list_can_be_set() {
        ArrayList<ContactInfo> newList = new ArrayList<>();
        newList.add(new ContactInfo()
                .setType("hi")
                .setContact("hello"));
        newList.add(new ContactInfo()
                .setType("hoi")
                .setContact("heio"));
        newList.add(new ContactInfo()
                .setType("morning")
                .setContact("good morning"));

        addressTest.setContacts(newList);

        Assertions.assertEquals(3, addressTest.getContacts().size());
        Assertions.assertEquals(newList, addressTest.getContacts());
    }

    @Test
    void removing_contact_removes_right_contact() {
        ContactInfo contact1 = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        ContactInfo contact2 = new ContactInfo()
                .setType("hoi")
                .setContact("heio");
        ContactInfo contact3 = new ContactInfo()
                .setType("morning")
                .setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        //New one to ensure it doesn't just check for reference variables
        ContactInfo removed = addressTest.removeContact(new ContactInfo()
                .setType("hoi")
                .setContact("heio"));

        Assertions.assertEquals(contact2, removed);
    }

    @Test
    void removing_contact_decreases_size_of_list() {
        ContactInfo contact1 = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        ContactInfo contact2 = new ContactInfo()
                .setType("hoi")
                .setContact("heio");
        ContactInfo contact3 = new ContactInfo()
                .setType("morning")
                .setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        addressTest.removeContact(new ContactInfo()
                .setType("hoi")
                .setContact("heio"));

        Assertions.assertEquals(2, addressTest.getContacts().size());
    }

    @Test
    void removing_contact_leaves_right_ones_behind() {
        ContactInfo contact1 = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        ContactInfo contact2 = new ContactInfo()
                .setType("hoi")
                .setContact("heio");
        ContactInfo contact3 = new ContactInfo()
                .setType("morning")
                .setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        addressTest.removeContact(new ContactInfo()
                .setType("hoi")
                .setContact("heio"));

        Assertions.assertEquals(contact1, addressTest.getContacts().get(0));
        Assertions.assertEquals(contact3, addressTest.getContacts().get(1));
    }

    @Test
    void constructing_same_contacts_list_still_returns_equal() {
        ContactInfo contact1 = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        ContactInfo contact2 = new ContactInfo()
                .setType("hoi")
                .setContact("heio");
        ContactInfo contact3 = new ContactInfo()
                .setType("morning")
                .setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        AddressDTO other = new AddressDTO().setResidence("Hungary, Budapest, XIX. district").setType(AddressType.TEMPORARY);

        other.addContact(new ContactInfo()
                .setType("hi")
                .setContact("hello"));
        other.addContact(new ContactInfo()
                .setType("hoi")
                .setContact("heio"));
        other.addContact(new ContactInfo()
                .setType("morning")
                .setContact("good morning"));

        Assertions.assertEquals(true, other.equals(addressTest));
    }

    @Test
    void address_with_different_attributes_not_equal() {
        ContactInfo contact1 = new ContactInfo()
                .setType("hi")
                .setContact("hello");
        ContactInfo contact2 = new ContactInfo()
                .setType("hoi")
                .setContact("heio");
        ContactInfo contact3 = new ContactInfo()
                .setType("morning")
                .setContact("good morning");

        addressTest.addContact(contact1);
        addressTest.addContact(contact2);
        addressTest.addContact(contact3);

        AddressDTO other = new AddressDTO()
                .setResidence("some address");

        other.addContact(new ContactInfo()
                .setType("hi")
                .setContact("hello"));

        Assertions.assertNotEquals(true, other.equals(addressTest));
    }

    @Test
    void adding_address_returns_created_status_code() {
        ResponseEntity<AddressDTO> response = restTemplate.postForEntity("/api/people/1/addresses",  addressTest, AddressDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
     void adding_address_saves_it_to_database() {
        ResponseEntity<AddressDTO> saved = restTemplate.postForEntity("/api/people/1/addresses", addressTest, AddressDTO.class);

        ResponseEntity<AddressDTO> response = restTemplate.getForEntity("/api/addresses/" + saved.getBody().getId(), AddressDTO.class);

        Assertions.assertEquals("Hungary, Budapest, XIX. district", response.getBody().getResidence());
        Assertions.assertEquals(AddressType.TEMPORARY, saved.getBody().getType());
    }

    @Test
    void getting_address_from_database_returns_saved_entity() {
        ResponseEntity<AddressDTO> response = restTemplate.getForEntity("/api/addresses/1", AddressDTO.class);


        Assertions.assertEquals("Hungary, Budapest, 1194, Some street 13.", response.getBody().getResidence());
        Assertions.assertEquals(AddressType.PERMANENT, response.getBody().getType());
    }

    @Test
    void getting_all_addresses_returns_ok_status_code() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/addresses", String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updating_address_entity_updates_it_in_the_database() {
        ResponseEntity<AddressDTO> original = restTemplate.getForEntity("/api/addresses/1", AddressDTO.class);

        restTemplate.exchange("/api/addresses", HttpMethod.PUT, new HttpEntity<AddressDTO>(original.getBody().setType(AddressType.TEMPORARY).setResidence("Someplace far")), AddressDTO.class);

        ResponseEntity<AddressDTO> updated = restTemplate.getForEntity("/api/addresses/1", AddressDTO.class);

        Assertions.assertEquals(AddressType.TEMPORARY, updated.getBody().getType());
        Assertions.assertEquals("Someplace far", updated.getBody().getResidence());
        Assertions.assertEquals(1, updated.getBody().getId());
    }

    @Test
    void updating_address_entity_returns_ok_status_code() {
        ResponseEntity<AddressDTO> original = restTemplate.getForEntity("/api/addresses/1", AddressDTO.class);

        ResponseEntity<AddressDTO> response = restTemplate.exchange("/api/addresses", HttpMethod.PUT, new HttpEntity<AddressDTO>(original.getBody().setType(AddressType.TEMPORARY).setResidence("Someplace far")), AddressDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleting_address_entity_deletes_it_from_the_database() {
        restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<AddressDTO> deleted = restTemplate.getForEntity("/api/addresses/2", AddressDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, deleted.getStatusCode());
    }

    @Test
    void deleting_address_entity_returns_no_content_status_code() {
        ResponseEntity<AddressDTO> deleted = restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, AddressDTO.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, deleted.getStatusCode());
    }
}
