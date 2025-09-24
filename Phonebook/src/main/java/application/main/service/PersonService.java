package application.main.service;

import application.main.model.database.dao.PersonDAO;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {
    private final PersonDAO personDAO;
    private final PersonMapper personMapper;

    @Override
    public Person createPerson(Person person) {
        return personDAO.save(person);
    }

    @Override
    public Person getPerson(int id) {
        return personDAO.findById(id)
                .orElseThrow(()
                -> new NoSuchElementException("The person under id " + id + " does not exist") );
    }

    @Override
    public List<Person> getAllPeople() {
        return personDAO.findAll();
    }

    @Override
    public Person updatePerson(Person person) {
        return personDAO.save(person);
    }

    @Override
    public void deletePerson(int id) {
        if (personDAO.existsById(id)) {
            personDAO.deleteById(id);
        }
    }
}
