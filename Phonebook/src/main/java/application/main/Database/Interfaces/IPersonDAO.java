package application.main.Database.Interfaces;

import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;

import java.util.List;

public interface IPersonDAO {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int Id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public SimplePersonDTO deletePerson(int Id);
}
