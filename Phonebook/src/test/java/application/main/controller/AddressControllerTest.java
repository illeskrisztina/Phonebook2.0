package application.main.controller;

import application.main.model.entity.dto.AddressDTO;
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

    private AddressDTO addressTest;

    @BeforeEach
    void setUp() {
        addressTest = new AddressDTO()
                .setResidence("Hungary, Budapest, XIX. district")
                .setType(AddressType.TEMPORARY);
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

    @Test
    void deleting_temporary_address_entity_of_person_only_modifies_temporary_address() {
        restTemplate.exchange("/api/addresses/2", HttpMethod.DELETE, null, AddressDTO.class);
        List<AddressDTO> addresses = dispatcher.getAllAddress(2);

        Assertions.assertNotNull(addresses);
        Assertions.assertEquals(1, addresses.size());
        Assertions.assertEquals("Hungary, Balatonboglar, Rozsa u. 37, 8630", addresses.get(0).getResidence());
        Assertions.assertEquals(AddressType.TEMPORARY, addresses.get(0).getType());
    }

    @Test
    void deleting_permanent_address_entity_of_person_only_modifies_permanent_address() {
        restTemplate.exchange("/api/addresses/3", HttpMethod.DELETE, null, AddressDTO.class);
        List<AddressDTO> addresses = dispatcher.getAllAddress(2);

        Assertions.assertNotNull(addresses);
        Assertions.assertEquals(1, addresses.size());
        Assertions.assertEquals("Jozefa Kozaceka 2068/29, 960 01 Zvolen, Slovakia", addresses.get(0).getResidence());
        Assertions.assertEquals(AddressType.PERMANENT, addresses.get(0).getType());
    }
}
