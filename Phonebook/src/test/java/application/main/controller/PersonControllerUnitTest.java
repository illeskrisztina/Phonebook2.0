package application.main.controller;

import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonDTO;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.service.Dispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerUnitTest {

    @Autowired
    private PersonMapper mapper;
    @Mock
    private Dispatcher dispatcher;
    @InjectMocks
    private PersonController controller;


    @Test
    void when_creating_person_if_error_is_thrown_conflict_status_code_returned() {
        when(dispatcher.createPerson(any(Person.class))).thenThrow(NoSuchElementException.class);

        ResponseEntity<PersonDTO> response = controller.createPerson(new Person());

        verify(dispatcher,  times(1)).createPerson(any(Person.class));
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void if_dispatcher_returns_null_for_getPerson_not_found_is_returned() {
        when(dispatcher.getPerson(1)).thenReturn(null);

        ResponseEntity<SimplePersonDTO> response = controller.getPerson(1);

        verify(dispatcher,  times(1)).getPerson(1);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void when_exception_is_thrown_for_getPerson_returns_bad_request_status_code() {
        when(dispatcher.getPerson(1)).thenThrow(new RuntimeException("Test"));

        ResponseEntity<SimplePersonDTO> response = controller.getPerson(1);

        verify(dispatcher,  times(1)).getPerson(1);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void when_exception_is_thrown_for_getAllPeople_returns_not_found_status_code() {
        when(dispatcher.getAllPeople()).thenThrow(new NoSuchElementException("Test"));

        ResponseEntity<List<SimplePersonDTO>> response = controller.getAllPeople();

        verify(dispatcher,  times(1)).getAllPeople();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void when_updating_person_when_no_element_is_found_exception_is_thrown() {
        when(dispatcher.updatePerson(any(Person.class))).thenThrow(NoSuchElementException.class);

        ResponseEntity<PersonDTO> response = controller.updatePerson(new Person());

        verify(dispatcher,  times(1)).updatePerson(any(Person.class));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void when_generic_exception_is_thrown_during_updating_person_not_found_status_code_is_returned() {
        when(dispatcher.updatePerson(any(Person.class))).thenThrow(RuntimeException.class);

        ResponseEntity<PersonDTO> response = controller.updatePerson(new Person());

        verify(dispatcher,  times(1)).updatePerson(any(Person.class));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void when_generic_exception_is_thrown_during_delete_person_not_found_status_code_is_returned() {
        Mockito.doThrow(NoSuchElementException.class).when(dispatcher).deletePerson(any(Integer.class));

        ResponseEntity<Void> response = controller.deletePerson(1);

        verify(dispatcher,  times(1)).deletePerson(1);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
