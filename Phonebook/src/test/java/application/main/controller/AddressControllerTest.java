package application.main.controller;

import application.main.model.entity.Address;
import application.main.model.entity.enums.AddressType;
import application.main.service.interfaces.IDispatcher;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private IDispatcher dispatcher;

    private Address addressTest;

    @BeforeEach
    void setUp() {
        addressTest = new Address()
                .setResidence("Hungary, Budapest, XIX. district")
                .setType(AddressType.TEMPORARY);
    }

    @Test
    void adding_address_returns_created_status_code() {
        ResponseEntity<Address> response = restTemplate.postForEntity("/api/people/1/addresses",  addressTest, Address.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void adding_address_saves_it_to_database() {
        ResponseEntity<Address> saved = restTemplate.postForEntity("/api/people/1/addresses", addressTest, Address.class);

        ResponseEntity<Address> response = restTemplate.getForEntity("/api/addresses/" + saved.getBody().getId(), Address.class);

        Assertions.assertEquals("Hungary, Budapest, XIX. district", response.getBody().getResidence());
        Assertions.assertEquals(AddressType.TEMPORARY, saved.getBody().getType());
    }

    @Test
    void getting_address_from_database_returns_saved_entity() {
        ResponseEntity<Address> response = restTemplate.getForEntity("/api/addresses/1", Address.class);


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
        ResponseEntity<Address> original = restTemplate.getForEntity("/api/addresses/1", Address.class);

        restTemplate.exchange("/api/addresses", HttpMethod.PUT, new HttpEntity<Address>(original.getBody().setType(AddressType.TEMPORARY).setResidence("Someplace far")), Address.class);

        ResponseEntity<Address> updated = restTemplate.getForEntity("/api/addresses/1", Address.class);

        Assertions.assertEquals(AddressType.TEMPORARY, updated.getBody().getType());
        Assertions.assertEquals("Someplace far", updated.getBody().getResidence());
        Assertions.assertEquals(1, updated.getBody().getId());
    }

    @Test
    void updating_address_entity_returns_ok_status_code() {
        ResponseEntity<Address> original = restTemplate.getForEntity("/api/addresses/1", Address.class);

        ResponseEntity<Address> response = restTemplate.exchange("/api/addresses", HttpMethod.PUT, new HttpEntity<Address>(original.getBody().setType(AddressType.TEMPORARY).setResidence("Someplace far")), Address.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleting_address_entity_deletes_it_from_the_database() {
        restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<Address> deleted = restTemplate.getForEntity("/api/addresses/2", Address.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, deleted.getStatusCode());
    }

    @Test
    void deleting_address_entity_returns_ok_status_code() {
        ResponseEntity<Address> deleted = restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, Address.class);

        Assertions.assertEquals(HttpStatus.OK, deleted.getStatusCode());
    }

    @Test
    void deleting_temporary_address_entity_of_person_only_modifies_temporary_address() {
        restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, Address.class);
        List<Address> addresses = dispatcher.getAllAddress(2);

        Assertions.assertNotNull(addresses);
        Assertions.assertEquals(1, addresses.size());
        Assertions.assertEquals("Hungary, Balatonboglar, Rozsa u. 37, 8630", addresses.get(0).getResidence());
        Assertions.assertEquals(AddressType.TEMPORARY, addresses.get(0).getType());
    }

    @Test
    void deleting_permanent_address_entity_of_person_only_modifies_permanent_address() {
        restTemplate.exchange("/api/addresses/3", HttpMethod.DELETE, null, Address.class);
        List<Address> addresses = dispatcher.getAllAddress(2);

        Assertions.assertNotNull(addresses);
        Assertions.assertEquals(1, addresses.size());
        Assertions.assertEquals("Jozefa Kozaceka 2068/29, 960 01 Zvolen, Slovakia", addresses.get(0).getResidence());
        Assertions.assertEquals(AddressType.PERMANENT, addresses.get(0).getType());
    }
}
