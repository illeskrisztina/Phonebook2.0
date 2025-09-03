package application.main.service.interfaces;

import application.main.model.entity.dto.SimplePersonDTO;
import application.main.model.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int id);
}
