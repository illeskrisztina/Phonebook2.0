package application.main.model.database.interfaces;

import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;

import java.util.List;

public interface IPersonDAO {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int id);
}
