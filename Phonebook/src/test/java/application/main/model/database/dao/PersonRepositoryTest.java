package application.main.model.database.dao;

import application.main.model.entity.Address;
import application.main.model.entity.Person;
import application.main.model.entity.enums.AddressType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class PersonRepositoryTest {
    private Person personTest;
    private Address temporaryAddress;
    @Autowired
    private PersonDAO repo;

    @BeforeEach
    void setUp() {
        personTest = new Person()
                .setAge(25)
                .setName("Melanie");
        temporaryAddress = new Address()
                .setType(AddressType.TEMPORARY)
                .setResidence("other address");
    }

    @Test
    void saving_person_entity_saves_it_in_database()
    {
        int personId = repo.save(personTest).getId();

        Person saved = repo.findById(personId).orElse(null);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(personId, saved.getId());
        Assertions.assertEquals(personTest.getName(), saved.getName());
        Assertions.assertEquals(personTest.getAge(), saved.getAge());
    }

    @Test
    void saving_person_entity_with_address_saves_address_too() {
        personTest.setTemporaryAddress(temporaryAddress);
        int personId = repo.save(personTest).getId();

        Person saved = repo.findById(personId).orElse(null);

        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getTemporaryAddress());
        Assertions.assertEquals(temporaryAddress, saved.getTemporaryAddress());
    }

    @Test
    void getting_existing_entity_from_database_returns_correct_entity() {
        Person inDatabase = repo.findById(1).orElse(null);

        Assertions.assertNotNull(inDatabase);
        Assertions.assertEquals("Jane Doe",  inDatabase.getName());
        Assertions.assertEquals(24, inDatabase.getAge());
        Assertions.assertEquals(1, inDatabase.getId());
        Assertions.assertNotNull(inDatabase.getPermanentAddress());
    }

    @Test
    void getting_non_existent_entity_returns_null() {
        Assertions.assertNull(repo.findById(10).orElse(null));
    }

    @Test
    void getting_all_people_returns_list_of_entities_saved() {
        List<Person> saved = repo.findAll();

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(3, saved.size());
    }

    @Test
    void updating_name_of_person_saves_it_in_database() {
        int savedId = repo.save(personTest).getId();
        personTest.setName("Alaine");

        repo.save(personTest);
        Person updated = repo.findById(savedId).orElse(null);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(personTest.getName(), updated.getName());
    }

    @Test
    void deleting_person_from_database_gets_rid_of_it()
    {
        repo.deleteById(1);

        Person deleted = repo.findById(1).orElse(null);
        Assertions.assertNull(deleted);
    }

    @Test
    void deleting_one_person_entity_leaves_others_unchanged()
    {
        repo.deleteById(1);

        List<Person> rest = repo.findAll();

        Assertions.assertEquals(2, rest.size());
    }
}
