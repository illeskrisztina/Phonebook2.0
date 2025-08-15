package application.main.model.Database.Interfaces;

import application.main.model.Entity.DTOs.SimplePersonDTO;
import application.main.model.Entity.Person;

import java.util.List;

public interface IPersonDAO {
    public Person createPerson(Person person);

    public SimplePersonDTO getPerson(int Id);

    public List<SimplePersonDTO> getAllPeople();

    public Person updatePerson(Person person);

    public Person deletePerson(int Id);
}
