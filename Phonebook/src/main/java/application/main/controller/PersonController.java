package application.main.controller;

import application.main.model.entity.Person;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.service.interfaces.IDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final IDispatcher dispatcher;
    private static final String ERROR_HEADER = "Error";

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        try {
            Person created = dispatcher.createPerson(person);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header(HttpHeaders.LOCATION, "/" + created.getId())
                    .body(created);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header(ERROR_HEADER, "Could not add person entity into the database.")
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimplePersonDTO> getPerson(@PathVariable("id") int id) {
        try {
            SimplePersonDTO person = dispatcher.getPerson(id);
            if (person == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header("Person with id " + id + " not found.")
                        .build();
            }
            return ResponseEntity.ok(person);
        } catch (NoSuchElementException f) {
            log.error(f.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Could not find the right person to update.")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header(ERROR_HEADER, "Something went wrong while fetching person entity")
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SimplePersonDTO>> getAllPeople() {
        try {
            List<SimplePersonDTO> allPeople = dispatcher.getAllPeople();
            return ResponseEntity.ok(allPeople);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Could not retrieve the list of people stored in the database.")
                    .build();
        }
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            Person updated = dispatcher.updatePerson(person);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException f) {
            log.error(f.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Could not update person entity.")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Something went wrong.")
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id) {
        try {
            dispatcher.deletePerson(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(ERROR_HEADER, "Could not delete person entity from database.")
                    .build();
        }
    }
}
