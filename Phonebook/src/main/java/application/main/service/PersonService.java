package application.main.service;

import application.main.model.database.dao.PersonDAO;
import application.main.model.entity.Person;
import application.main.model.entity.dto.PersonMapper;
import application.main.model.entity.dto.SimplePersonDTO;
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
    public SimplePersonDTO getPerson(int id) {
        SimplePersonDTO person = personMapper.personToSimplePersonDTO(personDAO.findById(id).orElse(null));

        if (person == null) {
            throw new NoSuchElementException("The person under id " + id + " does not exist");
        }

        return person;
    }

    @Override
    public List<SimplePersonDTO> getAllPeople() {
        return personDAO.findAll().stream().map(personMapper::personToSimplePersonDTO).toList();
    }

    @Override
    public Person updatePerson(Person person) {
        return personDAO.save(person);
    }

    @Override
    public Person deletePerson(int id) {
        Person deleted = personDAO.findById(id).orElse(null);

        if (deleted != null) {
            personDAO.deleteById(id);
        }

        return deleted;
    }
}
