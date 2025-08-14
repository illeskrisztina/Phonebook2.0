package application.main.Controllers;

import application.main.Entities.DTOs.SimplePersonDTO;
import application.main.Entities.Person;
import application.main.Model.Interfaces.IDispatcher;
import application.main.Model.Dispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/people")
public class PersonController
{
  IDispatcher dispatcher = new Dispatcher();

  @PostMapping
  public ResponseEntity<Person> createPerson(@RequestBody Person person)
  {
    try
    {
      Person created = dispatcher.createPerson(person);
      return new ResponseEntity<>(person, HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<SimplePersonDTO> getPerson(@PathVariable("id") int id)
  {
    try
    {
      SimplePersonDTO person = dispatcher.getPerson(id);
      return new ResponseEntity<>(person, HttpStatus.OK);
    }
    catch (NoSuchElementException f)
    {
      f.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<List<SimplePersonDTO>> getAllPeople()
  {
    try
    {
      List<SimplePersonDTO> allPeople = dispatcher.getAllPeople();
      return new ResponseEntity<>(allPeople, HttpStatus.OK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping
  public ResponseEntity<Person> updatePerson(@RequestBody Person person)
  {
    try
    {
      Person updated = dispatcher.updatePerson(person);
      return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    catch (NoSuchElementException f)
    {
      f.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Person> deletePerson(@PathVariable("id") int id)
  {
    try
    {
      Person deleted = dispatcher.deletePerson(id);
      return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }
}
