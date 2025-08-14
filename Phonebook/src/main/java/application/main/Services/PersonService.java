package application.main.Services;

import application.main.Database.DAOs.AddressDAO;
import application.main.Database.DAOs.ContactInfoDAO;
import application.main.Database.DAOs.PersonDAO;
import application.main.Database.Interfaces.IAddressDAO;
import application.main.Database.Interfaces.IContactInfoDAO;
import application.main.Database.Interfaces.IPersonDAO;
import application.main.Entities.Address;
import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;
import application.main.Services.Interfaces.IPersonService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
