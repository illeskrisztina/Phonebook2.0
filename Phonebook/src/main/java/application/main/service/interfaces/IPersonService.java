package application.main.service.interfaces;

import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {
    Person createPerson(Person person);

    Person getPerson(int id);

    List<Person> getAllPeople();

    Person updatePerson(Person person);

    void deletePerson(int id);
}
