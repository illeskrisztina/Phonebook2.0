package application.main.service.interfaces;

import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;

import java.util.List;

public interface IPersonService {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int Id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int Id);
}
