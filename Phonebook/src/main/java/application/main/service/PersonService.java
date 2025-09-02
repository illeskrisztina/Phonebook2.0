package application.main.service;

import application.main.model.database.dao.PersonDAO;
import application.main.model.database.interfaces.IPersonDAO;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import application.main.service.interfaces.IPersonService;

import java.util.List;
import java.util.NoSuchElementException;

public class PersonService implements IPersonService {
    private final IPersonDAO personDAO = PersonDAO.getInstance();

    @Override
    public Person createPerson(Person person) {
        return personDAO.createPerson(person);
    }

    @Override
    public SimplePersonDTO getPerson(int id) {
        SimplePersonDTO person = personDAO.getPerson(id);

        if (person == null) {
            throw new NoSuchElementException("The person under id " + id + " does not exist");
        }

        return person;
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return personDAO.getAllPeople();
    }

    @Override
    public Person updatePerson(Person person) {
        Person updated = personDAO.updatePerson(person);

        if (updated == null) {
            throw new NoSuchElementException("The person under id " + person.getId() + " does not exist");
        }

        return updated;
    }

    @Override
    public Person deletePerson(int id) {
        return personDAO.deletePerson(id);
    }
}
