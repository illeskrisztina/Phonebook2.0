package application.main.service;

import application.main.model.database.dao.PersonDAO;
import application.main.model.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonDAO repo;
    @InjectMocks
    private PersonService service;

    private Person personTest;

    @BeforeEach
    void setUp(@Mock PersonDAO repo) {
        personTest = new Person()
                .setAge(25)
                .setName("Melanie");
    }

    @Test
    void createPerson_calls_repository_method() {
        when(repo.save(personTest)).thenReturn(personTest.setId(1));

        Person called = service.createPerson(personTest);

        verify(repo, times(1)).save(personTest);
        Assertions.assertEquals(personTest, called);
    }

    @Test
    void getPerson_returns_person_if_person_is_found() {
        when(repo.findById(1)).thenReturn(Optional.of(personTest));

        Person called = service.getPerson(1);

        Assertions.assertEquals(personTest, called);
    }

    @Test
    void getting_non_existent_entity_throws_exception() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.getPerson(1);
        });
    }

    @Test
    void getAllPeople_calls_repository_method() {
        when(repo.findAll()).thenReturn(List.of(personTest));

        List<Person> called = service.getAllPeople();

        verify(repo, times(1)).findAll();
        Assertions.assertEquals(List.of(personTest), called);
    }

    @Test
    void updatePerson_calls_repository_method() {
        when(repo.save(personTest)).thenReturn(personTest.setId(1));

        Person called = service.updatePerson(personTest);

        verify(repo, times(1)).save(personTest);
        Assertions.assertEquals(personTest, called);
    }

    @Test
    void deletePerson_if_person_exists_calls_repository_method() {
        when(repo.existsById(1)).thenReturn(true);

        service.deletePerson(1);

        verify(repo, times(1)).existsById(1);
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void deletePerson_calls_none_if_person_does_not_exist() {
        when(repo.existsById(1)).thenReturn(false);

        service.deletePerson(1);

        verify(repo, times(1)).existsById(1);
        verify(repo, times(0)).deleteById(1);
    }
}
