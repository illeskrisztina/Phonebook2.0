package application.main.Services.Interfaces;

import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;

import java.util.ArrayList;
import java.util.List;

public interface IPersonService {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int Id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int Id);
}
