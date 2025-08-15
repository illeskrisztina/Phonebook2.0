package application.main.Service;

import application.main.Model.Database.DAOs.PersonDAO;
import application.main.Model.Database.Interfaces.IPersonDAO;
import application.main.Model.Entity.DTOs.SimplePersonDTO;
import application.main.Model.Entity.Person;
import application.main.Service.Interfaces.IPersonService;

import java.util.List;
import java.util.NoSuchElementException;

public class PersonService implements IPersonService {
    private final IPersonDAO personDAO = PersonDAO.getInstance();

    @Override
    public Person createPerson(Person person) {
        return personDAO.createPerson(person);
    }

    @Override
    public SimplePersonDTO getPerson(int Id) {
        SimplePersonDTO person = personDAO.getPerson(Id);

        if (person == null) {
            throw new NoSuchElementException("The person under id " + Id + " does not exist");
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
    public Person deletePerson(int Id) {
        return personDAO.deletePerson(Id);
    }
}
