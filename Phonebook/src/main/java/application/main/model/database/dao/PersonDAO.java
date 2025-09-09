package application.main.model.database.dao;

import application.main.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonDAO extends JpaRepository<Person, Integer> {

}
