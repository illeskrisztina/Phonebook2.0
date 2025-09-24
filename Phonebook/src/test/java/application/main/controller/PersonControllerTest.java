package application.main.controller;

import application.main.model.entity.dto.PersonDTO;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
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
class PersonControllerTest {

    private PersonDTO personTest;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PersonMapper mapper;

    @BeforeEach
    void setUp() {
        personTest = new PersonDTO()
                .setAge(25)
                .setName("Melanie");
    }

    @Test
    void adding_person_returns_created_status_code() {
        ResponseEntity<PersonDTO> response = restTemplate.postForEntity("/api/people",  personTest, PersonDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void adding_person_to_database_actually_adds_entity(){
        ResponseEntity<PersonDTO> added = restTemplate.postForEntity("/api/people", personTest, PersonDTO.class);

        ResponseEntity<SimplePersonDTO> response = restTemplate.getForEntity("/api/people/" + added.getBody().getId(), SimplePersonDTO.class);

        Assertions.assertEquals(25, response.getBody().getAge());
        Assertions.assertEquals("Melanie", response.getBody().getName());
    }

    @Test
    void getting_person_from_database_returns_ok_status_code() {
        ResponseEntity<SimplePersonDTO> response = restTemplate.getForEntity("/api/people/1", SimplePersonDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_person_from_database_returns_correct_person() {
        ResponseEntity<SimplePersonDTO> response = restTemplate.getForEntity("/api/people/1", SimplePersonDTO.class);

        Assertions.assertEquals(24, response.getBody().getAge());
        Assertions.assertEquals("Jane Doe", response.getBody().getName());
    }

    @Test
    void trying_to_get_non_existing_person_returns_not_found_status_code() {
        ResponseEntity<SimplePersonDTO> response = restTemplate.getForEntity("/api/people/10", SimplePersonDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void successfully_getting_all_people_returns_ok_status_code() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/people", List.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getting_all_people_returns_all_people_in_database() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/people", List.class);

        Assertions.assertEquals(3, response.getBody().size());
    }

    @Test
    void updating_test_person_updates_entity() {
        ResponseEntity<SimplePersonDTO> response = restTemplate.getForEntity("/api/people/1",  SimplePersonDTO.class);

        restTemplate.exchange("/api/people", HttpMethod.PUT, new HttpEntity<>(mapper.simplePersonDTOToPerson(response.getBody().setName("Margaret").setAge(12))) , PersonDTO.class);

        ResponseEntity<SimplePersonDTO> updated = restTemplate.getForEntity("/api/people/1", SimplePersonDTO.class);

        Assertions.assertEquals("Margaret", updated.getBody().getName());
        Assertions.assertEquals(12, updated.getBody().getAge());
        Assertions.assertEquals(1, updated.getBody().getId());
    }

    @Test
    void deleting_person_removes_them_from_database() {
        restTemplate.exchange("/api/people/1", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<PersonDTO> deleted = restTemplate.getForEntity("/api/people/1", PersonDTO.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, deleted.getStatusCode());
    }
}
