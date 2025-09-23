package application.main.service.interfaces;

import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {
    public Person createPerson(Person person);

    public Person getPerson(int id);

    public List<Person> getAllPeople();

    public Person updatePerson(Person person);

    public void deletePerson(int id);
}
