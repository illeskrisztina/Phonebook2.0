package application.main.controller;

import application.main.model.entity.Person;
import application.main.model.entity.dto.SimplePersonDTO;
import application.main.service.Dispatcher;
import application.main.service.interfaces.IDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

  @PostMapping
  public ResponseEntity<Person> createPerson(@RequestBody Person person)
  {
    try
    {
      return new ResponseEntity<>(dispatcher.createPerson(person), HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      LOG.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.CONFLICT);
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
      LOG.error(f.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch (Exception e)
    {
      LOG.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
      LOG.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
      LOG.error(f.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch (Exception e)
    {
      LOG.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
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
      LOG.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
